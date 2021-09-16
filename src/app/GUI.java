package app;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphic User Interface Class
 * 
 * @author Stelio Brooky
 *
 */
public class GUI {
	JFrame frame = new JFrame("Chap's Challenge");
	JPanel designPanel = new JPanel();	
	JPanel gamePanel = new JPanel();
	
	/**
	 * Constructor
	 */
	public GUI() {
		
		gamePanel.setBackground(Color.GREEN);
		gamePanel.setBounds(0, 0, 600, 600);
		
		Color bg = Color.CYAN.darker();
		designPanel.setBackground(bg);
		designPanel.setBounds(700, 0, 300, 600);
		
		frame.setSize(new Dimension(900,600));
		frame.setResizable(false);
		frame.add(gamePanel);
		frame.add(designPanel);
		frame.setVisible(true);
		
	}
}
