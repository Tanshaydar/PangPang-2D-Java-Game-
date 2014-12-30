/*
 * GameEngine.java
 * @package logic
 *
 * Created on 07.Dec.2011
 *
 * Copyright(c) Tansel Altınel.  All Rights Reserved.
 * For more information about the project
 * or the code please contact me: tansel@tanshaydar.com
 *
 */
package logic;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentation.MainPanel;

import data.Balloon;
import data.GameEntity;
import data.Obstacle;
import data.Player;
import data.Sprite;

/**
 *
 * @author
 */
@SuppressWarnings("serial")
public class GameEngine extends JPanel{
	/**
	 * This boolean is for testing the GameEngine.
	 * Do not change this value to true!
	 */
	private static final boolean debug = false;
	/**
	 * These variables are the logic layer related managers
	 * ScreenManager is referenced by the presentation layer
	 * since it was created there.
	 */
    /** Our Screen manager. This variable will be delivered by presentation tier by reference*/
    private ScreenManager sm;
    /** Our Sound manager */
    private SoundManager sound;
    /** Our HighScore manager */
    private HighScoreManager score;
    /** Our Save manager */
    private SaveManager save;
    
    /*//////////////////////////////////////////////////////
     * 
     *//////////////////////////////////////////////////////
    
    /**
     * These instances are for us to return the main menu when ESC is pressed.
     */
    /** We need an instance of the layout when we lost our focus on the game screen */
    private CardLayout layout;
    private JPanel cardPanel;
    private MainPanel mainPanel;
 
    /*//////////////////////////////////////////////////////
     * 
     *//////////////////////////////////////////////////////
    /**
     * Game logic related variables here.
     */
    /** True if the game is currently "running", i.e. the game loop is looping */
    private boolean gameRunning = true;
    /** True if the game is currently "paused" */
    private boolean pause = false;
    /** The message to display which waiting for a key press */
    private String message = "To Start Level";
    /** The message to display which waiting for a key press */
    private String message2 = "Press Any Key";
    /** This integer holds the level number. Default value is 1 */
    private int level = 1;
    /** This boolean let us know if the game is initialized as multiplayer. Default value is false */
    private boolean multiplayer = false;
    /** On rare occasions, our game will stop for user to press any key on keyboard to continue
     * to prevent any unexpected occurrences.
     */
    private boolean waitingForKeyPressed = true;
    /** This variable is for determining if the game is on main menu
     * Because game will be paused and it will be prevented from
     * rendering again and again.
     */
    private boolean isMainMenu = false;
    /** For the widh of the rounded rectangle to be drawn before the screen text*/
    private int rounded = 0;
    /**
     *  This integer holds the number of the valus on the screen to determine if
     * if the level was cleared or not.
     */
    private int balloonCount = 0;
    
    private boolean timeStop = false;
    private long timeStopTime;
    
    /**
     * Dragons here:
     *               .==.        .==.
     *              //`^\\      //^`\\
     *		   // ^ ^\(\__/)/^ ^^\\
     *		  //^ ^^ ^/6  6\ ^^ ^ \\
     *		 //^ ^^ ^/( .. )\^ ^ ^ \\
     *		// ^^ ^/\| v""v |/\^ ^ ^\\
     *         // ^^/\/ /  `~~`  \ \/\^ ^\\
     *        -----------------------------
     */
    
    /**
     * Here the Data layer entities are getting prepared to be on the stage.
     * Finally, a chance to be star!
     */
    /** This arraylist holds all the entities on the screen to check collisions and
     * make drawing works easier, withouth checking too many types. In the end, everything
     * on the screen is a GameEntity object.
     */
    private ArrayList entities = new ArrayList();
    /** This arraylist holds the objects to be removed at the end of each game loop*/
    private ArrayList removeList = new ArrayList();
    /** This is one of the mosp important game objects: Player One. It is created for both
     * single and multiplayer games.
     */
    private Player playerOne = null;
    /** Player Two is created on only multiplayer games. Multiplayer game is decided by the
     * boolean variable @multiplayer 
     */
    private Player playerTwo = null;
    
