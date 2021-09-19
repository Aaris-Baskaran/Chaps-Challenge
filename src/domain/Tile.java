package domain;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * 
 * Abstract tile class.
 *
 * @author aarisbaskaran
 *
 */
public abstract class Tile {
	
	protected boolean canMove;
	
	/**
	 * Constructor.
	 */
	public Tile() {
	}

	protected Image getImage(String fileName) {
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
