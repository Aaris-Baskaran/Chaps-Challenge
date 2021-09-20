package domain;

import java.awt.*;

/**
 * Represents an info field.
 * 
 * @author aarisbaskaran
 *
 */
public class InfoTile extends Tile{

	/**
	 * can move onto info fields
	 */
	public InfoTile() {
		canMove = true;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		return getImage("InfoFieldTile.png");
	}
}
