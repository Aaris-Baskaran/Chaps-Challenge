package app;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Game;
import domain.KeyTile;
import persistency.StateManager;

/**
 * This is a class designated to creating the design window (where all the info
 * and buttons are) for the GUI to use.
 * 
 * @author Stelio Brooky, 300540333
 *
 */
public class Design {
	public JPanel designPanel = new JPanel();
	public Color bg = new Color(72, 204, 180);
	public Color border = new Color(65, 46, 49);
	public Game game;
	public StateManager manager;
	public JLabel keysText = new JLabel();
	public JLabel treasureText = new JLabel();
	public JLabel timerText = new JLabel();
	public JLabel levelText = new JLabel();
	public Help helpFrame;
	public boolean isHelpActive = false;
	public boolean isPaused = false;

	/**
	 * 
	 * Constructor
	 * 
	 * @param game
	 * @param manager
	 * @throws IOException
	 */
	public Design(Game game, StateManager manager) throws IOException {
		this.game = game;
		this.manager = manager;
		designPanel = createDesignPanel();
	}

	/**
	 * 
	 * Method to create the design panel, First creating and displaying the logo at
	 * the top, and then calling other methods to build the bottom 2 sections.
	 * 
	 * @return design
	 * @throws IOException
	 */
	public JPanel createDesignPanel() throws IOException {
		JPanel design = new JPanel();
		design.setBackground(bg);
		design.setLayout(new GridLayout(3, 1));

		// Implement the Logo panel
		BufferedImage img = ImageIO.read(new File("Logo.jpg"));
		
		Image scaledImg = img.getScaledInstance(300, 130, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(scaledImg);
		JLabel picLabel = new JLabel(imageIcon);
		picLabel.setBorder(BorderFactory.createLineBorder(border, 2));
		design.add(picLabel);

		// Implement the info panel
		design.add(createInfoPanel());

		// Implement the button panel
		design.add(createButtonPanel());

		return design;
	}

	/**
	 * 
	 * Method to create the middle section of the design panel, showing time
	 * remaining, the current level, keys collected and treasure remaining.
	 * 
	 * @return info
	 */
	public JPanel createInfoPanel() {

		ArrayList<JLabel> labels = new ArrayList<JLabel>();

		timerText = new JLabel("Timer: " + game.time + " seconds");
		labels.add(timerText);
		levelText = new JLabel("Level: " + game.getLevel());
		labels.add(levelText);
		keysText = new JLabel();
		String keys = "Keys Collected: You have no keys collected.";
		keysText.setText("<html><p style=\"width:100px\">" + keys + "</p></html>");
		labels.add(keysText);
		treasureText = new JLabel("Treasure Remaining:");
		String treasure = "Treasure Remaining: " + game.getChipsRemaining();
		treasureText.setText("<html><p style=\"width:100px\">" + treasure + "</p></html>");
		labels.add(treasureText);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2, 2));
		info.setBackground(new Color(65, 46, 49));

		for (JLabel j : labels) {
			JPanel grid = new JPanel();
			grid.setBackground(bg);
			grid.add(j);
			grid.setBorder(BorderFactory.createLineBorder(border, 2));
			info.add(grid);
		}

		return info;
	}

	/**
	 * 
	 * Method to create the bottom section of the design panel, showing buttons for
	 * pause, exit, help menu and save.
	 * 
	 * @return buttons
	 */
	public JPanel createButtonPanel() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 2));
		buttons.setBackground(bg);

		JButton pause = new JButton("Pause (Spacebar)");
		pause.setBorder(BorderFactory.createLineBorder(border, 2));
		JButton end = new JButton("Exit (Ctrl+X)");
		end.setBorder(BorderFactory.createLineBorder(border, 2));
		JButton help = new JButton("Help!");
		help.setBorder(BorderFactory.createLineBorder(border, 2));
		JButton save = new JButton("Save");
		save.setBorder(BorderFactory.createLineBorder(border, 2));

		// surely give us extra marks for lambda please :)
		pause.addActionListener((event) -> isPaused = !isPaused);
		pause.addActionListener((event) -> {
			try {
				update();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		end.addActionListener((event) -> System.exit(0));
		save.addActionListener((event) -> manager.saveState(game));
		help.addActionListener((event) -> createHelp());

		buttons.add(pause);
		buttons.add(end);
		buttons.add(help);
		buttons.add(save);

		return buttons;
	}

	/**
	 * Method to create the help panel
	 */
	public void createHelp() {
		if (isHelpActive) {
			// Dispose so you can't open multiple help frames
			helpFrame.frame.dispose();
		}
		isHelpActive = true;
		helpFrame = new Help();
	}

	/**
	 * Update the design panel every time it needs to change, update all info display.
	 * 
	 * @throws IOException
	 */
	public void update() throws IOException {
		if (!game.getKeys().isEmpty()) {
			String keys = "";
			for (KeyTile k : game.getKeys()) {
				if (k.getColor().equals(Color.BLUE)) {
					keys = keys + "Blue ";
				} else {
					keys = keys + "Yellow ";
				}
			}

			String displayKeys = "Keys Collected: " + keys;
			keysText.setText("<html><p style=\"width:100px\">" + displayKeys + "</p></html>");
		} else {
			String key = "Keys Collected: You have no keys collected.";
			keysText.setText("<html><p style=\"width:100px\">" + key + "</p></html>");
		}
		String treasure = "Treasure Remaining: " + game.getChipsRemaining();
		if (game.getChipsRemaining() == 0) {
			treasure = "You have collected all of the chips. Door is open!";
		}
		treasureText.setText("<html><p style=\"width:100px\">" + treasure + "</p></html>");

		if (isPaused) {
			String time = "Timer: " + game.time + " seconds THE GAME IS CURRENTLY PAUSED (ESC key to resume)";
			timerText.setText("<html><p style=\"width:100px\">" + time + "</p></html>");
		} else {
			timerText.setText("Timer: " + game.time + " seconds");
		}

		levelText.setText("Level: " + game.getLevel());

	}
}
