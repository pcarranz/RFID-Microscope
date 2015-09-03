package rfid.microscope;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 *
 * @author Patricia Carranza
 */
public interface Constants
{
   public static final int BAUDRATE_2400 = 2400;
   public static final String UNKNOWN = "Unknown Specimen";
   public static final String BEGIN_PROMPT = "Select a specimen and place it on 1 to learn more!";
   
    // COM Port Numbers
    public static final String COM_A = "COM4";
    public static final String COM_B = "COM5";
    public static final String COM_C = "COM6";
    public static final String ARDUINO_PORT = "COM8";
    
    // Current RFID Reader Numbers
    public static final String READER_1 = "1";
    public static final String READER_2 = "2";
    public static final String READER_3 = "3";
    
    // RFID Tag IDs
    public static final String VOLCANIC_ROCK_ID  = "\n160019EEB2\r";
    public static final String SNAKE_SKIN_ID     = "\n160019BB76\r";
    public static final String BIRD_FEATHER_ID   = "\n160019A7DC\r";
    public static final String BEETLE_ID         = "\n160019A7F4\r";
    public static final String FOSSIL_ID         = "\n160019B4E2\r";
    public static final String SHARK_TOOTH_ID    = "\n160019CFC9\r";
    public static final String CORAL_ID          = "\n16001A034D\r";
    public static final String BUTTERFLY_ID      = "\n70006D5A37\r";
    public static final String TOMATO_SEEDS_ID   = "\n70006D2365\r";
    public static final String MAPLE_LEAF_ID     = "\n160019E960\r";
    
    // Specimen Names
    public static final String VOLCANIC_ROCK  = "Obsidian - Volcanic Rock";
    public static final String SNAKE_SKIN     = "Snake Skin";
    public static final String BIRD_FEATHER   = "Quail Bird Feather";
    public static final String BEETLE         = "Chafer Beetle";
    public static final String FOSSIL         = "Fossil Ammonite";
    public static final String SHARK_TOOTH    = "Bull Shark Tooth";
    public static final String CORAL          = "Coral";
    public static final String BUTTERFLY      = "Tiger Butterfly";
    public static final String TOMATO_SEEDS   = "Tomato Seeds";
    public static final String MAPLE_LEAF     = "Maple Leaf";
    
    // Specimen Facts
    public static final String DID_YOU_KNOW = "Did you know?\n\n";
    
    public static final String VOLCANIC_ROCK_FACTS  = 
              "Classification: Intermediate Igneous rock\n"
            + "Texture: Smooth\n\n"
            + DID_YOU_KNOW
            + "• Obsidian is an igneous rock that forms when molten rock cools "
            + "very fast. The fast cooling does not let crystals form.\n"
            + "• Obsidian can only form near active volcanoes.\n"
            + "• When obsidian breaks, the pieces are very sharp which is why it"
            + " was used for tools in the Stone Age.\n"
            + "• Obsidian is considered a \"young\" rock. It is rarely older than"
            + " 20 million years old.\n"
            + "• Obsidian often has small bubbles trapped in the glass caused"
            + " by water vapor.";
    
    public static final String SNAKE_SKIN_FACTS     = 
            "Type: Reptile\n"
            + "Diet: Carnivore\n"
            + "Scientific Name: Enhydria chinesis (Chinese water snake)\n"
            + DID_YOU_KNOW
            + "• Snakes shed their skin nearly 3 times a year in a process that"
            + " usually lasts a few days. The process is called molting.\n"
            + "• There are more than 3,000 species of snakes in the world.\n"
            + "• Snakes aren't slimy, their scales are smooth and dry.\n"
            + "• Antarctica is the only place in the world where snakes are not found.\n"
            + "• Snakes don't smell with their noses like humans. They have a"
            + " split tongue that they use to smell and taste.";
    
    public static final String BIRD_FEATHER_FACTS   = 
              "Type: Bird\n"
            + "Diet: Omnivore\n"
            + "Scientific Name: Coturnix coturnix (California Quail)\n\n"
            + DID_YOU_KNOW
            + "• Birds have both feathers and scales. You can find scales on the"
            + " legs and feet of most birds.\n"
            + "• There are four basic wing shapes that apply to most flying birds:"
            + " Elliptical, long pointed, long narrow, and broad.\n"
            + "• Feathers are very strong and flexible to allow both lift and"
            + " forward movement. Feathers can bend at almost right angles.\n"
            + "• California quail usually live in hot, dry areas without much water.\n"
            + " They mostly gain nutrients through moist plants.\n"
            + "• Quails can lay 10 to 20 eggs at one time.";
    
