package persistency;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class StateManager {
	
	private static File[] files;
	
	private final TreeMap<Integer, State> levels;
	
	public StateManager() {
		files = findFiles();
		levels = createLevels();
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
	
	public State loadState(int lvl) {
		
		State state = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(files[lvl - 1]);
			
			Element element = doc.getDocumentElement();
			
			String level = element.getElementsByTagName("Level").item(0).getTextContent();
			String time = element.getElementsByTagName("Time").item(0).getTextContent();
			
			String[] maze = new String[25];
			for(int row = 0; row < 25; ++row) {
				maze[row] = element.getElementsByTagName("Row").item(row).getTextContent();
			}
			
			String x = element.getElementsByTagName("X").item(0).getTextContent();
			String y = element.getElementsByTagName("Y").item(0).getTextContent();
			String dir = element.getElementsByTagName("Dir").item(0).getTextContent();
			String chips = element.getElementsByTagName("Chips").item(0).getTextContent();
			
			char direction = dir.charAt(0);
			
			int levelNum = Integer.parseInt(level);
			int timeLeft = Integer.parseInt(time);
			int xPos = Integer.parseInt(x);
			int yPos = Integer.parseInt(y);
			int chipsLeft = Integer.parseInt(chips);
			
			state = new State(levelNum, timeLeft, maze, xPos, yPos, direction, chipsLeft);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return state;		
	}
	
	public void SaveXML() {
		try {
			File saved = new File("levels/saved.xml");
			saved.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TreeMap<Integer, State> getLevels() {
		return levels;
	}
}
