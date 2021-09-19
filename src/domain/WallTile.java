package domain;

import java.awt.*;

/**
 * Represents a wall tile.
 * 
 * @author aarisbaskaran
 *
 */
public class WallTile extends Tile{
	
	/**
	 * Cannot move through walls
	 */
	public WallTile() {
		canMove = false;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	public Image getImage() {
		return getImage("WallTile.png");
	}

}
