/*
 * Bonus.java
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

import logic.GameEngine;
import logic.ScreenManager;
import logic.SpriteManager;

/**
 *
 * @author Tansel
 */
public class Bonus extends GameEntity{
	
	/**
	 * PangPang game has five different bonuses
	 * This integer variable is to determine which kind of
	 * bonus will it be.
	 * 0: Extra Life
	 * 1: Shield
	 * 2: Freeze Balloons
	 * 3: Duplicate Sting
	 * 4: Chain upgrade for Sting
	 */
	private int type;
	/**
	 * To generate a randomized bonus.
	 */
	private Random rand = new Random();
	/**
	 * If the bonus is already taken, no more collision will occur.
	 */
	private boolean used = false;
	
    /**
     * 
     * @param ge
     * @param file
     * @param x
     * @param y
     * @param sm
     */
    public Bonus( GameEngine ge, String file, int x, int y, ScreenManager sm){
        super( ge, file, x, y, sm);
        
        dx = 0;
        dy = 200;
        
        type = rand.nextInt(5);
        
        switch (type) {
		case 0:
			sprite = SpriteManager.get().getSprite("images//bonus//life.png");
			break;
		case 1:
			sprite = SpriteManager.get().getSprite("images//bonus//shield.png");
			break;
		case 2:
			sprite = SpriteManager.get().getSprite("images//bonus//freezeScreen.png");
			break;
		case 3:
			sprite = SpriteManager.get().getSprite("images//bonus//duplicate.png");
			break;
		case 4:
			sprite = SpriteManager.get().getSprite("images//bonus//chain.png");
			break;
		default:
			System.out.println("*An error occurred while creating bonus. System will exit!");
			System.exit(0);
			break;
		}
    }

    /**
     * When a bonus is taken (collided) by a {@link Player}, it will
     * cast the entity to {@link Player} and will alter the qualifications
     * of player.
     */
	public void collidedWith(GameEntity entity) {
		if( used)
			return;
		if( entity instanceof Player) {
			Player player = (Player) entity;
			switch (type) {
			case 0:
				player.addLives(1);
				break;
			case 1:
				player.setInvincible(true);
				break;
			case 2:
				player.setTimeStop(true);
				break;
			case 3:
				player.setMultipleStings(true);
				break;
			case 4:
				player.setChainSting(true);
				break;
			default:
				break;
			}
			ge.removeEntity( this);
			used = true;
		}
	}
	
	/**
	 * Overriden method move of {@link GameEntity}
	 */
	public void move( long delta) {
		super.move(delta);
		if( y + sprite.getHeight() >= sm.getHeight())
			dy = 0;
	}
    
}
