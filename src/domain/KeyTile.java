package domain;

import java.awt.Color;

/**
 * Represents a key
 * 
 * @author aarisbaskaran
 *
 */
public class KeyTile extends Tile {

	private Color color;
	/**
	 * Can move onto keys
	 */
	public KeyTile(Color c) {
		color = c;
		canMove = true;
	}
}
