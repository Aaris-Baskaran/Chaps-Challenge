package persistency;

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

import domain.Game;

public class StateManager {
	
	private static File[] files;
	
	private final TreeMap<Integer, State> levels;
	
	private final int levelNum;
	
	public StateManager() {
		files = findFiles();
		levels = createLevels();
		levelNum = setLevel();
	}
	
	private File[] findFiles() {
		File folder = new File("levels");
		
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File f, String name)
            {
                return name.startsWith("level");
            }
		};
		
		return folder.listFiles(filter);
	}
	
	public TreeMap<Integer, State> createLevels() {
		TreeMap<Integer, State> levelList = new TreeMap<>();
		
		for(int i = 1; i <= files.length; ++i) {
			levelList.put(i, loadState(i));
		}
		
		return levelList;
	}
	
	public State loadGame() {
		File saved = new File("levels/saved.xml");
		if(saved.isFile()) {
			return loadState(saved);
		}
		else {
			return loadState(levelNum);
		}
	}
	
	public State loadState(File file) {
		
		State state = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(file);
			
			Element element = doc.getDocumentElement();
			
			String level = element.getElementsByTagName("Level").item(0).getTextContent();
			String time = element.getElementsByTagName("Time").item(0).getTextContent();
			
			String[] maze = new String[35];
			for(int row = 0; row < 35; ++row) {
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
			int xPos = Integer.parseInt(x);
			int yPos = Integer.parseInt(y);
			int chipsInChest = Integer.parseInt(treasure);
			int chipsLeft = Integer.parseInt(chips);
			
			state = new State(levelNum, timeLeft, maze, keys, xPos, yPos, direction, chipsInChest, chipsLeft);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return state;		
	}
	
	public State loadState(int lvl) {
		
		State state = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(files[lvl - 1]);
			
			Element element = doc.getDocumentElement();
			
			String level = element.getElementsByTagName("Level").item(0).getTextContent();
			String time = element.getElementsByTagName("Time").item(0).getTextContent();
			
			String[] maze = new String[35];
			for(int row = 0; row < 35; ++row) {
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
			int xPos = Integer.parseInt(x);
			int yPos = Integer.parseInt(y);
			int chipsInChest = Integer.parseInt(treasure);
			int chipsLeft = Integer.parseInt(chips);
			
			state = new State(levelNum, timeLeft, maze, keys, xPos, yPos, direction, chipsInChest, chipsLeft);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return state;		
	}
	
	public State loadSelect() {
		
		State state = null;
		
		JFileChooser j = new JFileChooser("levels");
		
		int r = j.showOpenDialog(null);
		
		if (r == JFileChooser.APPROVE_OPTION) {
			 state = loadState(j.getSelectedFile());
		}
		
		return state;		
	}
	
	public void SaveXML(Game game) {
		try {
			File saved = new File("levels/saved.xml");
			saved.createNewFile();
			
			FileWriter writer = new FileWriter(saved);
			writer.write("<?xml version = \"1.0\"?>\n");
			writer.write("<Game>\n");
			writer.write("	<Level>" + game.getLevel() + "</Level>\n");
			writer.write("	<Time>" + game.getTime() + "</Time>\n");
			
			String[] maze = game.toString().split("\n");
			for(int row = 0; row < 35; ++row) {
				writer.write("	<Row>");
					writer.write(maze[row]);
				writer.write("</Row>\n");
			}
			
			writer.write("	<Keys>" + game.getInventory() + "</Keys>\n");
			writer.write("	<X>" + game.getChapPos().getX() + "</X>\n");
			writer.write("	<Y>" + game.getChapPos().getY() + "</Y>\n");
			writer.write("	<Dir>" + Game.getChapDirection() + "</Dir>\n");
			writer.write("	<Chips>" + game.getTreasureChestSize() + "</Chips>\n");
			writer.write("	<Chips>" + game.getChipsRemaining() + "</Chips>\n");
			
			writer.write("</Game>\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveLevel(Game game) {
		try {
			File saved = new File("levels/init.xml");
			saved.createNewFile();
			
			FileWriter writer = new FileWriter(saved);
			writer.write("<?xml version = \"1.0\"?>\n");
			writer.write("<Game>\n");
			writer.write("	<Level>" + game.getLevel() + "</Level>\n");
			writer.write("</Game>\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int setLevel() {
		
		int lvl = 1;
		
		File init = new File("levels/init.xml");
		
		if(!init.isFile()) return lvl;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(init);
			
			Element element = doc.getDocumentElement();
			
			String level = element.getElementsByTagName("Level").item(0).getTextContent();
			
			lvl = Integer.parseInt(level);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return lvl;
	}
	
	public void delete() {
		File saved = new File("levels/saved.xml");
		saved.delete();
	}
	
	public TreeMap<Integer, State> getLevels() {
		return levels;
	}
}
