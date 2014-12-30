/*
 * PangPanel.java
 * @package presentation
 *
 * Created on 10.Ara.2011
 *
 * Copyright(c) Tansel Altınel.  All Rights Reserved.
 * For more information about the project
 * or the code please contact me: tansel@tanshaydar.com
 *
 */
package presentation;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logic.HighScoreManager;
import logic.SaveManager;
import logic.ScreenManager;
import logic.SoundManager;
import logic.SpriteManager;
import data.Sprite;

/**
 *
 * @author Tansel
 */
public abstract class PangPanel extends JPanel{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 989953683048975148L;
	/**
	 * This is our {@link ScreenManager}
	 */
	protected ScreenManager sm;
	/**
	 * This is our {@link SaveManager}
	 */
	protected SaveManager save;
	/**
	 * This is our {@link SoundManager}
	 */
	protected SoundManager sound;
	/**
	 * This is our {@link HighScoreManager}
	 */
	protected HighScoreManager score;
    
	/**
	 * Main {@link JPanel} that holds every panel in itself.
	 */
    protected JPanel cardPanel;
    /**
     * The layout we are using.
     */
    protected CardLayout layout;
    /**
     * The back button that allows almost every panel to traverse back to main menu.
     */
    protected JButton back;
    /**
     * Background of the panels.
     */
    protected Sprite bg;
    
    /**
     * This is an abstract class that every other menu panel will inherit.
     * @param sm	Our screen manager which comes from main.
     * @param save	Our save manager also comes from main.
     * @param sound Our sound manager comes from main.
     * @param score	Our score manager comes from main.
     * @param cardPanel	Our cardpanel
     * @param layout	Our layout
     */
    public PangPanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
    		JPanel cardPanel, CardLayout layout) {
        
    	this.sm = sm;
    	this.save = save;
    	this.sound = sound;
    	this.score = score;
    	
    	this.cardPanel = cardPanel;
        this.layout = layout;
        
        setLayout(null);
        setIgnoreRepaint(true);
        setFocusable(false);

        bg = SpriteManager.get().getSprite("images//menu//menubg.jpg");
        addBackButton();
    }
    
    /**
     * Adds a button to traverse back to main menu
     */
    public void addBackButton() {
        back = new JButton( "Back", new ImageIcon("images//menu//buttons//default_button.png"));
        back.setHorizontalTextPosition(SwingConstants.CENTER);
        back.setFont( new Font("Arial", Font.BOLD, 18));
        back.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        back.setBounds(25, 525, 150, 50);
        back.setBorder(null);
        back.addActionListener( new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
        add(back);
    }
    
    /**
     * When the button "back" is pressed, this method is called.
     */
    public void back() {
    	layout.show( cardPanel, "main");
    	this.repaint();
    }
    
	public void paintComponent (Graphics g){
		super.paintComponent( sm.getDrawGraphics());
		Graphics2D g2d = (Graphics2D) g;
		bg.draw(0, 0, g2d);
	}
}
