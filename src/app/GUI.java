package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

import domain.Game;
import persistency.StateManager;
import recorder.Recorder;
import renderer.Renderer;

/**
 * Graphic User Interface Class
 * 
 * @author Stelio Brooky, 300540333
 *
 */
public class GUI extends WindowAdapter {
	public StateManager manager = new StateManager();

	public Recorder record = new Recorder(this);

	public Game game = new Game(manager.loadGame());

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

	/**
	 * Actions for keys
	 */
	Action upAction;
	Action downAction;
	Action leftAction;
	Action rightAction;
	Action spaceAction;
	Action escAction;
	Action ctrlSAction;
	Action ctrlXAction;
	Action ctrlRAction;
	Action ctrl1Action;
	Action ctrl2Action;

	/**
	 * 
	 * Constructor, Constructs the board using helper methods and initiates the
	 * timer.
	 * 
	 * @throws IOException
	 */
	public GUI() throws IOException {

		gamePanel = rend.getPanel();
		gamePanel.setPreferredSize(new Dimension(540, 540));

		designPanel = design.designPanel;
		designPanel.setPreferredSize(new Dimension(300, 540));

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
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		ActionListener timerAction = new ActionListener() {

			/**
			 * Perform the timer functionality under certain conditions
			 */
			public void actionPerformed(ActionEvent ae) {
				if (game.getLevel() == 2) {
					game.moveBugs();
				}
				if (game.isDead()) {
					design.isPaused = true;
					JOptionPane.showMessageDialog(designPanel, "You got hit by a bug, level will restart");
					if (game.getLevel() == 1) {
						game.loadLevel(manager.getLevels().get(1));
					} else {
						game.loadLevel(manager.getLevels().get(2));
					}
					rend.updateBoard(game);
					try {
						design.update();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					design.isPaused = false;
				}
				// turn the clock down if the game isnt paused
				if (!design.isPaused) {
					game.time = game.time - 1;
				}
				rend.updateBoard(game);
				try {
					design.update();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (game.time < 1) {
					design.isPaused = true;
					JOptionPane.showMessageDialog(designPanel, "Time ran out, Level will restart");
					if (game.getLevel() == 1) {
						game.loadLevel(manager.getLevels().get(1));
					} else {
						game.loadLevel(manager.getLevels().get(2));
					}
					rend.updateBoard(game);
					try {
						design.update();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					design.isPaused = false;
				}
			}
		};
		timer = new Timer(1000, timerAction);// create the timer which calls the actionperformed method for every second
		timer.start();// start the task

		// stop the space bar from selecting highlighted buttons
		InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
		im.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
		im.put(KeyStroke.getKeyStroke("released SPACE"), "none");

		frame.addWindowListener(this);

	}

	private void createMenuBar() {
		// create the file option
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save and Quit");
		saveItem.addActionListener((event) -> manager.saveState(game));
		saveItem.addActionListener((event) -> System.exit(0));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem loadItem = new JMenuItem("Load Saved Game");
		loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem exitItem = new JMenuItem("Quit");
		exitItem.addActionListener((event) -> System.exit(0));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

		// create the recorder option
		JMenu recordMenu = new JMenu("Record");
		JMenuItem recordGame = new JMenuItem("Record and store this game");
		recordGame.addActionListener((event) -> record.recordGame());
		JMenuItem loadRecordedGame = new JMenuItem("Load the previous recorded game");
		loadRecordedGame.addActionListener((event) -> record.loadRecordedGame());

		// create the replay option
		JMenu replayMenu = new JMenu("Replay");
		JMenuItem replayGame = new JMenuItem("Replay the recorded game");
		replayGame.addActionListener((event) -> createRecorderPanel());

		// create the level select option
		JMenu levelMenu = new JMenu("Level");
		JMenuItem level1Item = new JMenuItem("Load Level 1");
		level1Item.addActionListener((event) -> ctrl1Action.actionPerformed(event));
		level1Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem level2Item = new JMenuItem("Load Level 2");
		level2Item.addActionListener((event) -> ctrl2Action.actionPerformed(event));
		level2Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK));

		// create the generate help menu option
		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpItem = new JMenuItem("See game instructions and controls");
		helpItem.addActionListener((event) -> design.createHelp());

		// add them all to the menu bar and then add the menu bar to the frame
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
		// Create the panel that the replay functionality needs
		recorderPanel = record.replayRecordedGame();
		if (game.getLevel() == 1) {
			game.loadLevel(manager.getLevels().get(1));
		} else {
			game.loadLevel(manager.getLevels().get(2));
		}

		frame.add(recorderPanel, BorderLayout.SOUTH);
		frame.pack();

	}

	/**
	 * 
	 * The move class, which is used to recognize when the player has moved. This is
	 * used for key bindings and the replay functionality for the recorder
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
			if (game.getLevel() == 1) {
				game.loadLevel(manager.getLevels().get(2));
				rend.updateBoard(game);
				try {
					design.update();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				design.isPaused = true;
				JOptionPane.showMessageDialog(designPanel,
						"Congratulations on beating Chip vs Chap, if you wish to continue, the game will now restart at level 1, otherwise, thank you for playing!");
				game.loadLevel(manager.getLevels().get(1));
				rend.updateBoard(game);
				try {
					design.update();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				design.isPaused = false;
			}
		}

	}

	/**
	 * Creates the window pop up when someone wishes to close with the X button on the top of the frame
	 * 
	 */
	public void windowClosing(WindowEvent e) {
		int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you would like to exit?");
		if (confirm == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	private void keyBindings() {
		upAction = new UpAction();
		downAction = new DownAction();
		leftAction = new LeftAction();
		rightAction = new RightAction();
		spaceAction = new SpaceAction();
		escAction = new EscAction();
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
		// escape key
		designPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), "escAction");
		designPanel.getActionMap().put("escAction", escAction);
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
	
	/**
	 * 
	 * Key binding class used to represent the up arrow key
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class UpAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!design.isPaused) {
				record.pastMoves("up");
				move('u');
			}
		}
	}
	
	/**
	 * 
	 * Key binding class used to represent the down arrow key
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class DownAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!design.isPaused) {
				move('d');
				record.pastMoves("down");
			}
		}
	}

	/**
	 * 
	 * Key binding class used to represent the left arrow key
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class LeftAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!design.isPaused) {
				move('l');
				record.pastMoves("left");
			}
		}
	}
	
	/**
	 * 
	 * Key binding class used to represent the right arrow key
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class RightAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!design.isPaused) {
				move('r');
				record.pastMoves("right");
			}
		}
	}

	/**
	 * 
	 * Key binding class used to represent the spacebar
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class SpaceAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			design.isPaused = true;
		}
	}

	/**
	 * 
	 * Key binding class used to represent the escape key
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class EscAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			design.isPaused = false;
		}
	}

	/**
	 * 
	 * Key binding class used to represent the Control-S combination
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class CtrlSAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.saveState(game);
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * Key binding class used to represent the Control-X combination
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class CtrlXAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			manager.delete();
			manager.saveLevel(game);
			System.exit(0);
		}
	}

	/**
	 * 
	 * Key binding class used to represent the Control-R combination
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class CtrlRAction extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			game.loadLevel(manager.loadSelect());
		}
	}

	/**
	 * 
	 * Key binding class used to represent the Control-1 combination
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class Ctrl1Action extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The action method that runs when the key is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			game.loadLevel(manager.getLevels().get(1));
			rend.updateBoard(game);
			try {
				design.update();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Key binding class used to represent the Control-2 combination
	 * 
	 * @author Stelio Brooky, 300540333
	 *
	 */
	public class Ctrl2Action extends AbstractAction {

		/**
		 * a universal version identifier for the serialization
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			game.loadLevel(manager.getLevels().get(2));
			rend.updateBoard(game);
			try {
				design.update();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
