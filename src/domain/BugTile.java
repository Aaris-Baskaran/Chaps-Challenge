package domain;

import java.awt.*;

/**
 * Represents the bug.
 * 
 * @author aarisbaskaran
 *
 */
public class BugTile extends Tile{

	/**
	 * other moving things can move onto bug tiles.
	 */
	public BugTile() {
		canMove = true;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		char dir = Game.getBugDirection();

		//check direction first
		if(dir == 'u'){
			return getImage("BugFacingUpTile.png");
		}
		else if (dir == 'd'){
			return getImage("BugFacingDownTile.png");
		}
		else if (dir == 'l'){
			return getImage("BugFacingLeftTile.png");
		}
		else {
			return getImage("BugFacingRightTile.png");
		}
	}
}
