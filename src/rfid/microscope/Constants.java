/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfid.microscope;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Patty
 */
public interface Constants
{
   public static final int BAUDRATE_2400 = 2400;
   
    // COM Port Numbers
    public static final String COM_A = "COM4";
    public static final String COM_B = "COM5";
    public static final String COM_C = "COM6";
//    public static final String ARDUINO_PORT = "COM3";
    
    // Current RFID Reader Numbers
    public static final String READER_1 = "1";
    public static final String READER_2 = "2";
    public static final String READER_3 = "3";
    
    // RFID Tag IDs
    public static final String VOLCANIC_ROCK_ID  = "\n020080FA93\r";
    public static final String SNAKE_SKIN_ID     = "\n02007A5908\r";
    public static final String BIRD_FEATHER_ID   = "\n02007A5E18\r";
    public static final String BEETLE_ID         = "\n02007F5281\r";
    public static final String FOSSIL_ID         = "\n0200B01E11\r";
    public static final String SHARK_TOOTH_ID    = "\n0200B2D1CF\r";
    public static final String CORAL_ID          = "\n0200802557\r";
    public static final String BUTTERFLY_ID      = "\n70006D5A37\r";
    public static final String TOMATO_SEEDS_ID   = "\n70006D2365\r";
    public static final String MAPLE_LEAF_ID     = "\n0200802558\r";
    
    
    // Specimen Names
    public static final String VOLCANIC_ROCK  = "Obsidian - Volcanic Rock";
    public static final String SNAKE_SKIN     = "Snake Skin";
    public static final String BIRD_FEATHER   = "Quail Bird Feather";
    public static final String BEETLE         = "Chafer Beetle";
    public static final String FOSSIL         = "Fossil Ammonite";
    public static final String SHARK_TOOTH    = "Bull Shark Tooth";
    public static final String CORAL          = "Coral";
    public static final String BUTTERFLY      = "Tiger Butterfly";
    public static final String MAPLE_LEAF     = "Maple Leaf";
    public static final String TOMATO_SEEDS   = "Tomato Seeds";
    
    // Specimen Facts
    public static final String DID_YOU_KNOW = "Did you know?\n\n";
    
    public static final String VOLCANIC_ROCK_FACTS  = 
              "Classification: Intermediate Igneous rock\n\n"
            + "Did you know?\n\n"
            + "- Obsidian is an igneous rock that forms when molten rock cools "
            + "very rapidly. The result is a rock that cooled so fast, crystals "
            + "did not get a chance to form.\n"
            + "- Obsidian is a volcanic glass with a smooth and uniform structure.\n"
            + "- Obsidian can only form near active volcanoes.\n"
            + "- When obsidian breaks, the pieces are very sharp which is why it "
            + "was used as tools in the Stone Age.\n"
            + "- Although most rocks that form in the Earth's crust are very old, "
            + "obsidian is rarely older than 20 million years old which is young "
            + "for a rock.\n"
            + "- A certain amount of water is always present in obsidian. It is "
            + "common to see small bubbles trapped in the glass caused by water vapor.";

    
    public static final String SNAKE_SKIN_FACTS     = 
              "Did you know?\n\n"
            + "- Snakes don’t have eyelids.\n"
            + "- Snakes can’t bite food so have to swallow it whole.\n"
            + "- Snakes have internal ears but not external ones.";
    
    public static final String BIRD_FEATHER_FACTS   = 
              "Type: Bird\n"
            + "Diet: Omnivore\n"
            + "Scientific Name: Coturnix coturnix\n\n"
            + "- Birds have both feathers and scales. You can find scales on the legs and feet of most birds.\n"
            + "- There are four basic wing shapes that apply to most flying birds: Elliptical, long pointed, long narrow, and broad.\n"
            + "- Feathers are incredibly strong and yet are incredibly flexible. To allow both lift and forward movement, feathers can\n"
            + " bend at almost a right angles.";
    
    public static final String BEETLE_FACTS         = 
              "Type: Insect\n"
            + "Diet: Carnivore\n"
            + "Scientific Name: Coleoptera\n\n"
            + "- Adult beetles have two sets of wings.\n"
            + "- Most beetles only live for a year.\n"
            + "- \"Fireflies\" and \"Lightning bugs\" are also beetles. They glow in the dark to communicate.\n"
            + "- Beetles cannot see very well, so they communicate using pheromones, sounds or vibrations.\n"
            + "Beetles live everywhere – from hot deserts to the polar ice caps.";
    
    public static final String FOSSIL_FACTS         = 
              "Type: Invertebrate\n"
            + "Scientific Name: Ammonitida\n\n"
            + "- Ammonites lived in the sea 240-65 million years ago.\n"
            + "- They became extinct along with dinosaurs.";
    
    public static final String SHARK_TOOTH_FACTS    = 
              "- Bull shark facts";
    
    public static final String CORAL_FACTS       = 
              "Type: Invertebrate\n"
            + "Diet: Carnivore\n"
            + "Scientific Name: Anthozoa\n\n"
            + "- Coral polyps are tiny, soft-bodied organisms related to jellyfish.\n"
            + "- They have a protective limestone skeleton, which forms the structure of coral reefs.\n"
            + "- Coral reefs have been around for millions of years.\n"
            + "- The reefs grow best in warm, shallow, clear, sunny and moving water.\n"
            + "- Coral reefs contain 25% of the world's marine fish species.";
    
