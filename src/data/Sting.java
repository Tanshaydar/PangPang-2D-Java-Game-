/*
 * Sting.java
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

import java.util.Random;
import java.util.concurrent.TimeUnit;

import logic.GameEngine;
import logic.ScreenManager;

/**
 *
 * @author Tansel
 */
public class Sting extends GameEntity{
	
	/** This boolean holds the chain of Sting
	 * false -> Rope
	 * true -> Chain
	 */
	private boolean chain = false;
	/** This boolean will prevent from double shooting with one Default Sting*/
	private boolean shot = false;
	/** This long will hold the time and prevent double collision checking
	 * for Chaint type of stings, thus prevent from GameEngine to miscalculate
	 * the number of Balloons
	 */
	private long tick;
	/**
	 * This long will determine when the chain type of variable  
	 */
	private long life;
	
	/** This variable holds the current player that shot this sting*/
	private Player player;
	
	/**
	 * To determine if a bonus will be created when a {@link Balloon} is shot.
	 */
	private Random rand = new Random();
	
    /**
     * 
     * @param ge
     * @param file
     * @param x
     * @param y
     * @param sm
     * @param chain
     * @param player
     */
    public Sting( GameEngine ge, String file, int x, int y, ScreenManager sm, boolean type, Player player){
        super( ge, file, x, y, sm);
        this.chain = type;
        this.player = player;
        
        dx = 0;
        dy = -200;
        if( chain)
        	life = System.currentTimeMillis();
    }

	/**
	 * This function will check the collision with only
	 * balloons and obstacles since the only things it can
	 * shoot is them 
	 */
	public void collidedWith(GameEntity entity) {
		//If player shot a balloon, let's explode it
		if( entity instanceof Balloon) {
			//This will prevent double shooting if the Sting is not the Chain chain
			if( !chain) {
				if( shot)
					return;
			} else {
				if( TimeUnit.MILLISECONDS.toSeconds( tick - System.currentTimeMillis()) > -(0.3)) {
					System.out.println("Don't collide!");
					return;
				}
			}
			
			Balloon balloon = (Balloon) entity;
			
			if( balloon.getSize() == 1) {
				player.addScore( balloon.getSize());
				if( rand.nextInt( 15) == 0 ) {
					ge.addEntity( new Bonus(ge, "images//bonus//life.png", balloon.getX(), balloon.getY(), sm));
					System.out.println("*Bonus added!");
				}
				ge.removeEntity(balloon);
				if( !chain) {			//If the sting is a ROPE
					ge.removeEntity(this);
					vanished();
				}
			} else {
				ge.addEntity( new Balloon(ge, "images//balloon//", balloon.getX(), balloon.getY(), sm, balloon.getSize()/2, true));
				ge.addEntity( new Balloon(ge, "images//balloon//", balloon.getX() + balloon.sprite.getWidth()/2, balloon.getY(), sm, balloon.getSize()/2));
				player.addScore( balloon.getSize());
				if( rand.nextInt( 15) == 0 ) {
					ge.addEntity( new Bonus(ge, "images//bonus//life.png", balloon.getX(), balloon.getY(), sm));
					System.out.println("*Bonus added!");
				}
				ge.removeEntity(balloon);
				if( !chain) {
					ge.removeEntity(this);
					vanished();
				}
			}
			
			if( !chain)
				shot = true;
			else
				tick = System.currentTimeMillis();
		}
	}
	
	/**
	 * When a chain reaches an {@link Obstacle} or boundary of screen it will stop moving.
	 */
    public void move( long delta) {
    	super.move(delta);
    	if( y < 0) {
    		if( !chain) {
    			vanished();
    			ge.removeEntity(this);
    		} else {
    			stop();
    		}
    	}
    	if( chain)
    		if( TimeUnit.MILLISECONDS.toSeconds( life - System.currentTimeMillis()) < - 5) {
    			ge.removeEntity(this);
    			vanished();
    		}
    }
    
    /**
     * If a sting vanishes, player can now shoot another sting.
     */
    public void vanished(){
    	player.stingVanished();
    }
    
    public void stop(){
    	dx = 0;
    	dy = 0;
    }
    
    public boolean isChain() {
    	return chain;
    }
}
