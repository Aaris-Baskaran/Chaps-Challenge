package domain;

import java.awt.*;

/**
 * Represents the bug.
 *
 * @author aarisbaskaran
 */
public class BugTile extends Tile{

	private char type;
	private boolean direction;

	/**
	 * Constructor.
	 */
	public BugTile(char t) {
		type = t;
		direction = true;
		canMove = false;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {

		if (type == 'h'){
			if(direction){
				return getImage("BugFacingLeftTile.png");
			}
			else {
				return getImage("BugFacingRightTile.png");
			}
		}
		else{
			if(direction){
				return getImage("BugFacingUpTile.png");
			}
			else {
				return getImage("BugFacingDownTile.png");
			}
		}

	}

	public boolean getDirection() {
		return direction;
	}

	public void toggleDirection(){
		if (direction){
			direction = false;
		}
		else{
			direction = true;
		}
	}

	public char getType() {
		return type;
	}
	
	@Override
	public String toString() {
		if(type == 'h') return "B";
		return "b";
	}
}