    public static final String MAPLE_LEAF_FACTS         = 
              "Type: Plant\n"
            + "Scientific Name: Acer (Maple Tree)\n"
            + "- Leaves change color from green to different shades of yellow, orange and red during the autumn.\n"
            + "- Maple syrup is made from a Maple Trees sap.\n"
            + "- A maple leaf can be found in the center of Canada's flag.";
    
    // Specimen Microscopic Image File Paths
    public static final String VOLCANIC_ROCK_IMG  = "/rfid/microscope-images/volcanic-rock.jpg";
//    public static final String SNAKE_SKIN_IMG     = "/rfid/microscope-images/snake-skin.jpg";
//    public static final String BIRD_FEATHER_IMG   = "/rfid/microscope-images/bird-feather.JPG";
//    public static final String BEETLE_IMG         = "/rfid/microscope-images/beetle.jpg";
//    public static final String FOSSIL_IMG         = "/rfid/microscope-images/fossil.jpg";
//    public static final String SHARK_TOOTH_IMG    = "/rfid/microscope-images/cotton.jpg";
//    public static final String CORAL_IMG          = "/rfid/microscope-images/coral.jpg";
//    public static final String MAPLE_LEAF_IMG     = "/rfid/microscope-images/leaf.jpg";
//    public static final String TOMATO_SEEDS_IMG   = "/rfid/microscope-images/tomato.jpg";
    
    // Specimen Microscopic Image Objects
    public static final Image volcanicRockImage = new Image(VOLCANIC_ROCK_IMG);
//    public static final Image snakeSkinImage    = new Image(SNAKE_SKIN_IMG);
//    public static final Image birdImage         = new Image(BIRD_FEATHER_IMG);
//    public static final Image beetleImage       = new Image(BEETLE_IMG);
//    public static final Image fossilImage       = new Image(FOSSIL_IMG);
//    public static final Image sharkToothImage   = new Image(SHARK_TOOTH_IMG);
//    public static final Image coralImage        = new Image(CORAL_IMG);
//    public static final Image mapleLeafImage    = new Image(MAPLE_LEAF_IMG);
//    public static final Image tomatoSeedsImage  = new Image(TOMATO_SEEDS_IMG);
    
    
    // Specimen Video Objects
    public static final String VOLCANIC_ROCK_VID  = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/coral-HD.mp4";
//    public static final String SNAKE_SKIN_VID     = "file:///C:/Users/Patty/Documents/CPE461/RFID-Microscope/src/rfid/video-files/coral-HD.mp4";
//    public static final String BIRD_FEATHER_VID   = "file:///C:/Users/Patty/Documents/CPE461/RFID-Microscope/src/rfid/video-files/birds-SD.mp4";
//    public static final String BEETLE_VID         = "file:///C:/Users/Patty/Documents/CPE461/RFID-Microscope/src/rfid/video-files/coral-HD.mp4";
//    public static final String FOSSIL_VID         = "file:///C:/Users/Patty/Documents/CPE461/RFID-Microscope/src/rfid/video-files/coral-HD.mp4";
//    public static final String SHARK_TOOTH_VID    = "file:///C:/Users/Patty/Documents/CPE461/RFID-Microscope/src/rfid/video-files/coral-HD.mp4";
//    public static final String CORAL_VID       = "file:///C:/Users/slocm-microscope/Documents/NetbeansProjects/RFID-Microscope/src/rfid/video-files/coral-HD.mp4";
//    public static final String MAPLE_LEAF_VID     = "file:///C:/Users/Patty/Documents/CPE461/RFID-Microscope/src/rfid/video-files/cotton-HD.mp4";
    
    public static final Media volcanicRockMedia = new Media(VOLCANIC_ROCK_VID);
//    public static final Media snakeSkinMedia    = new Media(SNAKE_SKIN_VID);
//    public static final Media birdMedia         = new Media(BIRD_FEATHER_VID);
//    public static final Media beetleMedia       = new Media(BEETLE_VID);
//    public static final Media fossilMedia       = new Media(FOSSIL_VID);
//    public static final Media boneMedia         = new Media(SHARK_TOOTH_VID);
//    public static final Media coralMedia     = new Media(CORAL_VID);
//    public static final Media cottonMedia       = new Media(MAPLE_LEAF_VID);
    
    public static final MediaPlayer volcanicRockVideo   = new MediaPlayer(volcanicRockMedia);
//    public static final MediaPlayer snakeSkinVideo      = new MediaPlayer(snakeSkinMedia);
//    public static final MediaPlayer birdVideo           = new MediaPlayer(birdMedia);
//    public static final MediaPlayer beetleVideo         = new MediaPlayer(beetleMedia);
//    public static final MediaPlayer fossilVideo         = new MediaPlayer(fossilMedia);
//    public static final MediaPlayer boneVideo           = new MediaPlayer(boneMedia);
//    public static final MediaPlayer coralVideo       = new MediaPlayer(coralMedia);
//    public static final MediaPlayer cottonVideo         = new MediaPlayer(cottonMedia);
}
