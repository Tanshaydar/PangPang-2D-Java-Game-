/*
 * GameEntity.java
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
import java.awt.Rectangle;

import logic.GameEngine;
import logic.ScreenManager;
import logic.SpriteManager;

/**
 *
 * @author Tansel
 */
public abstract class GameEntity {
    /** The current x location of this entity */ 
    protected double x;
    /** The current y location of this entity */
    protected double y;
    /** The sprite that represents this entity */
    protected Sprite sprite;
    /** The current speed of this entity horizontally (pixels/sec) */
    protected double dx;
    /** The current speed of this entity vertically (pixels/sec) */
    protected double dy;
    /** The rectangle used for this entity during collisions  resolution */
    private Rectangle own = new Rectangle();
    /** The rectangle used for other entities during collision resolution */
    private Rectangle other = new Rectangle();
    /** The rectangle used for other entities during collision resolution */
    protected ScreenManager sm;
    /** GameEngine object is taken to notify GameEngine on specific occurrences */
    protected GameEngine ge;
    
    /**
     * This is the constructor of our abstract class GameEntity.
     * All objects in the screen are instance of this class.
     * @param ge	{@link GameEngine} object to send specific messages on specific occurrences.
     * @param file	Path to the image.
     * @param x	X position of the object.
     * @param y	Y position of the object.
     * @param sm	{@link ScreenManager} object to determine the boundaries of the screen.
     */
    public GameEntity( GameEngine ge, String file, int x, int y, ScreenManager sm){
        this.sprite = SpriteManager.get().getSprite(file);
        this.x = x;
        this.y = y;
        this.sm = sm;
        this.ge = ge;
    }
    
    /**
     * @return The x position of the entity.
     */
    public int getX() {
        return (int) x;
    }
    
    /**
     * 
     * @return The y position of the entity.
     */
    public int getY() {
        return (int) y;
    }
    
    /**
     * Moves the entity. Probably will be overridden by each object.
     * @param delta is the amount of time that entity will move.
     */
    public void move( long delta) {
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }
    
    /**
     * This method draws the visual of the entity to the screen.
     * Each object draws itself.
     * @param g	A {@link Graphics2D} object coming from {@link GameEngine}
     */
    public void draw( Graphics2D g) {
        sprite.draw((int)x, (int)y, g);
    }
    
    /**
     * This method check the collision between itself and another entity.
     * {@link GameEngine} calls this method for each entity and they return a
     * boolean value if collision occurs.
     * @param entity	Entity to be collided.
     * @return	If collision occurred.
     */
    public boolean collidesWith( GameEntity entity) {
        own.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        other.setBounds((int) entity.x, (int) entity.y, entity.sprite.getWidth(), entity.sprite.getHeight());
        
        return own.intersects(other);
    }
    
    /**
     * Collision responses will take within Game Entities.
     * GameEngine will check for collisions, but every respective
     * response to collisions will be taken care of in different
     * entities like this:
     * -Bonus Entity:.......collision with Player Entity
     * -Player Entity:......collision with Balloon Entity
     * -Sting Entity:.......collision with Balloon Entity
     * -Obstacle Entity:....collision with Sting Entity
     * -Balloon Entity:.....collision with Obstacle Entity
     * 
     * @param entity
     */
    public abstract void collidedWith( GameEntity entity);
}
