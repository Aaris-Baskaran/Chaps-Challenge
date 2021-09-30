package recorder;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//import javax.lang.model.element.Element;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
//import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import com.sun.tools.sjavac.Transformer;

//import com.sun.tools.sjavac.Transformer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;

import java.io.File;
import java.io.IOException;

import app.GUI;
import domain.Game;
import persistency.StateManager;

/**
 * @author Maurice.
 *
 */
public class Recorder {
	/*
	 * List that stores all the previous moves of the player.
	 */
	public ArrayList<String> moves = new ArrayList<String>();
	
	/*
	 * Keeps track of the total moves of the player.
	 */
	private static int totalMoves = 0;
	
	private GUI gui;
	
	JButton speed = new JButton("x1");
	
	private int moveCount = 0;
	
	private long sleepTime = 1;
	
	public Recorder(GUI g) {
		gui = g;
	}
	/*
	 * Method that gets called by the GUI to add a step to the list.
	 */
	public void pastMoves(String s) {
		moves.add(s);
		setTotalMoves(getTotalMoves() + 1);
	}
	
	/*
	 * Record the previous game.
	 */
	public void recordGame() {
		// Change Arraylist of moves into XML file
		System.out.print(totalMoves);
		writeXmlFile(moves);
		//moves.clear();
	}
	
	public void writeXmlFile(ArrayList<String> list) {
		try {
			File saved = new File("previousGame/PreviousGame.xml");
			saved.createNewFile();
			
			FileWriter writer = new FileWriter(saved);
			writer.write("<?xml version = \"1.0\"?>\n");
			writer.write("<Moves>\n");
			
			for(String s : moves) {
				writer.write("<Direction>");
				writer.write(s+"</Direction>\n");
			}
			writer.write("</Moves>");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	       
	}
	
	/*
	 * Load the previous game. 
	 */
	public void loadRecordedGame() {
		// Pass the recorded game to GUI.
		StateManager m = new StateManager();
		//m.loadState();
	}
	
	/*
	 * Replay the previous game.
	 */
	public JPanel replayRecordedGame() {
		Color bg = new Color(72, 204, 180);
		Color border = new Color(65, 46, 49);
		// Replay recorded game.
		// Add panel with 3 buttons: step by step, auto-replay, set replay speed.
		JPanel replayPanel = new JPanel();
		replayPanel.setBackground(bg);
		replayPanel.setBorder(BorderFactory.createLineBorder(border, 2));
		replayPanel.setPreferredSize(new Dimension(840,45));
		JButton stepByStep = new JButton("Step by Step");
		JButton autoReplay = new JButton("Auto Replay");
		
		stepByStep.addActionListener((event) -> moveStepByStep());
		autoReplay.addActionListener((event) -> autoReplay());
		speed.addActionListener((event) -> setSpeed());
		replayPanel.add(stepByStep);
		replayPanel.add(autoReplay);
		replayPanel.add(speed);
		return replayPanel;
		
	}
	
	private void moveStepByStep() {
		System.out.println("step");
		System.out.println( moves.size());
		if(moveCount < moves.size()) {
			System.out.println( moveCount);
			if(moves.get(moveCount).equals("up")) {
				gui.move('u');
				System.out.println('u');
			}
			if(moves.get(moveCount).equals("down")) {
				gui.move('d');
				System.out.println('d');
			}
			if(moves.get(moveCount).equals("left")) {
				gui.move('l');
				System.out.println('l');
			}
			if(moves.get(moveCount).equals("right")) {
				gui.move('r');
				System.out.println('r');
			}
		}
		moveCount++;
	}
	
	private void autoReplay() {
		System.out.println("auto");
		for(String s :moves) {
			if(s.equals("up")) {
				gui.move('u');
				System.out.println(s);
			}
			if(s.equals("down")) {
				gui.move('d');
				System.out.println(s);
			}
			if(s.equals("left")) {
				gui.move('l');
				System.out.println(s);
			}
			if(s.equals("right")) {
				gui.move('r');
				System.out.println(s);
			}
			try {
				TimeUnit.SECONDS.sleep(sleepTime);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private int setSpeed() {
		if(speed.getText()=="x1") {
			speed.setText("x2");
			sleepTime = (long) 0.5;;
			System.out.println(sleepTime);
			return 1;
		}else
		if(speed.getText()=="x2") {
			speed.setText("x4");
			sleepTime = (long) 0.25;
			System.out.println(sleepTime);
			return 1;
		}else
		if(speed.getText()=="x4") {
			speed.setText("x1");
			sleepTime = 1;
			System.out.println(sleepTime);
			return 1;
		}
		System.out.println(sleepTime);
		return 1;
	}

	public static int getTotalMoves() {
		return totalMoves;
	}
	
	public static void setTotalMoves(int totalMoves) {
		Recorder.totalMoves = totalMoves;
	}
}
