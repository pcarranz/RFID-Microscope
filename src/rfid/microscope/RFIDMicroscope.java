/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * The San Luis Obispo Children's Museum
 */
package rfid.microscope;

import javafx.geometry.Insets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortException;

public class RFIDMicroscope extends Application implements Constants {
   //
   public static Boolean isMicroscopeOn = false;
   public static Boolean isFactsOn = false;
   public static Boolean isVideoOn = false;

   // Serial Port Objects
   public static SerialPort serialPortA;
   public static SerialPort serialPortB;
   public static SerialPort serialPortC;
   public static SerialPort arduinoPort;

   // RFID Tag Information
   public static String tagId;

   // UI Objects
   public static Text readerNumber = new Text();
   public static Label specimenName = new Label();
   public static Label specimenFacts = new Label();
   public static StackPane contentPane = new StackPane();

   // Data HashMaps
   public static final Map<String, String> factData = new ConcurrentHashMap<>();
   public static final Map<String, String> specimenNameData = new ConcurrentHashMap<>();
   public static final Map<String, Image> imageData = new ConcurrentHashMap<>();
   public static final Map<String, Media> videoData = new ConcurrentHashMap<>();

   // Image Objects
   public static ImageView microscopeImageView = new ImageView();
   public static ImageView specimenImageView = new ImageView();

   // Video Objects
   public static MediaView videoView = new MediaView();

   // Transition Objects
   public static FadeTransition fadeImage = new FadeTransition(Duration.millis(1000), microscopeImageView);

