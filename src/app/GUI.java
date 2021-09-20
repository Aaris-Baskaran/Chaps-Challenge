package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.JOptionPane;
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
	JFrame frame = new JFrame("Chip vs Chap");
	JPanel designPanel = new JPanel();
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
	Action spaceAction;
	Action ctrlSAction;
	Action ctrlXAction;
	Action ctrlRAction;
	Action ctrl1Action;
	Action ctrl2Action;

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 */
	public GUI() throws IOException {

		gamePanel.setBackground(Color.GREEN);
		gamePanel.setBounds(0, 0, 600, 600);

		// createDesign();
		Color bg = new Color(72, 204, 180);
		designPanel.setBackground(bg);
		// designPanel.setBounds(700, 0, 300, 600);
		designPanel.setLayout(new GridLayout(3, 3, 20, 0));
		// designPanel.setSize(300, 600);

		BufferedImage img = ImageIO.read(new File("Logo.jpg"));
		JLabel picLabel = new JLabel();
		Image dimg = img.getScaledInstance(270, 170, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		picLabel = new JLabel(imageIcon);
		fillPanel(designPanel);
		designPanel.add(picLabel);
		fillPanel(designPanel);
		timerText = new JLabel("Timer:");
		Font f = new Font("SansSerif", Font.BOLD, 20);
		// set the font to the Jlabel
		timerText.setFont(f);
		timerText.setBounds(80, 20, 250, 80);
		designPanel.add(timerText);
		fillPanel(designPanel);
		designPanel.add(new JLabel(""));

		// Key Press Actions
		keyBindings();

		frame.setSize(new Dimension(900, 600));
		frame.setResizable(false);

		frame.add(gamePanel);
		frame.add(designPanel);

		frame.setVisible(true);

	}

	private void keyBindings() {
		upAction = new UpAction();
		downAction = new DownAction();
		leftAction = new LeftAction();
		rightAction = new RightAction();
		spaceAction = new SpaceAction();
		ctrlSAction = new CtrlSAction();
		ctrlXAction = new CtrlXAction();
		ctrlRAction = new CtrlRAction();
		ctrl1Action = new Ctrl1Action();
		ctrl2Action = new Ctrl2Action();

		// up arrow key
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
		designPanel.getActionMap().put("upAction", upAction);
		// down arrow key
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		designPanel.getActionMap().put("downAction", downAction);
		// left arrow key
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		designPanel.getActionMap().put("leftAction", leftAction);
		// right arrow key
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		designPanel.getActionMap().put("rightAction", rightAction);
		// space bar
		designPanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "spaceAction");
		designPanel.getActionMap().put("spaceAction", spaceAction);
		// control s combination
		designPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "ctrlSAction");
		designPanel.getActionMap().put("ctrlSAction", ctrlSAction);
		// control x combination
		designPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "ctrlXAction");
		designPanel.getActionMap().put("ctrlXAction", ctrlXAction);
		// control r combination
		designPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "ctrlRAction");
		designPanel.getActionMap().put("ctrlRAction", ctrlRAction);
		// control x combination
		designPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK), "ctrl1Action");
		designPanel.getActionMap().put("ctrl1Action", ctrl1Action);
		// control r combination
		designPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK), "ctrl2Action");
		designPanel.getActionMap().put("ctrl2Action", ctrl2Action);
	}

	private void fillPanel(JPanel p) {
		p.add(new JPanel());
		p.add(new JPanel());
	}

	// Key Binding Classes
	public class UpAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('u');
		}
	}

	public class DownAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('d');
		}
	}

	public class LeftAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('l');
		}
	}

	public class RightAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			game.move('r');
		}
	}

	public class SpaceAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(designPanel, "Paused");
		}
	}

	public class CtrlSAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			changeColour(gamePanel);
		}
	}

	public class CtrlXAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			changeColour(gamePanel);
		}
	}

	public class CtrlRAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			changeColour(gamePanel);
		}
	}
	public class Ctrl1Action extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			changeColour(gamePanel);
		}
	}

	public class Ctrl2Action extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			changeColour(gamePanel);
		}
	}

	private static void changeColour(JPanel p) {
		p.setBackground(Color.getHSBColor((float) Math.random() * 255, (float) Math.random() * 255,
				(float) Math.random() * 255));
	}

}
