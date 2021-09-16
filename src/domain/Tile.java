package domain;

/**
 * 
 * Abstract tile class.
 *
 * @author aarisbaskaran
 *
 */
public abstract class Tile {
	
	private boolean canMove;
	
	/**
	 * Constructor.
	 */
	public Tile(boolean canMove) {
		this.canMove = canMove;
	}
}
