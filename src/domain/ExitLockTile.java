package domain;

import java.awt.*;

/**
 * Represents the exit lock tile
 * 
 * @author aarisbaskaran
 *
 */
public class ExitLockTile extends Tile{

	/**
	 * Cannot move onto locked exit
	 */
	public ExitLockTile() {
		canMove = false;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		return getImage("ExitLockTile.png");
	}
}
