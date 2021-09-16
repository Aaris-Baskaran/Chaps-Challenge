package domain;

/**
 * Represents a free tile
 * 
 * @author aarisbaskaran
 *
 */
public class FreeTile extends Tile{
	
	/**
	 * Can move through free tiles
	 */
	public FreeTile() {
		canMove = true;
	}
}
