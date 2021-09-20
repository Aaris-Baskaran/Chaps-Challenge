package domain;

import java.awt.*;

/**
 * Represents the exit of the level.
 * 
 * @author aarisbaskaran
 *
 */
public class ExitTile extends Tile{
	/**
	 * can move onto the exit
	 */
	public ExitTile() {
		canMove = true;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		return getImage("ExitTile.png");
	}
}
