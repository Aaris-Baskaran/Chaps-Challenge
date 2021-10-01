package persistency;

import domain.Game;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * A class which manages the game states by saving and loading them into the Game.
 *
 * @author Kelland Vuong 300533094
 */
public class StateManager {
  
  /**
   * The file array which contain the level files.
   */
  private static File[] files;
  
  /**
   * The levels as a State.
   */
  private final TreeMap<Integer, State> levels;
  
  /**
   * the level number to be loaded into the game.
   */
  private final int levelNum;
  
  /**
   * A default constructor which constructs the State Manager.
   */
  public StateManager() {
    files = findFiles();
    organizeFiles();
    levels = createLevels();
    levelNum = setLevel();
  }
  
  /**
   * A method that finds all level files within the levels folder.
   *
   * @return the level files
   */
  public static File[] findFiles() {
    
    // Gets the folder
    File folder = new File("levels");
    
    // Creates a filter to find files starting with level
    FilenameFilter filter = new FilenameFilter() {
      
      public boolean accept(File f, String name) {
        return name.startsWith("level");
      }      
    };
    
    // returns all level files as an array
    return folder.listFiles(filter);
  }
  
  /**
   * A method that organizes the level files.
   */
  private void organizeFiles() {
    // creates a temporary file array
    File[] level  = new File[2];
    
    //  iteration that determines order in array
    for (File f : files) {
      if (f.getName().startsWith("level1")) {
        level[0] = f;
      } else if (f.getName().startsWith("level2")) {
        level[1] = f;
      }
    }
    
    // files become organised
    files = level;
  }
  
  /**
   * A method which creates a list of levels as States.
   *
   * @return the levels
   */
  public TreeMap<Integer, State> createLevels() {
    // Initialises a TreeMap to store levels
    TreeMap<Integer, State> levelList = new TreeMap<>();
    
    // iteration to store each level as a State
    for (int i = 1; i <= files.length; ++i) {
      levelList.put(i, loadState(i));
    }
    
    // returns as a TreeMap of States
    return levelList;
  }
  
  /**
   * A method which loads the game depending on how the player exits the game.
   * Running the game for the first time will load level 1 by default.
   *
   * @return the state
   */
  public State loadGame() {
    // Tries to get the saved.xml file
    File saved = new File("levels/saved.xml");
    
    // detects if saved.xml exists
    if (saved.isFile()) {
      // loads the saved state
      return loadState(saved);
    } else {
      // loads the previous unfinished level or loads level 1 by default
      return getLevels().get(levelNum);
    }
  }
  
