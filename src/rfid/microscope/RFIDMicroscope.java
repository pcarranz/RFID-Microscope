/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * San Luis Obispo Children's Museum
 */
package rfid.microscope;

import javafx.geometry.Insets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortException;

public class RFIDMicroscope extends Application implements Constants {

   // 
   public static int microscopeListenerTracker = 0;
   public static int factsListenerTracker = 0;
   public static int videoListenerTracker = 0;

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
   public static final Map<String, MediaPlayer> videoData = new ConcurrentHashMap<>();

   // Image Objects
   public static ImageView microscopeImageView = new ImageView();
   public static ImageView specimenImageView = new ImageView();

   // Video Objects
   public static MediaView videoView = new MediaView();

   // Transition Objects
   public static FadeTransition fadeVideo = new FadeTransition(Duration.millis(1000), videoView);
   public static FadeTransition fadeImage = new FadeTransition(Duration.millis(1000), microscopeImageView);

   @Override
   public void start(Stage primaryStage) throws InterruptedException {
      // RFID reader number circle
      Circle rfidCircle = new Circle(40, Paint.valueOf("#eea95a"));
      StackPane circlePane = new StackPane();
      circlePane.getChildren().addAll(rfidCircle, readerNumber);
      circlePane.setPadding(new Insets(5, 20, 5, 10)); // top, left, bottom, right

      // Container for RFID reader number and specimen name
      HBox specimenNameBox = new HBox();
      specimenNameBox.getChildren().addAll(circlePane, specimenName);

      // Container for specimen facts, images, and video
      specimenFacts.setText("Place a specimen on 1 to begin!");
      contentPane.getChildren().add(specimenFacts);

      // Set styles
      specimenNameBox.setStyle("-fx-background-color: #3BC7C3;");
      contentPane.setStyle("-fx-background-color: #B2E77B;");

      specimenFacts.setFont(Font.font(42.0));
      readerNumber.setFont(Font.font(42.0));
      specimenName.setFont(Font.font(42.0));

      specimenName.setPadding(new Insets(15, 0, 0, 0)); // top, bottom, right, left
      specimenFacts.setWrapText(true);
      specimenFacts.setPadding(new Insets(0, 0, 5, 5)); // top, bottom, right, left

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

      // Split screen into 2 layouts - top and center
      BorderPane root = new BorderPane();
      root.setTop(specimenNameBox);
      root.setCenter(contentPane);

      // Setup RFID reader ports and specimen data
      attachEventListeners();
      initData();

      // Setup RFID reader timer
      new RFIDTimer();
      System.out.println("Task scheduled.");

      Scene scene = new Scene(root, 600, 450);

      primaryStage.setFullScreen(true);
      primaryStage.setTitle("RFID Virtual Microscope");
      primaryStage.setScene(scene);
      primaryStage.show();
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

      // Set up Arduino port
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
   }

   private static void initData() {
      // Set Specimen Names
      specimenNameData.put(Constants.VOLCANIC_ROCK_ID, Constants.VOLCANIC_ROCK);
      specimenNameData.put(Constants.SNAKE_SKIN_ID, Constants.SNAKE_SKIN);
      specimenNameData.put(Constants.BIRD_FEATHER_ID, Constants.BIRD_FEATHER);
      specimenNameData.put(Constants.BEETLE_ID, Constants.BEETLE);
      specimenNameData.put(Constants.FOSSIL_ID, Constants.FOSSIL);
      specimenNameData.put(Constants.SHARK_TOOTH_ID, Constants.SHARK_TOOTH);
      specimenNameData.put(Constants.CORAL_ID, Constants.CORAL);
      specimenNameData.put(Constants.BUTTERFLY_ID, Constants.MAPLE_LEAF);

      // Set Fact data
      factData.put(Constants.VOLCANIC_ROCK_ID, Constants.VOLCANIC_ROCK_FACTS);
      factData.put(Constants.SNAKE_SKIN_ID, Constants.SNAKE_SKIN_FACTS);
      factData.put(Constants.BIRD_FEATHER_ID, Constants.BIRD_FEATHER_FACTS);
      factData.put(Constants.BEETLE_ID, Constants.BEETLE_FACTS);
      factData.put(Constants.FOSSIL_ID, Constants.FOSSIL_FACTS);
      factData.put(Constants.SHARK_TOOTH_ID, Constants.SHARK_TOOTH_FACTS);
      factData.put(Constants.CORAL_ID, Constants.CORAL_FACTS);
      factData.put(Constants.BUTTERFLY_ID, Constants.BUTTERFLY);
      factData.put(Constants.TOMATO_SEEDS_ID, Constants.TOMATO_SEEDS);
      factData.put(Constants.MAPLE_LEAF_ID, Constants.MAPLE_LEAF);

      // Set microscopic picture data
      imageData.put(Constants.VOLCANIC_ROCK_ID, volcanicRockImage);
//        imageData.put(Constants.SNAKE_SKIN_ID, snakeSkinImage);
//        imageData.put(Constants.BIRD_FEATHER_ID, birdImage);
//        imageData.put(Constants.BEETLE_ID, beetleImage);
//        imageData.put(Constants.FOSSIL_ID, fossilImage);
//        imageData.put(Constants.SHARK_TOOTH_ID, sharkToothImage);
//        imageData.put(Constants.CORAL_ID, coralImage);
//        imageData.put(Constants.BUTTERFLY_ID, mapleLeafImage);

      // Set video data
      videoData.put(Constants.VOLCANIC_ROCK_ID, volcanicRockVideo);
//        videoData.put(Constants.SNAKE_SKIN_ID, snakeSkinVideo);
//        videoData.put(Constants.BIRD_FEATHER_ID, birdVideo);
//        videoData.put(Constants.BEETLE_ID, beetleVideo);
//        videoData.put(Constants.FOSSIL_ID, fossilVideo);
//        videoData.put(Constants.SHARK_TOOTH_ID, boneVideo);
//        videoData.put(Constants.CORAL_ID, coralVideo);
//        videoData.put(Constants.BUTTERFLY_ID, cottonVideo);
   }

