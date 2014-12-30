/*
 * Balloon.java
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

import java.awt.Rectangle;

import logic.GameEngine;
import logic.ScreenManager;

/**
 *
 * @author Tansel
 */
public class Balloon extends GameEntity{
	
	/**
	 * Since we have different sizes of balloons, this additional parameter
	 * will let us differentiate the balloons from each other.
	 */
	private int size;
    
	/**
	 * Look for {@link GameEntity}
	 * @param ge
	 * @param file
	 * @param x
	 * @param y
	 * @param sm
	 * @param size
	 */
    public Balloon( GameEngine ge, String file, int x, int y, ScreenManager sm, int size){
        super( ge, (file += "ball_x" + size + ".png"), x, y, sm);
        this.size = size;
        dx = 200;
        dy = 200;
    }
    
    /**
     * Look for {@link GameEntity}
     * @param ge
     * @param file
     * @param x
     * @param y
     * @param sm
     * @param size
     * @param reversedWay	If the balloon should move other direction when created.
     */
    public Balloon( GameEngine ge, String file, int x, int y, ScreenManager sm, int size, boolean reversedWay){
        super( ge, (file += "ball_x" + size + ".png"), x, y, sm);
        this.size = size;
        dx = -200;
        dy = 200;
    }
    
    /**
     * This is the overridden method of actual move method.
     * When a balloon reaches to the boundaries of screen it shall
     * bounce back.
     */
    public void move( long delta) {
        if( x < 0)
            dx = Math.abs(dx);
        else if( x + sprite.getWidth() > sm.getWidth())
            dx = -Math.abs(dx);
        if( y < 0)
            dy = Math.abs(dy);
        else if( y + sprite.getHeight() > sm.getHeight())
            dy = -Math.abs(dy);
        super.move(delta);
    }

    /**
     * This collision method does the necessary processes when
     * collision between an {@link Obstacle} and {@link Balloon} occurs.
     * Specifically, a balloon will bounce back.
     */
    public void collidedWith(GameEntity entity) {
    	if( entity instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) entity;
    		Rectangle rect = new Rectangle( (int)obstacle.x, (int)obstacle.y, obstacle.sprite.getWidth(), obstacle.sprite.getHeight());
//    		sm.getDrawGraphics().fillRect(this.top().x, this.top().y, this.top().width, this.top().height);
//    		sm.getDrawGraphics().fillRect(this.bottom().x, this.bottom().y, this.bottom().width, this.bottom().height);
    		if( rect.intersects( top()))
    			dy = Math.abs(dy);
    		if( rect.intersects( bottom()))
    			dy = -Math.abs(dy);
    	}
    }
    
    public int getX(){
    	return (int) x;
    }
    public int getY(){
    	return (int) y;
    }
    public int getSize(){
    	return size;
    }
    
    /**
     * This new {@link Rectangle} is for checking exact collision between obstacle entity and balloon.
     * @return Top part of the balloon as a new rectangle.
     */
    private Rectangle top(){
    	return new Rectangle( (int)x, (int)y, sprite.getWidth(), sprite.getHeight()/2);
    }
    /**
     * Same as top method.
     * @return Bottom part of the ballon as a new rectangle.
     */
    private Rectangle bottom(){
    	return new Rectangle( (int)x, (int)y + sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight()/2);
    }

}
