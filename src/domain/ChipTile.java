package domain;

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
}
