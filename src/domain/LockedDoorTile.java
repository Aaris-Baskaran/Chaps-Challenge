package domain;

import java.awt.Color;

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
}
