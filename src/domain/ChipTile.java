package domain;

import java.awt.Image;

/**
 * Represents a chip (treasure).
 * 
 * @author aarisbaskaran
 *
 */
public class ChipTile extends Tile{

	/**
	 * can move on to chips
	 */
	public ChipTile() {
		canMove = true;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		return getImage("TreasureTile.png");
	}

	@Override
	public String toString() {
		return "T";
	}
}
