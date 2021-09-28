package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Help {

	public JFrame frame = new JFrame();
	public JPanel panel = new JPanel();

	public Help() {
		panel = createPanel();
		
		JButton exit = new JButton("Exit");
		exit.addActionListener((event) -> frame.dispose());
		
		frame.setLayout(new BorderLayout());
		
		panel.setPreferredSize(new Dimension(200, 400));
		exit.setPreferredSize(new Dimension(200, 100));
		
		panel.setBorder(BorderFactory.createLineBorder(new Color(65, 46, 49), 2));
		exit.setBorder(BorderFactory.createLineBorder(new Color(65, 46, 49), 2));

		frame.add(panel, BorderLayout.NORTH);
		frame.add(exit, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private JPanel createPanel() {
		JPanel p = new JPanel();

		JLabel header = new JLabel("Help");
		header.setFont(new Font("", Font.BOLD, 30));

		String display = "Welcome to the instructions and help section of Chip vs Chap. The aim of the game is to collect all of the chips and get to the exit before the time runs out. In order to do this, you must find keys, navigate the maze and in Level 2, use portals and avoid bugs to reach all the chips. Good Luck!";
		JLabel mainText = new JLabel(("<html><p style=\"width:125px\">" + display + "</p></html>"));
		
		JLabel controlHeader = new JLabel("Controls");
		controlHeader.setFont(new Font("", Font.BOLD, 30));
		
		String controls1 = "ARROW KEYS = Move Chap	SPACE = Pause";
		JLabel controlText1 = new JLabel(("<html><p style=\"width:125px\">" + controls1 + "</p></html>"));
		
		String controls2 = "Use the Menu Bar to see alternate options and keyboard shortcuts.";
		JLabel controlText2 = new JLabel(("<html><p style=\"width:125px\">" + controls2 + "</p></html>"));
		
		p.setBackground(new Color(72, 204, 180));
		p.add(header);
		p.add(mainText);
		p.add(controlHeader);
		p.add(controlText1);
		p.add(controlText2);

		return p;
	}

}