    /** Our game screen background. Just to deliver a better experience. It is a Sprite. */
    private Sprite background;
    
    /**
     * And finally to prevent to write paths for images every time
     * we declare them here to make the future changes and
     * coding below easier.
     */
    private static final String playerPath = "images//player//playerOne//right.png";
    private static final String playerPath2 = "images//player//playerTwo//left.png";
    private static final String balloonPath = "images//balloon//";
    private static final String obstaclePath = "images//obstacle//";
    
    /**
     * Here our constructor, GameEngine is being initialized. Our GameEngine is a JPanel
     * to be shuffled in CardLayout system.
     * @param sm	ScreenManager object
     * @param cardPanel	cardPanel to switch between menu and game
     * @param layout	layout to choose between menu and game
     * @param multiplayer	to set the game to be initialized to be multiplayer or not
     * @param mainPanel	to set menu options like continue button
     */
    public GameEngine( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score, 
    		JPanel cardPanel, CardLayout layout, MainPanel mainPanel, boolean multiplayer, int level){
        
    	this.sm = sm;
        this.save = save;
        this.sound = sound;
        this.score = score;
        
        this.cardPanel = cardPanel;
        this.layout = layout;
        this.mainPanel = mainPanel;
        
        this.multiplayer = multiplayer;
        this.level = level;
        
        setFocusable(true);
    	System.out.println("*GameEngine is created");
    	addKeyListener( new KeyInputHandler());
    	requestFocus();
    }
    
