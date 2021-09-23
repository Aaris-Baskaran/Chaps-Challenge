package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import domain.Game;
import persistency.StateManager;
import recorder.Recorder;
import renderer.Renderer;

/**
 * Graphic User Interface Class
 * 
 * @author Stelio Brooky
 *
 */
public class GUI {
	StateManager manager = new StateManager();
	Game game = new Game(manager.loadState());
	Renderer rend = new Renderer(game);
	Recorder record = new Recorder();
	JFrame frame = new JFrame("Chip vs Chap");
	JPanel designPanel = new JPanel();
	JPanel gamePanel = new JPanel();
	JPanel infoPanel = new JPanel();
	JMenuBar menu = new JMenuBar();
	JLabel keysText = new JLabel();
	public int count = 10;
	Timer timer;
	Color bg = new Color(72, 204, 180);

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

		gamePanel = rend.getPanel();
		gamePanel.setBackground(Color.GREEN);
		gamePanel.setPreferredSize(new Dimension(600,600));

		designPanel = createDesignPanel();
		designPanel.setPreferredSize(new Dimension(300,600));

		// Key Press Actions
		keyBindings();

		// Initialize the menu bar
		createMenuBar();

		frame.setLayout(new BorderLayout());
		
		frame.setResizable(false);

		frame.add(gamePanel, BorderLayout.WEST);
		frame.add(designPanel, BorderLayout.EAST);
		frame.pack();

		frame.setVisible(true);
		updateGUI();

	}
	
	private JPanel createDesignPanel() throws IOException {
		JPanel design = new JPanel();
		design.setBackground(bg);
		design.setLayout(new GridLayout(3, 1));

		BufferedImage img = ImageIO.read(new File("Logo.jpg"));
		JLabel picLabel = new JLabel();
		Image dimg = img.getScaledInstance(270, 170, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		picLabel = new JLabel(imageIcon);
		design.add(picLabel);

		// Implement the info panel
		infoPanel = createInfoPanel();
		design.add(infoPanel);
		
		//Implement the button panel
		design.add(createButtonPanel());
		
		return design;
	}

	private JPanel createButtonPanel() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 2));
		buttons.setBackground(bg);
		
		JButton pause = new JButton("Pause (Spacebar)");
		JButton end = new JButton ("Exit (Ctrl+X)");
		JLabel blank = new JLabel("");
		pause.addActionListener((event) -> JOptionPane.showMessageDialog(designPanel, "Paused"));//surely give us extra marks for lambda please :)
		end.addActionListener((event) -> System.exit(0));
		buttons.add(pause);
		buttons.add(end);
		buttons.add(blank);
		buttons.add(blank);
		
		return buttons;
	}
	
	private JPanel createInfoPanel() {
		// Font f = new Font("SansSerif", Font.BOLD, 20);
		JLabel timerText = new JLabel("Timer:");
		JLabel levelText = new JLabel("Level: " + game.getLevel());
		keysText = new JLabel("Keys Collected: " + game.getKeys().size());
		JLabel treasureText = new JLabel("Treasure Remaining:");

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2, 2));
		info.setBackground(bg);
		info.add(timerText);
		info.add(levelText);
		info.add(keysText);
		info.add(treasureText);
		
		return info;
	}

	private void createMenuBar() {
		var fileMenu = new JMenu("File");
		var saveItem = new JMenuItem("Save and Quit");
		saveItem.addActionListener((event) -> manager.SaveXML());
		saveItem.addActionListener((event) -> System.exit(0));
		saveItem.setMnemonic(KeyEvent.VK_S);
		var loadItem = new JMenuItem("Load Saved Game");
		var exitItem = new JMenuItem("Quit");
		exitItem.addActionListener((event) -> System.exit(0));

		var levelMenu = new JMenu("Level");
		var level1Item = new JMenuItem("Load Level 1");
		var level2Item = new JMenuItem("Load Level 2");

		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menu.add(fileMenu);

		levelMenu.add(level1Item);
		levelMenu.add(level2Item);
		menu.add(levelMenu);

		frame.setJMenuBar(menu);
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
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction");
		designPanel.getActionMap().put("upAction", upAction);
		// down arrow key
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		designPanel.getActionMap().put("downAction", downAction);
		// left arrow key
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		designPanel.getActionMap().put("leftAction", leftAction);
		// right arrow key
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		designPanel.getActionMap().put("rightAction", rightAction);
		// space bar
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "spaceAction");
		designPanel.getActionMap().put("spaceAction", spaceAction);
		// control s combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "ctrlSAction");
		designPanel.getActionMap().put("ctrlSAction", ctrlSAction);
		// control x combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "ctrlXAction");
		designPanel.getActionMap().put("ctrlXAction", ctrlXAction);
		// control r combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "ctrlRAction");
		designPanel.getActionMap().put("ctrlRAction", ctrlRAction);
		// control x combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK), "ctrl1Action");
		designPanel.getActionMap().put("ctrl1Action", ctrl1Action);
		// control r combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK), "ctrl2Action");
		designPanel.getActionMap().put("ctrl2Action", ctrl2Action);
	}

	private void fillPanel(JPanel p) {
		p.add(new JPanel());
		p.add(new JPanel());
	}
	
	public void updateGUI() {
				keysText = new JLabel("Keys Collected: " + game.getKeys().size());
				infoPanel.repaint();

		
		
	}

	// Key Binding Classes
	public class UpAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('u');
			record.pastMoves("u");
			rend.updateBoard(game);
			updateGUI();
		}
	}

	public class DownAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('d');
			record.pastMoves("d");
			rend.updateBoard(game);
		}
	}

	public class LeftAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			game.move('l');
			record.pastMoves("l");
			rend.updateBoard(game);
		}
	}

	public class RightAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			game.move('r');
			record.pastMoves("r");
			rend.updateBoard(game);
			keysText.setText("Keys Collected: " + game.getKeys().size());
			try {
				designPanel = createDesignPanel();
				frame.add(designPanel);
				frame.repaint();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public class SpaceAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(designPanel, "Paused");
		}
	}

	public class CtrlSAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manager.SaveXML();
			System.exit(0);
		}
	}

	public class CtrlXAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			rend.updateBoard(game);
			gamePanel = rend.getPanel();
			System.exit(0);
		}
	}

	public class CtrlRAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(record.moves);
		}
	}

	public class Ctrl1Action extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			rend.updateBoard(game);
			changeColour(designPanel);
			
			System.out.println(game.getKeys().size());
		}
	}

	public class Ctrl2Action extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
