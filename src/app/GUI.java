package app;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	JFrame frame = new JFrame("Chap's Challenge");
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	
	public GUI() {
		panel.setBackground(Color.BLACK);
		panel.setPreferredSize(new Dimension(700,700));
		
		panel2.setBackground(Color.GREEN);
		panel2.setPreferredSize(new Dimension(300,300));
		
		frame.setSize(new Dimension(1000,700));
		frame.add(panel);
		frame.add(panel2);
		frame.setVisible(true);
		
	}
}
