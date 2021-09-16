package domain;

import java.util.concurrent.locks.Lock;

/**
 * Represents a locked door.
 * 
 * @author aarisbaskaran
 *
 */
public class LockedDoorTile extends Tile {

	/**
	 * Initially, door is locked
	 */
	public LockedDoorTile() {
		canMove = false;
	}
}
