package domain;

import java.awt.Image;

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
		canMove = false;
	}

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		if (canMove){
			return getImage("ExitTile.png");
		}
		return getImage("ExitLockTile.png");
	}

	/**
	 * Setter for canMove field, will be called when all chips are collected,
	 */
	public void setCanMove(){
		canMove = true;
	}

	public String toString(){
		if(canMove){
			return "x";
		}
		return "X";
	}
}
