package persistency;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class StateManager {
	
	private static File[] files;
	
	public StateManager() {
		files = findFiles();
	}
	
	private File[] findFiles() {
		File folder = new File("levels");
		return folder.listFiles();
	}
	
	public State loadState() {
		
		State state = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(files[0]);
			
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
}
