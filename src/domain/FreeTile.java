package domain;

import java.awt.*;

/**
 * Represents a free tile
 * 
 * @author aarisbaskaran
 *
 */
public class FreeTile extends Tile{
	
	/**
	 * Can move through free tiles
	 */
	public FreeTile() { canMove = true; }

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	public Image getImage() {
		return getImage("FreeTile.png");
	}
}