    /**
     * This method initializes our game for every level.
     * It takes two parameters, as level and multiplayer.
     * @param level	This parameter helps us to initialize a certain level.
     * @param multiplayer	This parameter helps us to initialize second character if our game is multiplayer
     */
    public void initialize( int level, boolean multiplayer) {
    	if( save.getSave().getLevel() < level)
    		save.addSave(level);
    	entities.clear();
    	timeStop = false;
//    	System.out.println("*Music initialization started...");
//    	sound.initializeBackgroundMusic(level);
//    	System.out.println("*Music initialization successful!...");
    	System.out.println("**************************************************");
    	System.out.println("****	Started to initialize...");
    	System.out.println("****	Level: " + level);
    	System.out.println("****	Multiplayer: " + multiplayer);
    	System.out.println("**************************************************");
    	this.level = level;
    	this.multiplayer = multiplayer;
    	
    	//Create Player One if not null
    	if( playerOne == null) {
    		System.out.println("*Player One is null.");
    		playerOne = new Player( this, playerPath, sm.getWidth()/2 - 21, sm.getHeight()-54, sm, "playerOne");
    		entities.add(playerOne);
    	} else {
    		playerOne.initialize();
    		playerOne.setX( sm.getWidth()/2 - 25);
    		entities.add(playerOne);
    	}
    	
    	//Create Player Two | Only if it is multiplayer!
    	if( multiplayer) {
    		if( playerTwo == null) {
    			playerTwo = new Player( this, playerPath2, sm.getWidth()/2 + 21, sm.getHeight()-54, sm, "playerTwo");
    			entities.add(playerTwo);
    		} else {
    			playerTwo.initialize();
    			playerTwo.setX( sm.getWidth()/2 + 25);
    			entities.add(playerTwo);
    		}
    	}
    	
    	if( level <= 10)
    		background = SpriteManager.get().getSprite("images//bg//bg" + level + ".jpg");
    	
    	/**
    	 * After this point initialization will depend
    	 * on the level. We have ten level for differently
    	 * and carefully designed. Other levels are just for
    	 * high scores.
    	 */
    	switch ( level) {
		case 1:
	        entities.add( new Balloon( this, balloonPath, 20, 20, sm, 4));
	        addBalloon();
			break;
		case 2:
			entities.add( new Balloon( this, balloonPath, sm.getWidth()/2 - 100, 5, sm, 4));
			addBalloon();
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2 -150, sm.getHeight()/2, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2, sm.getHeight()/2, sm, false));
			break;
		case 3:
			entities.add( new Balloon( this, balloonPath, 10, 10, sm, 4));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()/2 - 50, sm.getHeight()/2 - 50, sm, 2));
			addBalloon();
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", 50, 250, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth() - 200, 250, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2 -150, sm.getHeight()/2 + 100, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2, sm.getHeight()/2 + 100, sm, false));
			break;
		case 4:
			entities.add( new Balloon( this, balloonPath, 10, 10, sm, 8));
			addBalloon();
			break;
		case 5:
			entities.add( new Balloon( this, balloonPath, 10, 10, sm, 4));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth() - 210, 10, sm, 4, true));
			addBalloon();
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", 50, 250, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth() - 200, 250, sm, true));
			break;
		case 6:
			entities.add( new Balloon( this, balloonPath, 10, 10, sm, 4));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth() - 210, 10, sm, 4, true));
			addBalloon();
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", 50, 250, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth() - 200, 250, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2- 50, 100, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2- 50, sm.getHeight() - 100, sm, false));
			break;
		case 7:
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 0, 450, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 150, 450, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 300, 450, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 450, 450, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 600, 450, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 750, 450, sm, false));
			entities.add( new Balloon( this, balloonPath, 5, 5, sm, 8));
			addBalloon();
			break;
		case 8:
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 156, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 306, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()-55, 6, sm, 1, true));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()-55, 156, sm, 1, true));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()-55, 306, sm, 1, true));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()/2-25, 140, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()/2-25, 250, sm, 1, true));
			addBalloon();
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", 0, 150, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", 0, 300, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", 0, 450, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth()/2-75, 200, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth()/2-75, 400, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth()-150, 150, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth()-150, 300, sm, true));
			entities.add( new Obstacle( this, obstaclePath + "staticObstacle.png", sm.getWidth()-150, 450, sm, true));
			break;
		case 9:
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, 5, 6, sm, 1));
			addBalloon();
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 0, 150, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 0, 300, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", 0, 450, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2-75, 200, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()/2-75, 400, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()-150, 150, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()-150, 300, sm, false));
			entities.add( new Obstacle( this, obstaclePath + "breakableObstacle.png", sm.getWidth()-150, 450, sm, false));
			break;
		case 10:
			entities.add( new Balloon( this, balloonPath, 5, 5, sm, 8));
			addBalloon();
			entities.add( new Balloon( this, balloonPath, sm.getWidth()-405, 5, sm, 8, true));
			addBalloon();
			break;
		default:
			background = SpriteManager.get().getSprite("images//bg//bg1.jpg");
			entities.add( new Balloon( this, balloonPath, 10, 10, sm, 8));
			addBalloon();
			break;
		}
