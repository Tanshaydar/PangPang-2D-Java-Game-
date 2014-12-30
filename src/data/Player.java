/*
 * Player.java
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
import java.util.concurrent.TimeUnit;

import logic.GameEngine;
import logic.ScreenManager;
import logic.SpriteManager;

/**
 *
 * @author
 */
public class Player extends GameEntity{
	
	/** Animation frames */
	private Sprite[] framesRight = new Sprite[4];
	/** Animation frames */
	private Sprite[] framesLeft = new Sprite[4];
	/** Time from the last frame change*/
	private long lastFrameChange = 0;
	/** Frame duration in milliseconds since GameEngine works in terms of milliseconds */
	private long frameDuration = 125;
	/** The current frame */
	private int frameNumber;
	
	/**
	 * To draw the shield bonus when taken, it is created here.
	 */
	private Sprite shield = SpriteManager.get().getSprite("images//player//shield.png");
	
	/**
	 * If player is supposed to go to right.
	 */
	private boolean right = false;
	/**
	 * If player is supposed to go to left.
	 */
	private boolean left = false;
	/**
	 * How many sting one player has shot.
	 */
	private int stingSize = 0;
	
	/**
	 * Number of lives of a player. It is 3 by default.
	 */
	private int lives = 3;
	/**
	 * Score of the player. It is 0 by default.
	 */
	private int score = 0;
	/**
	 * If player entered a resurrection state, which is
	 * when player got shot by a balloon and still has lives
	 * before game over.
	 */
	private boolean resurrect = false;
	/**
	 * Resurrection state time.
	 */
	private long resurrection;
	/**
	 * If player take a shield bonus, s/he will be invincible to the
	 * balloon collisions.
	 */
	private long invincibility;
	/**
	 * These variables are in boolean format
	 * and they represent and activate the bonuses
	 * that player takes during gameplay.
	 */
	private boolean invincible = false;
	private boolean timeStop = false;
	private boolean multipleStings = false;
	private boolean chainSting = false;
	/**
	 * Player Entity object is created.
	 * @param ge
	 * @param file
	 * @param x
	 * @param y
	 * @param sm
	 * @param playerFolder
	 */
    
    public Player( GameEngine ge, String file, int x, int y, ScreenManager sm, String playerFolder){
        super( ge, file, x, y, sm);
        dy = 0;
        dx = 200;
        
        framesLeft[0] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//left.png");
        framesLeft[1] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//left_step_1.png");
        framesLeft[2] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//left.png");
        framesLeft[3] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//left_step_2.png");
		framesRight[0] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//right.png");
		framesRight[1] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//right_step_1.png");
		framesRight[2] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//right.png");
		framesRight[3] = SpriteManager.get().getSprite("images//player//" + playerFolder + "//right_step_2.png");
    }
    
    /**
     * This method is heavily overriden as animation
     * frames are also changing when player moves. 
     */
    public void move( long delta) {
    	if( (x < 0) && (dx < 0) )
    		x = 1;
    	if( (x > (sm.getWidth()) - sprite.getWidth() ) && ( dx > 0))
    		x = sm.getWidth() - sprite.getWidth() + 1;
    	
    	if( right && !left) {
    		dx = ( Math.abs(dx));
    		super.move(delta);
    	} else if( left && !right) {
    		dx = -( Math.abs(dx));
    		super.move(delta);
    	}
    	
    	
    	lastFrameChange += delta;
    	if( lastFrameChange > frameDuration) {
    		lastFrameChange = 0;
    		
    		frameNumber++;
    		if( frameNumber >= framesLeft.length || frameNumber >= framesRight.length)
    			frameNumber = 0;
    		
    		if( left)
    			sprite = framesLeft[frameNumber];
    		if( right)
    			sprite = framesRight[frameNumber];
    	}
    	if( invincible)
    		if( TimeUnit.MILLISECONDS.toSeconds( invincibility - System.currentTimeMillis()) < -10)
    			invincible = false;
    }
    
