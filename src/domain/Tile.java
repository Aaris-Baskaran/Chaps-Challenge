package domain;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
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

	//Get image - to be used by only this domain. Helper for the get image in each class
	protected Image getImage(String fileName) {
		Image image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Returns the image for a tile - used by renderer.
	 * @return
	 */
	public Image getImage(){
		return getImage("FreeTile.png");
	}
}
