/*
 * SpriteManager.java
 * @package logic
 *
 * Created on 09.Ara.2011
 *
 * Copyright(c) Tansel Altınel.  All Rights Reserved.
 * For more information about the project
 * or the code please contact me: tansel@tanshaydar.com
 *
 */
package logic;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

import data.Sprite;

/**
 *
 * @author Tansel
 */
public class SpriteManager {
    
	/**
	 * This is a singleton pattern object. It returns only one.
	 */
    private static SpriteManager singleton = new SpriteManager();
    private HashMap sprites = new HashMap();
    /**
     * A {@link BufferedImage} is rendered by graphic card.
     */
    BufferedImage sourceImage = null;
    
    /**
     * Returns our only instance.
     * @return SpriteManager object.
     */
    public static SpriteManager get() {
        return singleton;
    }
    
    /**
     * 
     * @param file Path to the image.
     * @return Returns a sprite.
     */
    public Sprite getSprite( String file) {
        if( sprites.get(file) != null)
            return (Sprite) sprites.get(file);
        
        try {
            URL url = this.getClass().getClassLoader().getResource(file);
            if( url == null)
                fail( "Can't find the file: " + file);
            sourceImage = ImageIO.read(url);
        } catch(IOException e) {
            fail( "Failed to load: " + file);
        }
        
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.TRANSLUCENT);
        
        image.getGraphics().drawImage( sourceImage, 0, 0, null);
        
        Sprite sprite = new Sprite(image);
        sprites.put( file, sprite);
        
        return sprite;        
    }

    /**
     * If a resource is not available game will automatically exit.
     * @param string Error message.
     */
    private void fail(String string) {
        // we're pretty dramatic here, if a resource isn't available
        // we dump the message and exit the game
        System.err.println(string);
        System.exit(0);
    }
}
