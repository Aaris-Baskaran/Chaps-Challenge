package domain;

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
}
