package domain;

import java.awt.*;

/**
 * Represents a locked door.
 * 
 * @author aarisbaskaran
 *
 */
public class LockedDoorTile extends Tile {

	/**
	 * The color of a door. Used to identify what key can unlock it.
	 */
	private Color color;

	/**
	 * Initially, door is locked
	 */
	public LockedDoorTile() {
		canMove = false;
	}

	/**
	 * Get the door color.
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
			return getImage("BlueLockedDoorTile.png");
		}
		return getImage("YellowLockedDoorTile.png");
	}
}
