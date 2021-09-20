package domain;

import java.awt.*;

/**
 * Represents a key
 * 
 * @author aarisbaskaran
 *
 */
public class KeyTile extends Tile {

	/**
	 * The color of a key. Used to identify what door the key can unlock.
	 */
	private Color color;

	/**
	 * Can move onto keys
	 */
	public KeyTile(Color c) {
		color = c;
		canMove = true;
	}

	/**
	 * Gets the key color.
	 *
	 * @return color
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		if (color == Color.BLUE){
			return getImage("BlueKeyTile.png");
		}
		return getImage("YellowKeyTile.png");
	}
}