   /*
    * RFID reader handler methods
    */
   /*
    * Specimen Fun Facts Handler.
    */
   public static void factsHandler() {
      System.out.println("\nFactoids Handler...");

      Platform.runLater(() -> {
         if (factsListenerTracker == 0) {
            // Turn on indicator LED
            try {
               System.out.println("Write to Arduino successful: "
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
            
            factsListenerTracker = 1;
         }
      });
   }

   /*
    * Microscope image handler
    */
   public static void microscopeHandler() {
      System.out.println("\nMicroscope handler...");
      if (microscopeListenerTracker == 0) {
         // Run on UI thread, not Serial thread
         Platform.runLater(() -> {
            // Turn on indicator LEDs
            try {
               System.out.println("Write to Arduino successful: "
                       + arduinoPort.writeInt(2));
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            }

            // Remove the video block if there is one
            contentPane.getChildren().clear();
            contentPane.getChildren().add(microscopeImageView);

            // Display specimen info and image
            readerNumber.setText(Constants.READER_2);
            specimenName.setText(getSpecimenName(tagId));
            microscopeImageView.setImage(getSpecimenImage(tagId));
         });
         microscopeListenerTracker = 1;
      }
   }

   /*
    * Video handler.
    */
   public static void videoHandler() {
      System.out.println("\nVideo Handler...");

      // Run on UI thread, not Serial thread
      Platform.runLater(() -> {
         // Turn on indicator LEDs
         try {
            System.out.println("Write to Arduino successful: "
                    + arduinoPort.writeInt(3));
         }
         catch (SerialPortException ex) {
            System.out.println(ex);
         }

         RFIDTimer.timer.cancel();
         System.out.println("Timer cancelled.");

         // Clear content then add only video view
         contentPane.getChildren().clear();
         contentPane.getChildren().add(videoView);

         // Update labels, clear facts
         readerNumber.setText(Constants.READER_3);
         specimenName.setText(getSpecimenName(tagId));

         MediaPlayer video = getVideoMedia(tagId);
         videoView.setMediaPlayer(video);
         video.play();

         // Runs when a video starts playing
         video.setOnPlaying(() -> {
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
         video.setOnEndOfMedia(() -> {
            try {
               // Re-open serial ports
               if (!serialPortA.isOpened()) {
                  serialPortA.openPort();
                  serialPortA.addEventListener(new COM_A_listener());
               }
               if (!serialPortB.isOpened()) {
                  serialPortB.openPort();
                  serialPortB.addEventListener(new COM_B_listener());
               }
               if (!serialPortC.isOpened()) {
                  serialPortC.openPort();
                  serialPortC.addEventListener(new COM_C_listener());
               }

               // Clear video view
               contentPane.getChildren().remove(videoView);
               specimenFacts.setText("Select a specimen and place it on 1 to learn more!");
               specimenName.setText("");

               // Restart timer
               new RFIDTimer();
               System.out.println("Task scheduled.");
            }
            catch (SerialPortException e) {
               System.out.println(e);
            }
         });
      });
   }

   private static String getSpecimenName(String specimenId) {
      String result = "Unknown Specimen";

      if (specimenNameData.containsKey(specimenId)) {
         result = specimenNameData.get(specimenId);
      }

      return result;
   }

   private static String getSpecimenFacts(String specimenId) {
      String result = "Unknown Specimen";

      if (factData.containsKey(specimenId)) {
         result = factData.get(specimenId);
      }

      return result;
   }

   private static Image getSpecimenImage(String specimenId) {
      return imageData.get(specimenId);
   }

   private static MediaPlayer getVideoMedia(String specimenId) {
      return videoData.get(specimenId);
   }
}