   @Override
   public void start(Stage primaryStage) throws InterruptedException, SerialPortException {
      // Setup RFID reader ports and specimen data
      attachEventListeners();
      initData();
      serialPortB.closePort();
      serialPortC.closePort();
      
      /* Draw UI */
      // RFID reader number circle
      Circle rfidCircle = new Circle(40, Paint.valueOf("#eea95a"));
      StackPane circlePane = new StackPane();
      circlePane.getChildren().addAll(rfidCircle, readerNumber);
      circlePane.setPadding(new Insets(5, 20, 5, 10)); // top, left, bottom, right

      // Container for RFID reader number and specimen name
      HBox specimenNameBox = new HBox();
      specimenNameBox.getChildren().addAll(circlePane, specimenName);

      // Container for specimen facts, images, and video
      contentPane.getChildren().add(specimenFacts);

      // Set styles
      specimenNameBox.setStyle("-fx-background-color: #3BC7C3;");
      contentPane.setStyle("-fx-background-color: #B2E77B;");

      specimenFacts.setFont(Font.font(42.0));
      readerNumber.setFont(Font.font(42.0));
      specimenName.setFont(Font.font(42.0));

      specimenName.setPadding(new Insets(15, 0, 0, 0)); // top, right, bottom, left
      specimenFacts.setWrapText(true);
      specimenFacts.setPadding(new Insets(0, 80, 0, 80));// top, right, bottom, left

      readerNumber.setFill(Paint.valueOf("#FFF"));
      specimenName.setTextFill(Paint.valueOf("#FFF"));

      // Resize images to fit contentPane size
      microscopeImageView.fitWidthProperty().bind(contentPane.widthProperty());
      microscopeImageView.fitHeightProperty().bind(contentPane.heightProperty());

      // Set image view properties
      microscopeImageView.setPreserveRatio(true);
      microscopeImageView.setSmooth(true); // slower, but better quality
      microscopeImageView.setCache(true);

      // Fade transition properties
      fadeImage.setFromValue(0.1);
      fadeImage.setToValue(1.0);
      fadeImage.setCycleCount(1);

      // Split screen into 2 sections - top and center
      BorderPane root = new BorderPane();
      root.setTop(specimenNameBox);
      root.setCenter(contentPane);

      // Show UI
      specimenFacts.setText("Place a specimen on 1 to begin!");
      Scene scene = new Scene(root, 600, 450);

      scene.setCursor(Cursor.NONE);  // hide cursor
      primaryStage.setFullScreen(true);
      primaryStage.setTitle("RFID Virtual Microscope");
      primaryStage.setScene(scene);
      primaryStage.show();
      
      // Set up arduino port
      arduinoPort = new SerialPort(Constants.ARDUINO_PORT);
      try {
         arduinoPort.openPort();
         arduinoPort.setParams(
                 SerialPort.BAUDRATE_9600,
                 SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1,
                 SerialPort.PARITY_NONE);
         Thread.sleep(5000);  // Give Arduino time to reboot
      }
      catch (SerialPortException ex) {
         System.out.println(ex);
      }
      
      // Turn off all LEDs when application is closed
      primaryStage.setOnCloseRequest((WindowEvent event) -> {
        try {
            System.out.println("Write to Arduino successful: "
                    + arduinoPort.writeInt(0));
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         } 
      });
   }

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      launch(args);
   }

   /*
    * Attach RFID Reader SerialEventListener.
    */
   private void attachEventListeners() throws InterruptedException {
      // COM_A
      serialPortA = new SerialPort(Constants.COM_A);
      try {
         serialPortA.openPort();
         serialPortA.setParams(
                 Constants.BAUDRATE_2400,
                 SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1,
                 SerialPort.PARITY_NONE
         );
         int mask = SerialPort.MASK_RXCHAR;
         serialPortA.setEventsMask(mask);
         serialPortA.addEventListener(new COM_A_listener());
      }
      catch (SerialPortException ex) {
         System.out.println(ex);
      }

      // COM_B
      serialPortB = new SerialPort(Constants.COM_B);
      try {
         serialPortB.openPort();
         serialPortB.setParams(
                 Constants.BAUDRATE_2400,
                 SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1,
                 SerialPort.PARITY_NONE);
         int mask = SerialPort.MASK_RXCHAR;
         serialPortB.setEventsMask(mask);
         serialPortB.addEventListener(new COM_B_listener());
      }
      catch (SerialPortException ex) {
         System.out.println(ex);
      }

      // COM_C
      serialPortC = new SerialPort(Constants.COM_C);
      try {
         serialPortC.openPort();
         serialPortC.setParams(
                 Constants.BAUDRATE_2400,
                 SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1,
                 SerialPort.PARITY_NONE
         );
         int mask = SerialPort.MASK_RXCHAR;
         serialPortC.setEventsMask(mask);
         serialPortC.addEventListener(new COM_C_listener());
      }
      catch (SerialPortException ex) {
         System.out.println(ex);
      }
   }

   private static void initData() {
      // Set Specimen Names
      specimenNameData.put(VOLCANIC_ROCK_ID, VOLCANIC_ROCK);
      specimenNameData.put(SNAKE_SKIN_ID, SNAKE_SKIN);
      specimenNameData.put(BIRD_FEATHER_ID, BIRD_FEATHER);
      specimenNameData.put(BEETLE_ID, BEETLE);
      specimenNameData.put(FOSSIL_ID, FOSSIL);
      specimenNameData.put(SHARK_TOOTH_ID, SHARK_TOOTH);
      specimenNameData.put(CORAL_ID, CORAL);
      specimenNameData.put(BUTTERFLY_ID, BUTTERFLY);
      specimenNameData.put(TOMATO_SEEDS_ID, TOMATO_SEEDS);
      specimenNameData.put(MAPLE_LEAF_ID, MAPLE_LEAF);

      // Set Fact data
      factData.put(VOLCANIC_ROCK_ID, VOLCANIC_ROCK_FACTS);
      factData.put(SNAKE_SKIN_ID, SNAKE_SKIN_FACTS);
      factData.put(BIRD_FEATHER_ID, BIRD_FEATHER_FACTS);
      factData.put(BEETLE_ID, BEETLE_FACTS);
      factData.put(FOSSIL_ID, FOSSIL_FACTS);
      factData.put(SHARK_TOOTH_ID, SHARK_TOOTH_FACTS);
      factData.put(CORAL_ID, CORAL_FACTS);
      factData.put(BUTTERFLY_ID, BUTTERFLY_FACTS);
      factData.put(TOMATO_SEEDS_ID, TOMATO_SEEDS_FACTS);
      factData.put(MAPLE_LEAF_ID, MAPLE_LEAF_FACTS);

      // Set microscopic picture data
      imageData.put(VOLCANIC_ROCK_ID, volcanicRockImage);
      imageData.put(SNAKE_SKIN_ID, snakeSkinImage);
      imageData.put(BIRD_FEATHER_ID, birdImage);
      imageData.put(BEETLE_ID, beetleImage);
      imageData.put(FOSSIL_ID, fossilImage);
      imageData.put(SHARK_TOOTH_ID, sharkToothImage);
      imageData.put(CORAL_ID, coralImage);
      imageData.put(BUTTERFLY_ID, butterflyImage);
      imageData.put(TOMATO_SEEDS_ID, Constants.tomatoSeedsImage);
      imageData.put(MAPLE_LEAF_ID, mapleLeafImage);

      // Set video data
      videoData.put(VOLCANIC_ROCK_ID, volcanicRockMedia);
      videoData.put(SNAKE_SKIN_ID, snakeSkinMedia);
      videoData.put(BIRD_FEATHER_ID, birdMedia);
      videoData.put(BEETLE_ID, beetleMedia);
      videoData.put(FOSSIL_ID, fossilMedia);
      videoData.put(SHARK_TOOTH_ID, sharkMedia);
      videoData.put(CORAL_ID, coralMedia);
      videoData.put(BUTTERFLY_ID, butterflyMedia);
      videoData.put(TOMATO_SEEDS_ID, tomatoMedia);
      videoData.put(MAPLE_LEAF_ID, mapleMedia);
   }

   /*
    * RFID reader handler methods
    */
   /*
    * Specimen Fun Facts Handler.
    */
   public static void factsHandler() {
      System.out.println("\nFacts Handler...");

      Platform.runLater(() -> {
         // Keep track of which reader on
         isFactsOn = true;

         // Turn on indicator LED
         try {
            System.out.println("Lights Off: "
                    + arduinoPort.writeInt(0));
            System.out.println("Light Red (Facts): "
                    + arduinoPort.writeInt(1));
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         }

         // Remove the image if there is one
         contentPane.getChildren().clear();
         contentPane.getChildren().add(specimenFacts);

         // Display specimen info and facts
         readerNumber.setText(Constants.READER_1);
         specimenName.setText(getSpecimenName(tagId));
         specimenFacts.setText(getSpecimenFacts(tagId));

         // Close current open port
         try {
            serialPortA.closePort();
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         }

         // Run timer for arrow leds
         new RFIDTimer();
      });
   }

   /*
    * Microscope image handler
    */
   public static void microscopeHandler() {
      System.out.println("\nMicroscope handler...");
      
      // Turn on indicator LEDs
         try {
            System.out.println("Lights off: "
                    + arduinoPort.writeInt(0));
            
             System.out.println("Light Blue (Microscope): "
                    + arduinoPort.writeInt(2));
             
             // Turn off Arrow 1 LEDs
             System.out.println("Arrow 1 Off: "
                    + arduinoPort.writeInt(6));
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         }

      // Run on UI thread, not Serial thread
      Platform.runLater(() -> {
         // Keep track of which reader on
         isMicroscopeOn = true;

         // Remove the video block if there is one
         contentPane.getChildren().clear();
         contentPane.getChildren().add(microscopeImageView);
         fadeImage.play();

         // Display specimen info and image
         readerNumber.setText(Constants.READER_2);
         specimenName.setText(getSpecimenName(tagId));
         microscopeImageView.setImage(getSpecimenImage(tagId));
         
         
         // Close current open port
         try {
            serialPortB.closePort();
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         }
         
         // Run timer for arrow leds
         new RFIDTimer();
      });
   }

   /*
    * Video handler.
    */
   public static void videoHandler() {
      System.out.println("\nVideo Handler...");
      
      // Turn on indicator LEDs
         try {
            System.out.println("All lights off: "
                    + arduinoPort.writeInt(0));
            System.out.println("Light green (Video): "
                    + arduinoPort.writeInt(3));
            
            // Turn off Arrow 2 LEDs
             System.out.println("Arrow 2 Off: "
                    + arduinoPort.writeInt(7));
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         }

      // Run on UI thread, not Serial thread
      Platform.runLater(() -> {
         // Keep track of which reader is on
         isVideoOn = true;

         // Clear content then add only video view
         contentPane.getChildren().clear();
         contentPane.getChildren().add(videoView);

         // Update labels, clear facts
         readerNumber.setText(Constants.READER_3);
         specimenName.setText(getSpecimenName(tagId));

         MediaPlayer videoPlayer = new MediaPlayer(getVideoMedia(tagId));
         videoView.setMediaPlayer(videoPlayer);
         videoPlayer.play();

         // Runs when a video starts playing
         videoPlayer.setOnPlaying(() -> {
            try {
               // Close serial ports while video is playing
               if (serialPortA.isOpened()) {
                  serialPortA.closePort();
               }
               if (serialPortB.isOpened()) {
                  serialPortB.closePort();
               }
               if (serialPortC.isOpened()) {
                  serialPortC.closePort();
               }
            }
            catch (SerialPortException e) {
               System.out.println(e);
            }
         });

         // Runs when a video is finished playing
         videoPlayer.setOnEndOfMedia(() -> {
            try {
               // Open facts reader
               if (!serialPortA.isOpened()) {
                  serialPortA.openPort();
                  serialPortA.addEventListener(new COM_A_listener());
               }

               // Clear video view
               contentPane.getChildren().clear();
               contentPane.getChildren().add(specimenFacts);
               specimenFacts.setText(BEGIN_PROMPT);
               specimenName.setText("");
            }
            catch (SerialPortException e) {
               System.out.println(e);
            }
            
            videoPlayer.dispose();
         });
      });
   }

   private static String getSpecimenName(String specimenId) {
      String result = UNKNOWN;

      if (specimenNameData.containsKey(specimenId)) {
         result = specimenNameData.get(specimenId);
      }

      return result;
   }

   private static String getSpecimenFacts(String specimenId) {
      String result = UNKNOWN;

      if (factData.containsKey(specimenId)) {
         result = factData.get(specimenId);
      }

      return result;
   }

   private static Image getSpecimenImage(String specimenId) {
      return imageData.get(specimenId);
   }

    private static Media getVideoMedia(String specimenId) {
      return videoData.get(specimenId);
   }
}
