package domain;

/**
 * Represents the player.
 * 
 * @author aarisbaskaran
 *
 */
public class ChapTile extends Tile{

	/**
	 * other moving things can move onto chap tiles.
	 */
	public ChapTile() {
		canMove = true;
	}
}
