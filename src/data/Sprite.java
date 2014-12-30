/*
 * Sprite.java
 * @package data
 *
 * Created on 09.Ara.2011
 *
 * Copyright(c) Tansel Altınel.  All Rights Reserved.
 * For more information about the project
 * or the code please contact me: tansel@tanshaydar.com
 *
 */
package data;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Tansel
 */
public class Sprite {
    
    /**
     * This is the image to be shown on the screen
     */
    private Image image;
    
    /**
     * Sprite is the visual to be drawn on the screen.
     * @param image
     */
    public Sprite( Image image) {
        this.image = image;
    }
    
    /**
     * 
     * @return Width of the visual 
     */
    public int getWidth() {
        return image.getWidth(null);
    }
    
    /**
     * 
     * @return Height of the visual
     */
    public int getHeight() {
        return image.getHeight(null);
    }
    
    public void draw( int x, int y, Graphics2D g) {
        g.drawImage(image, x, y, null);
    }
}
