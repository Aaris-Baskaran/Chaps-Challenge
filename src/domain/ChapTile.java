package domain;

import java.awt.*;

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

	/**
	 * Returns the image for this tile - used in renderer.
	 *
	 * @return image
	 */
	@Override
	public Image getImage() {
		char dir = Game.getChapDirection();

		//check direction first
		if(dir == 'u'){
			return getImage("ChapBackWithMaskTile.png");
		}
		else if (dir == 'd'){
			return getImage("ChapFrontWithMaskTile.png");
		}
		else if (dir == 'l'){
			return getImage("ChapLeftWithMaskTile.png");
		}
		else {
			return getImage("ChapRightWithMaskTile.png");
		}
	}

	@Override
	public String toString() {
		return "C";
	}
}
