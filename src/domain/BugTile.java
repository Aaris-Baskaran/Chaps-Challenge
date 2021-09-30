package domain;

import java.awt.*;

/**
 * Represents the bug.
 * 
 * @author aarisbaskaran
 *
 */
public class BugTile extends Tile{

	private char type;
	private boolean direction;

	/**
	 * other moving things can move onto bug tiles.
	 */
	public BugTile(char t) {
		type = t;
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

	public boolean getDirection() {
		return direction;
	}

	public void setDirection(){
		if (direction){
			direction = false;
		}
		direction = true;
	}

	public char getType() {
		return type;
	}
}