    public static final String BEETLE_FACTS         = 
              "Type: Insect\n"
            + "Diet: Carnivore\n"
            + "Scientific Name: Cetonia aurata\n\n"
            + DID_YOU_KNOW
            + "• Adult beetles have two sets of wings.\n"
            + "• Most beetles only live for a year.\n"
            + "The metallic green coloring of the beetle is caused by the "
            + "reflection of light.\n"
            + "• \"Fireflies\" and \"Lightning bugs\" are also beetles. They "
            + "glow in the dark to communicate.\n"
            + "• Beetles cannot see very well, so they communicate using sound or"
            + "vibrations (pheromones).\n"
            + "• Beetles live everywhere – from hot deserts to the polar ice caps.\n"
            + "• They feed on leaves, fruits, flowers and buds of a range of "
            + "plants including roses.";
    
    public static final String FOSSIL_FACTS         = 
              "Type: Invertebrate\n"
            + "Diet: Carnivore\n"
            + "Scientific Name: Ammonitida\n\n"
            + DID_YOU_KNOW
            + "• Ammonites lived in the sea 240-65 million years ago.\n"
            + "• They became extinct along with the dinosaurs.\n"
            + "• Scientists use the various shapes and sizes of ammonite "
            + "shells that appeared and disappeared through the ages to date "
            + "other fossils.\n"
            + "• Ammonites were predatory, squid-like creatures that lived inside "
            + "coil-shaped shells.\n"
            + "• They ate small fish, crabs, lobsters, and shrimps.\n"
            + "• Ammonites constantly built new shells as they grew, but only"
            + " lived in the outer chamber.";
    
    public static final String SHARK_TOOTH_FACTS    = 
              "Composition: Calcium phosphate minerals\n"
            + DID_YOU_KNOW
            + "• Sharks have the most powerful jaws on the planet and are able"
            + " to move both their upper and lower jaws.\n"
            + "• A shark bites with it's lower jaw first and then its upper jaw.\n"
            + "• A shark may grow and use over 20,000 teeth in its lifetime!\n"
            + "• Each type of shark has a different shaped tooth depending on their "
            + " diet. For example, carnivorous sharks have much pointier teeth.\n"
            + "• Sharks never run out of teeth.  If one is lost, another spins"
            + " forward from the rows and rows of backup teeth.\n"
            + "• Most sharks have about 5 rows of teeth at any time.";
    
    public static final String CORAL_FACTS       = 
              "Type: Invertebrate\n"
            + "Diet: Carnivore\n"
            + "Scientific Name: Anthozoa\n\n"
            + DID_YOU_KNOW
            + "• Coral are tiny, soft-bodied organisms related to jellyfish.\n"
            + "• They have a protective limestone skeleton, which forms the"
            + " structure of coral reefs.\n"
            + "• Coral reefs have been around for millions of years.\n"
            + "• The reefs grow best in warm, shallow, clear, sunny and moving water.\n"
            + "• Coral reefs contain 25% of the world's marine fish species.\n"
            + "• Coral polyps are actually semi-see-through animals. They get their "
            + "many colors from the billions of colorful algae they host.\n";
    
    public static final String BUTTERFLY_FACTS  =
            "Type: Insect\n"
            + "Diet: Herbivore\n"
            + "Scientific Name: Rhopalocera\n\n"
            + DID_YOU_KNOW
            + "• Butterflies taste through their feet. They usually feed on the"
            + " sweet water plants make.\n"
            + "• Butterflies can live anywhere from 2 days to 11 months.\n"
            + "• Most butterflies live in tropical rainforests, but they can"
            + " live in all climates and heights of the world.\n"
            + "• Butterflies are colorful so that they can mate, absorb heat, and"
            + " blend in with other flowers while feeding.\n"
            + "• Scientists estimate that there are 28,000 species of butterflies"
            + " throughout the world.";
    
    public static final String TOMATO_SEEDS_FACTS =
            "Type: Plant\n"
            + "Scientific Name: Solanum lycopersicom\n"
            + DID_YOU_KNOW
            + "• A tomato is considered a fruit since a tomato has seeds and"
            + " grows from a flowering plant.\n"
            + "• They originated in the South American Andes near what is now"
            + " the country of Peru.\n"
            + "• Tomatoes were first used as a food by the Aztec's in Southern"
            + " Mexico.\n"
            + "• Tomatoes have been grown in space before!\n"
            + "• It usually takes about 6-8 weeks to grow tomatoes after planting seeds.\n"
            + "• Most tomatoes are red, but there are also green, yellow, orange,"
            + " pink, black, brown, white, and purple tomatoes.";
    
