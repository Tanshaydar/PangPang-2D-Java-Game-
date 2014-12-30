/*
 * ScreenManager.java
 * @package logic
 *
 * Created on 07.Ara.2011
 *
 * Copyright(c) Tansel Altınel.  All Rights Reserved.
 * For more information about the project
 * or the code please contact me: tansel@tanshaydar.com
 *
 */
package logic;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 *
 * @author Tansel
 */
public class ScreenManager extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6887137990846219773L;
	/** The strategy that allows us to use accelerate page flipping */
    private BufferStrategy strategy;
    /** An instance to let us to use default graphic device's properties */
    private GraphicsDevice device = null;
    /** Game's display mode. Can be tweaked to support multiple resolutions */
    private DisplayMode dispMode = new DisplayMode(1024, 768, 32, 60);
    /** DisplayModeOld lets us to remember default display mode of the screen device */
    private DisplayMode dispModeOld = null;
    /** A boolean that allows us to know whether fullscreen is enabled or not */
    protected boolean fullscreen = false;
    
    /**
     * This is our Screen Manager. It creates and regulates the screen
     * we draw our entities into. It is Double Buffered to deliver a smoother
     * animation.
     * @param fullscreen
     */
    public ScreenManager( boolean fullscreen){
        // create a frame to contain our game
        
        if( fullscreen) {
        	device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            dispModeOld = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
            setResizable(false);
            setUndecorated(false);
            device.setFullScreenWindow( this);
            setVisible(true);
        } else {
        	setIgnoreRepaint(true);
	        setSize( 800, 600);
	        setResizable(false);
	        setUndecorated(true);
	        setLocationRelativeTo(null);
//	        getContentPane().setLayout( new BorderLayout());
	        setVisible(true);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        // Buffer
        createBufferStrategy(2);           
        strategy = getBufferStrategy();
    }
    
    /**
     * Switches between fullscreen and windowed mode.
     * @param fullscreen
     */
    public void setFullScreen( boolean fullscreen) {
        if( this.fullscreen != fullscreen) {
            this.fullscreen = fullscreen;

            setVisible(true);
            strategy.dispose();

            if( !fullscreen) {
                device.setDisplayMode(dispModeOld);
                device.setFullScreenWindow(null);
                this.setSize(800, 600);
                this.setLocationRelativeTo(null);
                this.setVisible(true);
            } else {
                device.setFullScreenWindow(this);
                device.setDisplayMode(dispMode);
            }
            setVisible(true);
            strategy.show();
        }
    }

//    public Graphics2D getGraphics() {
//    	return (Graphics2D)strategy.getDrawGraphics();
//    }
    
    /**
     * 
     * @return Returns the Double Buffered {@link Graphics2D} object.
     */
    public Graphics2D getDrawGraphics(){
    	return (Graphics2D)strategy.getDrawGraphics();
    }
    
    public boolean isFullScreen() {
        return fullscreen;
    }
    
    /**
     * Updates the screen when called.
     */
    public void update(){
        strategy.show();
    }
}