//    	System.out.println("*Initialization is finished in 3... 2... 1...");
//    	sound.playBackgroundMusic();
        System.out.println("*Initialization is finished.");
    }
    
    /**
     * In this method we add new entity (like bonus or sting) to our
     * GameEntity list.
     * @param entity is the new entity to be added.
     */
    public void addEntity( GameEntity entity) {
    	if( entity instanceof Balloon)
    		addBalloon();
    	entities.add(entity);
    }
    
    /**
     * This method removes a certain entity from the list.
     * Specifically, it destroys entities once their job is done; but also
     * can prevent ceased player to be drawn on the screen.
     * @param entity
     */
    public void removeEntity( GameEntity entity) {
    	if( entity instanceof Balloon)
    		deleteBalloon();
        removeList.add(entity);
    }
    
    /**
     * This method decides if the game is over or not.
     * It is called from {@link Player} class so that
     * player object decides game's state.
     */
    public void notifyDeath() {
    	if( !multiplayer) {
    		if( playerOne.getLives() < 0) {
    			mainMenuDeath();
    			gameRunning = false;
    			String highScoreName = (String)JOptionPane.showInputDialog(this,
					"You've made a new score: " + playerOne.getScore()
				    + "\nPlease enter your name:", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
				if( highScoreName != null) {
				    score.addScore( playerOne.getScore(), highScoreName);
				    System.out.println( highScoreName + ": " + playerOne.getScore());
				}
				mainPanel.repaint();
    		}
    	} else {
    		if( playerOne.getLives() < 0 && playerTwo.getLives() < 0) {
    			mainMenuDeath();
    			gameRunning = false;
    			String highScoreName = (String)JOptionPane.showInputDialog(this,
                        "You've made a newscore: " + ( playerOne.getScore() + playerTwo.getScore())
                    + "\nPlease enter your name:", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                if( highScoreName != null) {
                	highScoreName += "(Multi)";
                    score.addScore( ( playerOne.getScore() + playerTwo.getScore()), highScoreName);
                    System.out.println( highScoreName + ": " + ( playerOne.getScore() + playerTwo.getScore()));
                	mainPanel.repaint();
    			}
    		}
    		if( playerOne.getLives() < 0)
    			removeEntity(playerOne);
    		if( playerTwo.getLives() < 0)
    			removeEntity(playerTwo);
    	}
    }
    
    /**
     * When a level finished we need a player input
     * to begin the new level.
     */
    public void notifyLevelEnd() {
//    	sound.stopBackgroundMusic();
//    	sound.playLevelEnd();
        message = "Level Completed!";
        waitingForKeyPressed = true;
    }
    
    /**
     * It's pretty much obvious, this method draws every object in the list.
     * On some special occasions, this method may alter the transparency and
     * it also draws the stats like scores and level.
     * @param g	This parameter is coming from {@link ScreenManager} as it is
     * Double Buffered and produces a {@link Graphics2D} object.
     */
    public void draw( Graphics2D g) {
    	//Draw Entities
        for( int i = 0; i < entities.size(); i++) {
            GameEntity entity = (GameEntity) entities.get(i);
            if( entity instanceof Player) {
            	Player player = (Player) entity;
            	if( player.isResurrect()) {
                    g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                    player.draw(g);
                    g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    if( TimeUnit.MILLISECONDS.toSeconds(player.resurrection() - System.currentTimeMillis()) < -3)
                    	player.setResurrect(false);
            	}
            	else
            		entity.draw( g);
            }
            else
            	entity.draw( g);
        }
        
        //Draw transparent black backgrounds
        g.setColor(Color.BLACK);
        g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.fillRoundRect( sm.getWidth()/2 -25, 0, 85, 40, 10, 10);
        g.fillRoundRect( 0, 0, 75, 40, 10, 10);
        if( multiplayer)
        	g.fillRoundRect( sm.getWidth()-75, 0, 75, 40, 10, 10);
        g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        //Draw strings
        g.setColor(Color.WHITE);
        g.drawString( "Level: " + level, sm.getWidth()/2 -20, 15);
        if( playerOne.getLives() >= 0)
        	g.drawString( "Lives: " + playerOne.getLives(), 10, 15);
        else
        	g.drawString( "-Ceased-", 10, 15);
        g.drawString( "Score: " + playerOne.getScore(), 10, 30);
        
        if( multiplayer) {
        	g.drawString( "Total: " + ( playerOne.getScore() + playerTwo.getScore()), sm.getWidth()/2 -20, 30);
        	if( playerTwo.getLives() >= 0)
        		g.drawString( "Lives: " + playerTwo.getLives(), sm.getWidth()-65, 15);
        	else
        		g.drawString( "-Ceased-", sm.getWidth()-65, 15);
            g.drawString( "Score: " + playerTwo.getScore(), sm.getWidth()-65, 30);
        }
    }
    
    /**
     * This method moves all the entities in the list.
     * Of course, it only calls entities' move method.
     * @param delta This parameter is send to entities to determine how much they will move.
     */
    public void moveEntities( long delta) {
        for( int i = 0; i < entities.size(); i++) {
            GameEntity entity = (GameEntity) entities.get(i);
            if( timeStop) {
            	if( TimeUnit.MILLISECONDS.toSeconds( timeStopTime - System.currentTimeMillis()) < -8)
            		timeStop = false;
            	else
            		if( !(entity instanceof Balloon))
            			entity.move(delta);
            }
            else
            	entity.move(delta);
        }
    }
    
    /**
     * This method doesn't actually check collisions, but instead
     * calls entities to check their own collisions. Every game entity
     * is responsible from its own collision and after collision message. 
     */
    public void checkCollisions() {
        for( int i = 0; i < entities.size(); i++) {
            for( int j = 0; j < entities.size(); j++) {
                GameEntity own = (GameEntity) entities.get(i);
                GameEntity other = (GameEntity) entities.get(j);
                
                if( own.collidesWith(other)) {
                    own.collidedWith(other);
                    other.collidedWith(own);
                }
            }
        }
    }

    /**
     * This method creates a new thread for game to start.
     * This is the entry point of our actual game, and gameloop
     * takes place here. It's basically a new {@link Runnable} and
     * when the level is chosen from game panel, it initializes
     * and starts the game.
     */
    public void startGame() {
    	System.out.println("*Game is started.");
        //Initialize our game!
        System.out.println("*Multiplayer: " + multiplayer);
        initialize( level, multiplayer);
        
        Runnable r = new Runnable() {
			
			@Override
			public void run() {
			   try {
				   long lastLoopTime = System.currentTimeMillis();
				   while ( gameRunning) {
						long time = System.currentTimeMillis() - lastLoopTime;
						lastLoopTime = System.currentTimeMillis();
			            Graphics2D g = sm.getDrawGraphics();
			            g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

			            if( g.getFontMetrics().stringWidth( message) >  g.getFontMetrics().stringWidth( message2))
			            	rounded = g.getFontMetrics().stringWidth( message);
			            else
			            	rounded = g.getFontMetrics().stringWidth( message2);
			            
			            //Draw the background image!
		            	background.draw(0, 0, g);

			            
			            //Move entities if nothing particular happened
			            if( !waitingForKeyPressed)
			                moveEntities( time);

			            //Draw all entities to the screen
		            	draw( g);
		            	
			            //If game is waiting for a message to be shown up 
			            //draw the message and wait for a key input
			            if( waitingForKeyPressed) {
			            	g.setColor(Color.BLACK);
			                g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			                g.fillRoundRect( (800 - rounded)/2 - 10, 230, rounded+20, 90, 10, 10);
			                g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			                g.setColor(Color.WHITE);
			                g.drawString( message, (800 - g.getFontMetrics().stringWidth(message))/2, 250);
			                g.drawString( message2, (800 - g.getFontMetrics().stringWidth(message2))/2, 300);
			            }
			            
			            //Check collisions
			            checkCollisions();
			            //Remove any entity that has been marked up for clearing up
			            entities.removeAll(removeList);
			            removeList.clear();
			            
//			            debug();
			            
			            //Now, drawing is complete and graphics should be cleared up
			            //Also buffer should be flipped over
		            	g.dispose();
			            if( !isMainMenu)
			            	sm.update();
			            
			            //Sleep our thread...
						Thread.sleep( 10);
				   }
			   } catch (InterruptedException iex) {
			      System.err.println("*Game is interrupted unexpectedly!");
			   }
			}
		};
		
		Thread gameLoop = new Thread(r);
		gameLoop.start();
    }

    /**
     * This method pauses and unpauses the game.
     * Simply, when specific button is pressed, this method
     * is callsed.
     * @param pause	Is game paused?
     */
    public void pause( boolean pause) {
    	System.out.println("*Pause method is called: " + pause);
    		this.pause = pause;
    		if( pause) {
    			message = "Game is paused!";
    			message2 = "Press any key to continue";
    			waitingForKeyPressed = true;
    		} else {
    			message2 = "Press any key";
    			waitingForKeyPressed = false;
    		}
    }
    
    /**
     * This method switches between main menu and our game.
     * @param isMainMenu are we in main menu or game.
     */
    public void mainMenu( boolean isMainMenu) {
    	this.isMainMenu = isMainMenu;
    	if( isMainMenu) {
    		pause( isMainMenu);
    		mainPanel.gameIsPaused( isMainMenu);
    		layout.show(cardPanel, "main");
    		System.out.println("*Game is paused and returned to main menu.");	
    	} else {
    		System.out.println("*Game is continuing and returned to game screen.");
    	}
    }
    
    public void mainMenuDeath(){
    	mainPanel.gameOver();
    	layout.show(cardPanel, "main");
		System.out.println("*Game is over and returned to main menu.");
		mainPanel.repaint();
    }

    public void debug() {
    	System.out.println( "GameEngine is focuslable: " + this.isFocusable() );
    	System.out.println( "GameEngine is focus owner: " + this.isFocusOwner());
    }
    
    /**
     * Since one level's state depends on number of balloons
     * every time we add a new balloon, we need to hold the exact number of
     * balloons in the list.
     */
    public void addBalloon() {
    	balloonCount++;
    }
    /**
     * Same thing as addballoon method applies here but in reverse order.
     * However, a new level initialization signal takes here as when the
     * number of balloons are 0, level is finished.
     */
    public void deleteBalloon(){
    	balloonCount--;
    	if( balloonCount == 0) {
    		initialize( ++level, multiplayer);
    		waitingForKeyPressed = true;
    		message = "Congrats! You finished the level " + (level-1) + "";
    	}
    }
    
    /**
     * This method manages the Freezer bonus as all entities on the screen
     * except player is to be frozen.
     * @param timeStop if bonus is taken.
     */
    public void setTimeStop( boolean timeStop) {
    	this.timeStop = timeStop;
    	if( timeStop)
    		timeStopTime = System.currentTimeMillis();
    }

    /**
     * This class manages our key input processes.
     * @author Tansel
     *
     */
	private class KeyInputHandler extends KeyAdapter {
		/** The number of key presses we've had while waiting for an "any key" press */
		private int pressCount = 1;
		
		/**
		 * Notification from AWT that a key has been pressed. Note that
		 * a key being pressed is equal to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		public void keyPressed(KeyEvent e) {
			/** If we're waiting for an "any key" typed then we don't 
			 * want to do anything with just a "press"
			 */
			if ( waitingForKeyPressed) {
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				playerOne.setLeft(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				playerOne.setRight(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				playerOne.fire();
			}
			
			if( multiplayer) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					playerTwo.setLeft(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					playerTwo.setRight(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					playerTwo.fire();
				}
			}
		} 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "released"
			if ( waitingForKeyPressed) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				playerOne.setLeft(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				playerOne.setRight(false);
			}
			
			if( multiplayer) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					playerTwo.setLeft(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					playerTwo.setRight(false);
				}			
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			/** If we're waiting for a "any key" type then check
			 * if we've received any recently.
			 * We may have had a keyType() event from the user 
			 * releasing the shoot or move keys, 
			 * hence the use of the "pressCount" counter.
			 */

			if( e.getKeyChar() == 'p' || e.getKeyChar() == 'P')
				pause( true);
			if( e.getKeyChar() == 'f' || e.getKeyChar() == 'F')
				sm.setFullScreen( true);
			if( e.getKeyChar() == 'g' || e.getKeyChar() == 'G')
				sm.setFullScreen( false);

			if ( waitingForKeyPressed) {
					if (pressCount == 1) {
						// since we've now received our key typed
						// event we can mark it as such and start 
						// our new game
						waitingForKeyPressed = false;
						pause = false;
						pressCount = 0;
					} else
						pressCount++;
			}
			
			// if we hit escape, then quit the game
			if ( e.getKeyChar() == 27) {
				mainMenu( true);
			}
			else
				e.consume();
		}
	}
}
