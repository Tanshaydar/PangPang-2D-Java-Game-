/*
 * PangMain.java
 * @package presentation
 *
 * Created on 07.Ara.2011
 *
 * Copyright(c) Tansel Altınel.  All Rights Reserved.
 * For more information about the project
 * or the code please contact me: tansel@tanshaydar.com
 *
 */
package presentation;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import logic.HighScoreManager;
import logic.SaveManager;
import logic.ScreenManager;
import logic.SoundManager;

/**
 *
 * @author Tansel
 */
public class PangMain{
    
	/**
	 * Here we create our managers.
	 */
    private ScreenManager sm;
    private HighScoreManager score;
    private SoundManager sound;
    private SaveManager save;
    
    /**
     * These variables are for menu and traversing
     * between menu items.
     */
    private CardLayout layout;
    private JPanel cardPanel;
    private MainPanel mainPanel;
    
    /**
	 * The entry point into the game. We'll simply create an
	 * instance of class which will start the display and menu. 
     */
    public PangMain() {
    	score = new HighScoreManager();
    	sound = new SoundManager();
    	save = new SaveManager();
    	if( save.getSave() == null)
    		save.addSave(1);
    	//We create our screen manager since everything needs to be drawn to the screen
        sm = new ScreenManager( false);
        //We set our title
        sm.setTitle("PangPang");
        sm.setIconImage( new ImageIcon("images//balloon//ball_x1.png").getImage());
        //We are using a cardlayout type of layout to toggle between pages
        layout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setPreferredSize( new Dimension(800, 600));
        cardPanel.setLayout(layout);
        
        //This is our main panel and also main screen
        mainPanel = new MainPanel(sm, save, sound, score, cardPanel, layout);
        mainPanel.initializeCardPanel();
        sm.getContentPane().add( cardPanel); // add the new display components to the container
        
        //Finally, finalize everything and our ScreenManager is ready to be visible!
//        mainPanel.initializeCardPanel();
//        sm.setFullScreen(true);
        //Our main panel is to be shown
        layout.show( cardPanel, "main");
        mainPanel.repaint();
        sm.setVisible(true);
    }

    /**
     * We want to utilized every hardware related to deliver a smoother and stronger experience.
     * We are using DirectX accelerating for Windows, and OpenGL for any other platforms.
     * 
     * If the system is not capable of acceleration, we simply doesn't call any acceleration option.
     * */
    private static void createAndShowGUI()
    {
        //Check if the system is capable of acceleration
    	if( GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getImageCapabilities().isAccelerated()) {
            //These are main settings for using graphic card
            System.setProperty("sun.java2d.transaccel", "True");
            System.setProperty("sun.java2d.ddforcevram", "True");
            System.setProperty("sun.java2d.noddraw", "False");
            //If operating system is Windows, then use D3D, else use OpenGL
            if(  System.getProperty("os.name").startsWith("Windows"))
                System.setProperty("sun.java2d.d3d", "True");
            else
                System.setProperty("sun.java2d.opengl", "True");
    	}
    	//Finally create our entry point of the game
    	PangMain pang = new PangMain();
    }

    /**
     * Finally, our main method.
     * @param argv
     */
    public static void main(String argv[]) {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
