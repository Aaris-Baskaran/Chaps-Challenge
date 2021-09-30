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
	
	// Display fields
	private JPanel board;
	private ArrayList<Object> tileList = new ArrayList<Object>();
	private Game game;
	
	/**
	 * Renderer constructor. The tileList used to paint the board is initialised here.
	 */
	public Renderer(Game game) {
		for (int i = 0; i < 35; i++) {
			for (int j = 0; j < 35; j++) {
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
				
				Position chapPosition = game.getChapPos();
				int firstIndexX = chapPosition.getX() - 4;
				int firstIndexY = chapPosition.getY() - 4;
				int lastIndexX = chapPosition.getX() + 5;
				int lastIndexY = chapPosition.getY() + 5;
				
				int iPos = 0;
				
				for (int i = firstIndexY; i < lastIndexY; i++) {
					int jPos = 0;
					for (int j = firstIndexX; j < lastIndexX; j++) {
						if (tileList.size() > 0) {
							Object tile = tileList.get(i*35 + j);
							g.drawImage((Image) tile, jPos*60, iPos*60, 60, 60, this);
							jPos++;
						}
					}
					iPos++;
				}
				
				if (game.isOnInfo()) {
					g.drawRect(180, 310, 180, 100);
					g.setColor(Color.WHITE);
					g.fillRect(180, 310, 180, 100);
					g.setColor(Color.BLACK);
					g.setFont(g.getFont().deriveFont(15f));
					g.drawString(game.getInfo(), 235, 330);
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
		
		for (int i = 0; i < 35; i++) {
			for (int j = 0; j < 35; j++) {
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