    /**
     * Player attemts to shoot a {@link Sting}
     */
    public void fire() {
    	if( resurrect)
    		return;
    	if( multipleStings) {
    		if( stingSize < 2) {
    			if( chainSting)
    				ge.addEntity( new Sting( ge, "images//sting//chain.png", (int)this.x + sprite.getWidth()/2, (int)this.y, sm, true, this));
    			else
    				ge.addEntity( new Sting( ge, "images//sting//rope.png", (int)this.x + sprite.getWidth()/2, (int)this.y, sm, false, this));
    			stingSize++;
    		}
    	} else {
    		if( stingSize < 1) {
    			if( chainSting)
    				ge.addEntity( new Sting( ge, "images//sting//chain.png", (int)this.x + sprite.getWidth()/2, (int)this.y, sm, true, this));
    			else
    				ge.addEntity( new Sting( ge, "images//sting//rope.png", (int)this.x + sprite.getWidth()/2, (int)this.y, sm, false, this));
    			stingSize++;
    		}
    	}
    }
    
    /**
     * When player collides with a {@link Balloon}
     * s/he enters a respawn state unless s/he has 0 lives.
     */
	public void collidedWith(GameEntity entity) {
    	if( entity instanceof Balloon) {
    		if( invincible)
    			return;
    		if( !resurrect){
    			this.addLives(-1);
    			resurrect = true;
    			resurrection = System.currentTimeMillis();
    		}
    	}
	}
	
	/**
	 * If player has the shield bonus, this method will
	 * also draw a shield around player.
	 */
    public void draw( Graphics2D g) {
        super.draw(g);
        if( invincible)
        	shield.draw((int)x-10, (int)y-15, g);
    }
    
    /**
     * Number of lives of player.
     * @return
     */
	public int getLives() {
		return lives;
	}

	/**
	 * When player gets a life bonus
	 * his/her life will be increased.
	 * This method also used when collision with balloon occurs
	 * to decrease the life with parameter -1
	 * @param x to set the life player will receiver.
	 */
	public void addLives(int x) {
		if( x == 1)
			lives++;
		else if( x == -1) {
			lives--;
			chainSting = false;
			multipleStings = false;
			ge.notifyDeath();
		}
		else
			return;
	}

	/**
	 * Adds score to player.
	 * @param x quantity of score.
	 */
	public void addScore( int x) {
		score += x;
	}
	
	/**
	 * @return If player has the shield bonus, thus invincible.
	 */
	public boolean isInvincible() {
		return invincible;
	}

	/**
	 * @return If player has freezer bonus.
	 */
	public boolean isTimeStop() {
		return timeStop;
	}

	/**
	 * 
	 * @return If player can shoot two stings at a time.
	 */
	public boolean isMultipleStings() {
		return multipleStings;
	}

	/**
	 * 
	 * @return If player is able to shoot chains.
	 */
	public boolean isChainSting() {
		return chainSting;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
		if( invincible)
			invincibility = System.currentTimeMillis();
	}

	public void setTimeStop(boolean timeStop) {
		this.timeStop = timeStop;
		if( timeStop)
			ge.setTimeStop(timeStop);
	}

	public void setMultipleStings(boolean multipleStings) {
		if( this.multipleStings == multipleStings)
			score += 5;
		this.multipleStings = multipleStings;
	}

	public void setChainSting(boolean chainSting) {
		if( this.chainSting = chainSting)
			score += 5;
		this.chainSting = chainSting;
	}

	public int getScore(){
		return score;
	}
	
	public void setX( int x) {
		this.x = x;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void stingVanished() {
		stingSize--;
		if( stingSize < 0)
			stingSize = 0;
	}
	
	public void initialize(){
		right = false;
		left = false;
		stingSize = 0;
		resurrect = false;
		if( lives < 0)
			lives = 0;
	}

	public boolean isResurrect() {
		return resurrect;
	}

	public void setResurrect(boolean resurrect) {
		this.resurrect = resurrect;
	}
	
	public long resurrection(){
		return resurrection;
	}
}