    public static final String MAPLE_LEAF_FACTS         = 
              "Type: Plant\n"
            + "Scientific Name: Acer (Maple Tree)\n"
            + DID_YOU_KNOW
            + "• Leaves change color from green to different shades of yellow, "
            + "orange and red during the autumn.\n"
            + "• Maple syrup is made from a Maple Tree's sap.\n"
            + "• A maple leaf can be found in the center of Canada's flag.\n"
            + "• Leaves are divided in 3 to 9 sections. They are arranged opposite "
            + "to each other on the branches.\n"
            + "• Maple trees produce winged seeds which move like a helicopter"
            + " when they fall from the trees.";
    
    // Specimen Microscopic Image File Paths
    public static final String VOLCANIC_ROCK_IMG  = "/rfid/microscope-images/obsidian.jpg";
    public static final String SNAKE_SKIN_IMG     = "/rfid/microscope-images/snake-skin.jpg";
    public static final String BIRD_FEATHER_IMG   = "/rfid/microscope-images/quail-feather.jpg";
    public static final String BEETLE_IMG         = "/rfid/microscope-images/beetle.jpg";
    public static final String FOSSIL_IMG         = "/rfid/microscope-images/fossil-ammonite.jpg";
    public static final String SHARK_TOOTH_IMG    = "/rfid/microscope-images/shark-tooth.jpg";
    public static final String CORAL_IMG          = "/rfid/microscope-images/coral-internet.jpg";
    public static final String BUTTERFLY_IMG      = "/rfid/microscope-images/butterfly.jpg";
    public static final String TOMATO_SEEDS_IMG   = "/rfid/microscope-images/tomato-seed-internet.jpg";
    public static final String MAPLE_LEAF_IMG     = "/rfid/microscope-images/maple-leaf.jpg";
    
    // Specimen Microscopic Image Objects
    public static final Image volcanicRockImage = new Image(VOLCANIC_ROCK_IMG);
    public static final Image snakeSkinImage    = new Image(SNAKE_SKIN_IMG);
    public static final Image birdImage         = new Image(BIRD_FEATHER_IMG);
    public static final Image beetleImage       = new Image(BEETLE_IMG);
    public static final Image fossilImage       = new Image(FOSSIL_IMG);
    public static final Image sharkToothImage   = new Image(SHARK_TOOTH_IMG);
    public static final Image coralImage        = new Image(CORAL_IMG);
    public static final Image butterflyImage    = new Image(BUTTERFLY_IMG);
    public static final Image tomatoSeedsImage  = new Image(TOMATO_SEEDS_IMG);
    public static final Image mapleLeafImage    = new Image(MAPLE_LEAF_IMG);
    
    // Specimen Video Objects
    public static final String VOLCANIC_ROCK_VID  = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/obsidian-video.mp4";
    public static final String SNAKE_SKIN_VID     = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/snake-skin-video.mp4";
    public static final String BIRD_FEATHER_VID   = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/bird-video.mp4";
    public static final String BEETLE_VID         = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/beetle-video.mp4";
    public static final String FOSSIL_VID         = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/ammonite-video.mp4";
    public static final String SHARK_TOOTH_VID    = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/shark-video.mp4";
    public static final String CORAL_VID          = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/coral-video.mp4";
   public static final String BUTTERFLY_VID       = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/butterfly-video.mp4";
   public static final String TOMATO_SEEDS_VID    = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/tomato-seed-video.mp4";
   public static final String MAPLE_LEAF_VID      = "file:///C:/Users/slocm-microscope/Documents/GitHub/RFID-Microscope/src/rfid/video-files/maple-video.mp4";
    
    public static final Media volcanicRockMedia = new Media(VOLCANIC_ROCK_VID);
    public static final Media snakeSkinMedia    = new Media(SNAKE_SKIN_VID);
    public static final Media birdMedia         = new Media(BIRD_FEATHER_VID);
    public static final Media beetleMedia       = new Media(BEETLE_VID);
    public static final Media fossilMedia       = new Media(FOSSIL_VID);
    public static final Media sharkMedia        = new Media(SHARK_TOOTH_VID);
    public static final Media coralMedia        = new Media(CORAL_VID);
    public static final Media butterflyMedia    = new Media(BUTTERFLY_VID);
    public static final Media tomatoMedia       = new Media(TOMATO_SEEDS_VID);
    public static final Media mapleMedia        = new Media(MAPLE_LEAF_VID);
}
