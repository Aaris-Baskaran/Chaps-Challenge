package app;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domain.Game;
import domain.KeyTile;

public class Design {
	public JPanel designPanel = new JPanel();
	public Color bg = new Color(72, 204, 180);
	public JPanel infoPanel = new JPanel();
	public Game game;
	public JLabel keysText = new JLabel();
	public JLabel treasureText = new JLabel();

	public Design(Game game) throws IOException {
		this.game = game;
		designPanel = createDesignPanel();
	}

	public JPanel createDesignPanel() throws IOException {
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

		// Implement the button panel
		design.add(createButtonPanel());

		return design;
	}

	public JPanel createButtonPanel() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 2));
		buttons.setBackground(bg);

		JButton pause = new JButton("Pause (Spacebar)");
		JButton end = new JButton("Exit (Ctrl+X)");
		JLabel blank = new JLabel("");
		pause.addActionListener((event) -> JOptionPane.showMessageDialog(designPanel, "Paused"));// surely give us extra
																									// marks for lambda
																									// please :)
		end.addActionListener((event) -> System.exit(0));
		buttons.add(pause);
		buttons.add(end);
		buttons.add(blank);
		buttons.add(blank);

		return buttons;
	}

	public JPanel createInfoPanel() {
		// Font f = new Font("SansSerif", Font.BOLD, 20);
		JLabel timerText = new JLabel("Timer: " + game.getTime() + " seconds");
		JLabel levelText = new JLabel("Level: " + game.getLevel());
		keysText = new JLabel();
		String keys = "Keys Collected: You Have No Keys Collected.";
		keysText.setText("<html><p style=\"width:100px\">" + keys + "</p></html>");
		treasureText = new JLabel("Treasure Remaining:");
		String treasure = "Treasure Remaining: " + game.getChipsRemaining();
		treasureText.setText("<html><p style=\"width:100px\">" + treasure + "</p></html>");

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2, 2));
		info.setBackground(bg);
		info.add(timerText);
		info.add(levelText);
		info.add(keysText);
		info.add(treasureText);

		return info;
	}

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
		}
		String treasure = "Treasure Remaining: " + game.getChipsRemaining();
		if(game.getChipsRemaining() == 0) {
			treasure = treasure + ". Door is open!";
		}
		treasureText.setText("<html><p style=\"width:100px\">" + treasure + "</p></html>");
		

	}
}
