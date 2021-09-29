package domain;

import java.awt.Image;


/**
 * Represents the exit of the level.
 *
 * @author aarisbaskaran
 *
 */
public class PortalTile extends Tile{
    protected int portalNum;

    /**
     * can move onto the exit
     */
    public PortalTile(int p) {
        portalNum = p;
        canMove = true;
    }

    /**
     * Returns the image for this tile - used in renderer.
     *
     * @return image
     */
    @Override
    public Image getImage() {
        return getImage("PortalTile.png");
    }

    public String toString(){
        if (portalNum == 0){
            return "P";
        }
        return "p";
    }
}
