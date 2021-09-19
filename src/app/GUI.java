package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import domain.Game;

/**
 * Graphic User Interface Class
 * 
 * @author Stelio Brooky
 *
 */
public class GUI {
	Game game = new Game();
	JFrame frame = new JFrame("Chap's Challenge");
	static JPanel designPanel = new JPanel();
	JPanel gamePanel = new JPanel();
	public int count = 10;
	JLabel timerText = new JLabel();
	Timer timer;
	
	/**
	 * Actions for keys
	 */
	Action upAction;
	Action downAction;
	Action leftAction;
	Action rightAction;

	/**
	 * Constructor
	 * @throws IOException 
	 */
	public GUI() throws IOException {

		gamePanel.setBackground(Color.GREEN);
		gamePanel.setBounds(0, 0, 600, 600);
		

		//createDesign();
		Color bg = new Color(72, 204, 180);
		designPanel.setBackground(bg);
		//designPanel.setBounds(700, 0, 300, 600);
		designPanel.setLayout(new GridLayout(3,3,20,0));
		//designPanel.setSize(300, 600);
		
		BufferedImage img = ImageIO.read(new File("Logo.jpg"));
		JLabel picLabel = new JLabel();
		Image dimg = img.getScaledInstance(270, 170,
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		picLabel = new JLabel(imageIcon);
		fillPanel(designPanel);
		designPanel.add(picLabel);
		fillPanel(designPanel);
		timerText = new JLabel("Timer:");
		 Font f = new Font("SansSerif",Font.BOLD,20);
		    //change a jlabel font color
		  //set the font to the Jlabel 
		    timerText.setFont(f);
		    timerText.setBounds(80,20,250,80);
		designPanel.add(timerText);		
		fillPanel(designPanel);
		designPanel.add(new JLabel(""));		
				
		//Key Press Actions
		upAction = new UpAction();
		downAction = new DownAction();
		leftAction = new LeftAction();
		rightAction = new RightAction();
		
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
		designPanel.getActionMap().put("upAction", upAction);
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		designPanel.getActionMap().put("downAction", downAction);
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		designPanel.getActionMap().put("leftAction", leftAction);
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		designPanel.getActionMap().put("rightAction", rightAction);

		frame.setSize(new Dimension(900, 600));
		frame.setResizable(false);		
		
		frame.add(gamePanel);
		frame.add(designPanel);

		frame.setVisible(true);
		
	

	}
	private void fillPanel(JPanel p) {
		p.add(new JPanel());
		p.add(new JPanel());
	}
	
	 

	public class UpAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('u');
		}		
	}
	public class DownAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('d');
		}		
	}
	public class LeftAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('l');
		}		
	}
	public class RightAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('r');
		}		
	}
	
	private static void changeColour(JPanel p) {
		p.setBackground(Color.getHSBColor((float)Math.random()*255, (float)Math.random()*255, (float)Math.random()*255));
	}


}
