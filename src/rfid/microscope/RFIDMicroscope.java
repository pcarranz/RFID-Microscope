/*
 * RFID Virtual Microscope
 * Created By: Patricia Carranza
 * CPE 461-462, Dr. John Oliver
 * Cal Poly, San Luis Obispo
 * San Luis Obispo Children's Museum
 */
package rfid.microscope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortException;

public class RFIDMicroscope extends Application implements Constants {

    // Serial Port Objects
   public static SerialPort serialPortA;
   public static SerialPort serialPortB;
   public static SerialPort serialPortC;
   public static SerialPort arduinoPort;

   // RFID Tag Information
   public static String tagId;

   // UI Objects
   public static Label currentReader;
   public static Label specimenName;
   public static Label facts;
   public static Pane contentPane;

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
   public void start(Stage primaryStage) {
      // Split screen into 2 layouts - top and center
      BorderPane container = new BorderPane();

      // Holds the specimen name and current reader number
      Pane specimenNamePane = new Pane();
      container.setTop(specimenNamePane);

      // Holds all specimen content - facts, images, etc.
      contentPane = new Pane();
      container.setCenter(contentPane);

      // Create image/video containers
      HBox microscopeBox = new HBox();
      HBox videoBox = new HBox();
      HBox specimenBox = new HBox();
      HBox factsBox = new HBox();

      // Create 'Did you know' and specimen image container
      VBox didYouKnowBox = new VBox();

      // Create all labels
      specimenName = new Label("Specimen Name");
      currentReader = new Label();
      facts = new Label("Select a specimen to begin");

      // Current reader circle
      Circle circle = new Circle(40, Paint.valueOf("#eea95a"));

      // Set styles
      specimenNamePane.setStyle("-fx-background-color: #3BC7C3; -fx-padding: 3;");
      contentPane.setStyle("-fx-background-color: #B2E77B;");
      facts.setFont(Font.font(42.0));
      facts.setStyle("-fx-label-padding: 20;");
      currentReader.setFont(Font.font(42.0));
      currentReader.setTextFill(Paint.valueOf("#fff"));
      specimenName.setFont(Font.font(42.0));
      specimenName.setStyle("-fx-label-padding: 5;");
      specimenName.setTextFill(Paint.valueOf("#fff"));

      // Set layout constraints
      circle.setLayoutX(52.0);
      circle.setLayoutY(46.0);
      currentReader.setLayoutX(40.0);
      currentReader.setLayoutY(15.0);
      specimenName.setLayoutX(100.0);
      specimenName.setLayoutY(15.0);
      facts.setWrapText(true);
      factsBox.prefWidthProperty().bind(contentPane.widthProperty());
      factsBox.prefHeightProperty().bind(contentPane.heightProperty());

      // Size containers to window size
      AnchorPane.setBottomAnchor(container, 0.0);
      AnchorPane.setRightAnchor(container, 0.0);
      AnchorPane.setLeftAnchor(container, 0.0);
      AnchorPane.setTopAnchor(container, 0.0);

      // Resize images to fit contentPane size
      microscopeImageView.fitWidthProperty().bind(contentPane.widthProperty());
      microscopeImageView.fitHeightProperty().bind(contentPane.heightProperty());

      // Resize videos to fit contentPane size
      videoView.fitWidthProperty().bind(contentPane.widthProperty());
      videoView.fitHeightProperty().bind(contentPane.heightProperty());

      // Set image view properties
      microscopeImageView.setPreserveRatio(true);
      microscopeImageView.setSmooth(true); // slower, but better quality
      microscopeImageView.setCache(true);

      // Define container children
      specimenNamePane.getChildren().addAll(specimenName, circle, currentReader);
      contentPane.getChildren().addAll(microscopeBox, videoBox, didYouKnowBox);
      factsBox.getChildren().add(facts);
      didYouKnowBox.getChildren().addAll(specimenBox, factsBox);
      microscopeBox.getChildren().add(microscopeImageView);

      // Fade transition properties
      fadeImage.setFromValue(0.1);
      fadeImage.setToValue(1.0);
      fadeImage.setCycleCount(1);

      // Root layout container
      AnchorPane root = new AnchorPane();
      root.getChildren().addAll(container);

      // Setup RFID reader ports and specimen data
      attachEventListeners();
      initData();

      // Setup RFID reader timer
      new RFIDTimer();
      System.out.println("Task scheduled.");

      Scene scene = new Scene(root, 600, 450);

      primaryStage.setFullScreen(true);
      primaryStage.setTitle("Virtual Microscope");
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
    * Attach RFID Reader SerialEventListener. @param comPort The COM port to
    * initialize.
    */
   private void attachEventListeners() {
      // COM_A
      serialPortA = new SerialPort(Constants.COM_A);
      try {
         serialPortA.openPort();
         serialPortA.setParams(Constants.BAUDRATE_2400, SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
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
//            serialPortB.openPort();
         serialPortB.setParams(Constants.BAUDRATE_2400, SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
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
//            serialPortC.openPort();
         serialPortC.setParams(Constants.BAUDRATE_2400, SerialPort.DATABITS_8,
                 SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
         int mask = SerialPort.MASK_RXCHAR;
         serialPortC.setEventsMask(mask);
         serialPortC.addEventListener(new COM_C_listener());

      }
      catch (SerialPortException ex) {
         System.out.println(ex);
      }

      // Arduino
//        arduinoPort = new SerialPort(Constants.ARDUINO_PORT);
//        try
//        {
//            arduinoPort.openPort();//Open port
//            arduinoPort.setParams(9600, 8, 1, 0);//Set params
//        }
//        catch(SerialPortException ex)
//        {
//            System.out.println(ex);
//        } 
   }

   private static void initData() {
      // Set Specimen Names
      specimenNameData.put(Constants.VOLCANIC_ROCK_ID, Constants.VOLCANIC_ROCK);
//        specimenNameData.put(Constants.SNAKE_SKIN_ID, Constants.SNAKE_SKIN);
//        specimenNameData.put(Constants.BIRD_FEATHER_ID, Constants.BIRD_FEATHER);
//        specimenNameData.put(Constants.BEETLE_ID, Constants.BEETLE);
//        specimenNameData.put(Constants.FOSSIL_ID, Constants.FOSSIL);
//        specimenNameData.put(Constants.SHARK_TOOTH_ID, Constants.SHARK_TOOTH);
//        specimenNameData.put(Constants.CORAL_ID, Constants.CORAL);
//        specimenNameData.put(Constants.BUTTERFLY_ID, Constants.MAPLE_LEAF);

      // Set Fact data
      factData.put(Constants.VOLCANIC_ROCK_ID, Constants.VOLCANIC_ROCK_FACTS);
//        factData.put(Constants.SNAKE_SKIN_ID, Constants.SNAKE_SKIN_FACTS);
//        factData.put(Constants.BIRD_FEATHER_ID, Constants.BIRD_FEATHER_FACTS);
//        factData.put(Constants.BEETLE_ID, Constants.BEETLE_FACTS);
//        factData.put(Constants.FOSSIL_ID, Constants.FOSSIL_FACTS);
//        factData.put(Constants.SHARK_TOOTH_ID, Constants.SHARK_TOOTH_FACTS);
//        factData.put(Constants.CORAL_ID, Constants.CORAL_FACTS);
//        factData.put(Constants.BUTTERFLY_ID, Constants.MAPLE_LEAF_FACTS);

      // TODO set microscopic picture data
      imageData.put(Constants.VOLCANIC_ROCK_ID, volcanicRockImage);
//        imageData.put(Constants.SNAKE_SKIN_ID, snakeSkinImage);
//        imageData.put(Constants.BIRD_FEATHER_ID, birdImage);
//        imageData.put(Constants.BEETLE_ID, beetleImage);
//        imageData.put(Constants.FOSSIL_ID, fossilImage);
//        imageData.put(Constants.SHARK_TOOTH_ID, sharkToothImage);
//        imageData.put(Constants.CORAL_ID, coralImage);
//        imageData.put(Constants.BUTTERFLY_ID, mapleLeafImage);

      // TODO set video data
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
   public static void factoidsHandler() {
      System.out.println("Factoids Handler...");

      Platform.runLater(new Runnable() {
         @Override
         public void run() {
//                try {
//                    // Turn on indicator LED
//                    System.out.println("Write to Arduino successful: " 
//                            + arduinoPort.writeInt(1));
//                } 
//                catch (SerialPortException ex) {
//                    System.out.println(ex);
//                }

            // Remove the image if there is one
            if (contentPane.getChildren().contains(microscopeImageView)) {
               contentPane.getChildren().remove(microscopeImageView);
            }

            // Remove the video block if there is one
            if (contentPane.getChildren().contains(videoView)) {
               contentPane.getChildren().remove(videoView);
            }

            // Display specimen info and facts
            currentReader.setText(Constants.READER_1);
            specimenName.setText(getSpecimenName(tagId));
            facts.setText(getSpecimenFacts(tagId));
         }
      });
   }

   /*
    * Microscope image handler
    */
   public static void microscopeHandler() {
      System.out.println("Microscope handler...");

      Platform.runLater(new Runnable() {
         @Override
         public void run() {
                // Turn on indicator LED
            //sendToArduino(1);

            // Remove the video block if there is one
            if (contentPane.getChildren().contains(videoView)) {
               contentPane.getChildren().remove(videoView);
            }
            // Add image view if it has been removed from the content pane
            if (!contentPane.getChildren().contains(microscopeImageView)) {
               contentPane.getChildren().add(microscopeImageView);
               fadeImage.play();
            }
            // Display specimen info and image
            currentReader.setText(Constants.READER_2);
            specimenName.setText(getSpecimenName(tagId));
            facts.setText("");
            microscopeImageView.setImage(getSpecimenImage(tagId));
         }
      });
   }

   /*
    * Video handler.
    */
   public static void videoHandler() {
      System.out.println("Video Handler...");

      Platform.runLater(new Runnable() {
         @Override
         public void run() {
            RFIDTimer.timer.cancel();
            System.out.println("Timer cancelled.");

            // Remove the image if there is one
            if (contentPane.getChildren().contains(microscopeImageView)) {
               contentPane.getChildren().remove(microscopeImageView);
            }

            // Add Video view if not already there
            if (!contentPane.getChildren().contains(videoView)) {
               contentPane.getChildren().add(videoView);
            }

            // Update labels, clear facts
            currentReader.setText(Constants.READER_3);
            specimenName.setText(getSpecimenName(tagId));
            facts.setText("");

            MediaPlayer video = getVideoMedia(tagId);
            videoView.setMediaPlayer(video);
            video.play();  // Play video

            // Runs when a video starts playing
            video.setOnPlaying(new Runnable() {
               @Override
               public void run() {
                  try {
                     // Close serial ports while video is playing
                     if (serialPortA.isOpened())
                        serialPortA.closePort();
                     if (serialPortB.isOpened())
                        serialPortB.closePort();
                     if (serialPortC.isOpened())
                        serialPortC.closePort();
                  }
                  catch (SerialPortException e) {
                     System.out.println(e);
                  }
               }
            });

            // Runs when a video is finished playing
            video.setOnEndOfMedia(new Runnable() {
               @Override
               public void run() {
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
                     facts.setText("Select a specimen and place it on 1 to learn more!");
                     specimenName.setText("");

                     // Restart timer
                     new RFIDTimer();
                     System.out.println("Task scheduled.");
                  }
                  catch (SerialPortException e) {
                     System.out.println(e);
                  }
               }
            });
         }
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

   private static void sendToArduino(int message) throws InterruptedException {

   }
}
