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
	JPanel panel = new JPanel();
	
	JPanel panel2 = new JPanel();
	
	/**
	 * Constructor
	 */
	public GUI() {
		
		
		panel2.setBackground(Color.GREEN);
		//panel2.setPreferredSize(new Dimension(300,300));
		
		panel2.setBounds(0, 0, 600, 600);
		panel.setBackground(Color.BLACK);
		//panel.setPreferredSize(new Dimension(700,700));
		panel.setBounds(700, 0, 300, 600);
		frame.setSize(new Dimension(900,600));
		//frame.setLayout(new GridLayout(1,2));
		frame.add(panel2);
		frame.add(panel);
		frame.setVisible(true);
		
	}
}
