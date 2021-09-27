package renderer;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import domain.*;

/**
 * Renderer class
 * 
 * @author Gevin
 *
 */
public class Renderer {
	
	private JPanel board;
	private ArrayList<Object> tileList = new ArrayList<Object>();
	private Game game;
	
	/**
	 * Renderer constructor
	 */
	public Renderer(Game game) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Tile[][] maze = game.getMaze();
				Tile tile = maze[j][i];
				this.tileList.add(tile.getImage());
			}
		}
		this.game = game;
		createBoard();
	}
	
	/**
	 * The createBoard() method draws each Tile in the 2D array of the maze onto a JPanel.
	 */
	public void createBoard() {
		board = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g) {
				
				setBackground(Color.WHITE);
				
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (tileList.size() > 0) {
							Object tile = tileList.get(i*10 + j);
							g.drawImage((Image) tile, j*60, i*60, 60, 60, this);
							System.out.println(i + " " + j + "\n");
						}
					}
				}
				
			}
		};
		
	}
	
	/**
	 * The updateBoard(Game game) method will update the tileList used to paint the board and
	 * repaint the board afterwards.
	 * 
	 * @param game
	 */
	public void updateBoard(Game game) {
		tileList = update(game);
		board.repaint();
	}
	
	/**
	 * The update(Game game) method gets the 2D array of the maze in its current form and
	 * will add each tile to the updated list which is used in updateBoard(Game game).
	 * 
	 * @param game
	 * @return the updated tile list
	 */
	public ArrayList<Object> update(Game game) {
		ArrayList<Object> updatedList = new ArrayList<Object>();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Tile[][] maze = game.getMaze();
				Tile tile = maze[j][i];
				updatedList.add(tile.getImage());
			}
		}
		
		return updatedList;
	}
	
	/**
	 * The getPanel() method allows the GUI class to access and use the board panel for the GUI.
	 * 
	 * @return the board panel
	 */
	public JPanel getPanel() {
		return this.board;
	}
}
