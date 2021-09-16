package domain;

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
}
