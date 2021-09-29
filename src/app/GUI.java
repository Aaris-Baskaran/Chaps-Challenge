package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

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
	public StateManager manager = new StateManager();
	public Recorder record = new Recorder();
	public Game game = new Game(manager.getLevels().get(1));
	public Renderer rend = new Renderer(game);
	public Design design = new Design(game, manager);

	public JFrame frame = new JFrame("Chip vs Chap");
	public JPanel designPanel = new JPanel();
	public JPanel gamePanel = new JPanel();
	public JPanel recorderPanel = new JPanel();
	public JMenuBar menu = new JMenuBar();
	public Color bg = new Color(72, 204, 180);
	public Color border = new Color(65, 46, 49);
	public Timer timer;
	//public int timeLeft;

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
		gamePanel.setPreferredSize(new Dimension(540,540));

		designPanel = design.designPanel;
		designPanel.setPreferredSize(new Dimension(300,540));

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
		
		//timeLeft = game.getTime();
		ActionListener timerAction =new ActionListener() {

		    public void actionPerformed(ActionEvent ae) {
		    	try {
					design.update();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        //do your task
		    	game.time = game.time - 1;
		        if(game.time==0) {
		            timer.stop();//stop the task after do the work
		            System.exit(0);
		    }
		    }
		};
		timer=new Timer(1000,timerAction );//create the timer which calls the actionperformed method for every 1000 millisecond(1 second=1000 millisecond)
		timer.start();//start the task

	}

	private void createMenuBar() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save and Quit");
		saveItem.addActionListener((event) -> manager.SaveXML(game));
		saveItem.addActionListener((event) -> System.exit(0));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem loadItem = new JMenuItem("Load Saved Game");
		loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem exitItem = new JMenuItem("Quit");
		exitItem.addActionListener((event) -> System.exit(0));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

		JMenu recordMenu = new JMenu("Record");
		JMenuItem recordGame = new JMenuItem("Record and store this game");
		recordGame.addActionListener((event) -> record.recordGame());
		JMenuItem loadRecordedGame = new JMenuItem("Load the previous recorded game");
		loadRecordedGame.addActionListener((event) -> record.loadRecordedGame());

		JMenu replayMenu = new JMenu("Replay");
		JMenuItem replayGame = new JMenuItem("Replay the recorded game");
		replayGame.addActionListener((event) -> createRecorderPanel());
		JMenu levelMenu = new JMenu("Level");
		JMenuItem level1Item = new JMenuItem("Load Level 1");
		level1Item.addActionListener((event) -> ctrl1Action.actionPerformed(event));
		level1Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK));
		
		JMenuItem level2Item = new JMenuItem("Load Level 2");
		level2Item.addActionListener((event) -> ctrl2Action.actionPerformed(event));
		level2Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK));

		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpItem = new JMenuItem("See game instructions and controls");
		helpItem.addActionListener((event) -> design.createHelp());

		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menu.add(fileMenu);

		recordMenu.add(recordGame);
		recordMenu.add(loadRecordedGame);
		menu.add(recordMenu);

		replayMenu.add(replayGame);
		menu.add(replayMenu);

		levelMenu.add(level1Item);
		levelMenu.add(level2Item);
		menu.add(levelMenu);

		helpMenu.add(helpItem);
		menu.add(helpMenu);

		frame.setJMenuBar(menu);
	}

	private void createRecorderPanel() {
		record.replayRecordedGame();

		recorderPanel.setBackground(bg);
		recorderPanel.setBorder(BorderFactory.createLineBorder(border, 2));
		recorderPanel.setPreferredSize(new Dimension(840, 100));

		frame.add(recorderPanel, BorderLayout.SOUTH);
		frame.pack();

	}

	/**
	 * i like to move it move it
	 * 
	 * @param dir
	 */
	public void move(char dir) {
		game.move(dir);
		rend.updateBoard(game);
		try {
			design.update();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (game.isFinished()) {
			System.exit(0);
		}

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
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "ctrlSAction");
		designPanel.getActionMap().put("ctrlSAction", ctrlSAction);
		// control x combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "ctrlXAction");
		designPanel.getActionMap().put("ctrlXAction", ctrlXAction);
		// control r combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "ctrlRAction");
		designPanel.getActionMap().put("ctrlRAction", ctrlRAction);
		// control 1 combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK), "ctrl1Action");
		designPanel.getActionMap().put("ctrl1Action", ctrl1Action);
		// control 2 combination
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK), "ctrl2Action");
		designPanel.getActionMap().put("ctrl2Action", ctrl2Action);
	}

	// Key Binding Classes
	public class UpAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			record.pastMoves("u");
			move('u');
		}
	}

	public class DownAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			move('d');
			record.pastMoves("d");
		}
	}

	public class LeftAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			move('l');
			record.pastMoves("l");
		}
	}

	public class RightAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			move('r');
			record.pastMoves("r");
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
			manager.SaveXML(game);
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
			frame.remove(recorderPanel);
			frame.pack();
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
			game.loadLevel(manager.getLevels().get(1));
			rend.updateBoard(game);
		}
	}

	public class Ctrl2Action extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			game.loadLevel(manager.getLevels().get(2));
			rend.updateBoard(game);
		}
	}
}
