package domain;

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

}
