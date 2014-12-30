/*
 * Obstacle.java
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

import logic.GameEngine;
import logic.ScreenManager;

/**
 *
 * @author Tansel
 */
public class Obstacle extends GameEntity{
	
	/**
	 * This boolean variable will let us to determine
	 * the type of the Obstacle
	 * false = breakable
	 * true = unbreakable
	 */
	private boolean type = false;
    
	/**
	 * 
	 * @param ge
	 * @param file
	 * @param x
	 * @param y
	 * @param sm
	 * @param type
	 */
    public Obstacle( GameEngine ge, String file, int x, int y, ScreenManager sm, boolean type){
        super( ge, file, x, y, sm);
        this.type = type;
    }

    /**
     * When a collision between a {@link Sting} and obstacle,
     * obstacle will be destroyed if it is breakable.
     * It also stops the chain and rope types of stings, and destroys the
     * rope type of sting.
     */
    public void collidedWith(GameEntity entity) {
    	if( entity instanceof Sting) {
    		Sting sting = (Sting) entity;
    		if( !type){
    			ge.removeEntity( this);
    			if( sting.isChain())
    				sting.stop();
    			else {
    				sting.vanished();
    				ge.removeEntity(sting);
    			}
    		} else {
    			if( !sting.isChain()) {
    				sting.vanished();
    				ge.removeEntity(sting);
    			}
    		}
    	}
	}
}