  /**
   * A method which loads the game state when given a file by reading and parsing an XML file.
   *
   * @param file file which is read
   * @return the state from a file
   */
  public State loadState(File file) {
    
    // Creates an empty state
    State state = null;
    
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
    try {
      
      // initialises the parser
      DocumentBuilder db = dbf.newDocumentBuilder();
      
      // parses the file
      Document doc = db.parse(file);
      
      Element element = doc.getDocumentElement();
      
      // gets the variables
      String level = element.getElementsByTagName("Level").item(0).getTextContent();
      String time = element.getElementsByTagName("Time").item(0).getTextContent();
      
      String[] maze = new String[35];
      
      // iteration to store each row as a String into a String array
      for (int row = 0; row < 35; ++row) {
        maze[row] = element.getElementsByTagName("Row").item(row).getTextContent();
      }
      
      String keys = element.getElementsByTagName("Keys").item(0).getTextContent();
      String x = element.getElementsByTagName("X").item(0).getTextContent();
      String y = element.getElementsByTagName("Y").item(0).getTextContent();
      String dir = element.getElementsByTagName("Dir").item(0).getTextContent();
      String treasure = element.getElementsByTagName("Chips").item(0).getTextContent();
      String chips = element.getElementsByTagName("Chips").item(1).getTextContent();
      
      char direction = dir.charAt(0);
      
      int levelNum = Integer.parseInt(level);
      int timeLeft = Integer.parseInt(time);
      int posX = Integer.parseInt(x);
      int posY = Integer.parseInt(y);
      int chipsInChest = Integer.parseInt(treasure);
      int chipsLeft = Integer.parseInt(chips);
      
      // stores the state created via parsing
      state = new State(levelNum, timeLeft, maze, keys, posX, posY, direction, chipsInChest, 
                        chipsLeft);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    
    return state;   
  }
  
  /**
   * A method which loads the game state when given a number by reading and parsing an XML file.
   *
   * @param lvl level number
   * @return the level as a State
   */
  public State loadState(int lvl) {
    
    // Creates an empty state
    State state = null;
    
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
    try {
      
      // initialises the parser
      DocumentBuilder db = dbf.newDocumentBuilder();
      
      // parses the file
      Document doc = db.parse(files[lvl - 1]);
      
      Element element = doc.getDocumentElement();
      
      // gets the variables
      String level = element.getElementsByTagName("Level").item(0).getTextContent();
      String time = element.getElementsByTagName("Time").item(0).getTextContent();
      
      String[] maze = new String[35];
      
      // iteration to store each row as a String into a String array
      for (int row = 0; row < 35; ++row) {
        maze[row] = element.getElementsByTagName("Row").item(row).getTextContent();
      }
      
      String keys = element.getElementsByTagName("Keys").item(0).getTextContent();
      String x = element.getElementsByTagName("X").item(0).getTextContent();
      String y = element.getElementsByTagName("Y").item(0).getTextContent();
      String dir = element.getElementsByTagName("Dir").item(0).getTextContent();
      String treasure = element.getElementsByTagName("Chips").item(0).getTextContent();
      String chips = element.getElementsByTagName("Chips").item(1).getTextContent();
      
      char direction = dir.charAt(0);
      
      int levelNum = Integer.parseInt(level);
      int timeLeft = Integer.parseInt(time);
      int posX = Integer.parseInt(x);
      int posY = Integer.parseInt(y);
      int chipsInChest = Integer.parseInt(treasure);
      int chipsLeft = Integer.parseInt(chips);
      
      // stores the state created via parsing
      state = new State(levelNum, timeLeft, maze, keys, posX, posY, direction, chipsInChest, 
                        chipsLeft);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    
    return state;   
  }

  /**
   * A method which loads a game state via file selection.
   *
   * @return the selected state
   */
  public State loadSelect() {
    
    // Creates an empty state
    State state = null;
    
    // opens file selector
    JFileChooser j = new JFileChooser("levels");
    
    int r = j.showOpenDialog(null);
    
    // runs once file is selected
    if (r == JFileChooser.APPROVE_OPTION) {
      // creates the game state of selected XML file
      state = loadState(j.getSelectedFile());
    }
    
    return state;   
  }

  /**
   * A method which saves the current game state and writes it into a XML file.
   *
   * @param game current game
   */
  public void saveState(Game game) {
    try {
      // Tries to get saved.xml file
      File saved = new File("levels/saved.xml");
      
      // Creates an empty file if saved.xml does not exist
      saved.createNewFile();
      
      FileWriter writer = new FileWriter(saved);
      
      // writes into the file
      writer.write("<?xml version = \"1.0\"?>\n");
      writer.write("<Game>\n");
      writer.write("  <Level>" + game.getLevel() + "</Level>\n");
      writer.write("  <Time>" + game.getTime() + "</Time>\n");
      
      // writes multiple lines of rows
      String[] maze = game.toString().split("\n");
      for (int row = 0; row < 35; ++row) {
        writer.write("  <Row>");
        writer.write(maze[row]);
        writer.write("</Row>\n");
      }
      
      writer.write("  <Keys>" + game.getInventory() + "</Keys>\n");
      writer.write("  <X>" + game.getChapPos().getX() + "</X>\n");
      writer.write("  <Y>" + game.getChapPos().getY() + "</Y>\n");
      writer.write("  <Dir>" + Game.getChapDirection() + "</Dir>\n");
      writer.write("  <Chips>" + game.getTreasureChestSize() + "</Chips>\n");
      writer.write("  <Chips>" + game.getChipsRemaining() + "</Chips>\n");
      writer.write("</Game>\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * A method which saves the current level and writes it into a XML file.
   *
   * @param game current game
   */
  public void saveLevel(Game game) {
    try {
      // Tries to get init.xml
      File saved = new File("levels/init.xml");
      
      // Creates an empty file if init.xml does not exist
      saved.createNewFile();
      
      // writes into the file
      FileWriter writer = new FileWriter(saved);
      writer.write("<?xml version = \"1.0\"?>\n");
      writer.write("<Game>\n");
      writer.write("  <Level>" + game.getLevel() + "</Level>\n");
      writer.write("</Game>\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * A method which sets the previous unfinished level or sets it to 1 by default.
   *
   * @return the level number
   */
  public int setLevel() {
    
    // default level
    int lvl = 1;
    
    // Tries to get init.xml
    File init = new File("levels/init.xml");
    
    // if init.xml does not exist
    if (!init.isFile()) { 
      return lvl; 
    }
    
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
    try {
      
      // initialises the parser
      DocumentBuilder db = dbf.newDocumentBuilder();
      
      // parses the file
      Document doc = db.parse(init);
      
      Element element = doc.getDocumentElement();
      
      // gets the level
      String level = element.getElementsByTagName("Level").item(0).getTextContent();      
      lvl = Integer.parseInt(level);

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    
    return lvl;
  }
  
  /**
   * A method which deletes the saved state.
   */
  public void delete() {
    // Tries to get saved.xml
    File saved = new File("levels/saved.xml");
    // deletes saved.xml
    saved.delete();
  }
  
  /**
   * Gets the Levels.
   *
   * @return the levels
   */
  public TreeMap<Integer, State> getLevels() {
    return levels;
  }
}
