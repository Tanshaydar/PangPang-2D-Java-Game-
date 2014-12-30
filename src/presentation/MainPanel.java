/*
 * MainPanel.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logic.GameEngine;
import logic.HighScoreManager;
import logic.SaveManager;
import logic.ScreenManager;
import logic.SoundManager;

/**
 *
 * @author Tansel
 */
public class MainPanel extends PangPanel{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 7019295328884307599L;
	/**
	 * Panel that players choose specific level to play.
	 * It is same for both single and multiplayer.
	 */
    private NewGamePanel newGamePanel;
    /**
     * Panel that shows high scores.
     */
    private HighScorePanel highScorePanel;
    /**
     * Panel that shows options.
     */
    private OptionsPanel optionsPanel;
    /**
     * Panel that shows help section.
     */
    private HelpPanel helpPanel;
    /**
     * Panel that shows about information.
     */
    private AboutPanel aboutPanel;
    
    /**
     * Continue button is only shown when a game is running.
     */
    private JButton continueButton;
    /**
     * Opens {@link NewGamePanel} for SinglePlayer game.
     */
    private JButton newSinglePlayerGameButton;
    /**
     * Opens {@link NewGamePanel} for MultiPlayer game.
     */
    private JButton newTwoPlayerGameButton;
    /**
     * Opens {@link HighScorePanel}
     */
    private JButton highScoresButton;
    /**
     * Opens {@link OptionsPanel}
     */
    private JButton optionsButton;
    /**
     * Opens {@link HelpPanel}
     */
    private JButton helpButton;
    /**
     * Opens {@link AboutPanel}
     */
    private JButton aboutButton;
    /**
     * Exits game.
     */
    private JButton exitButton;
    
    /**
     * {@link GameEngine}
     */
    private GameEngine game;

    /**
     * Initializes all other panels.
     * @param sm
     * @param save
     * @param sound
     * @param score
     * @param cardPanel
     * @param layout
     */
    public MainPanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
    		JPanel cardPanel, CardLayout layout) {
    	super(sm, save, sound, score, cardPanel, layout);
        
        newGamePanel = new NewGamePanel(sm, save, sound, score, cardPanel, layout, this);
        highScorePanel = new HighScorePanel(sm, save, sound, score, cardPanel, layout);
        optionsPanel = new OptionsPanel(sm, save, sound, score, cardPanel, layout);
        helpPanel = new HelpPanel(sm, save, sound, score, cardPanel, layout);
        aboutPanel = new AboutPanel(sm, save, sound, score, cardPanel, layout);
        
        initializeButtons();
    }
    
    /**
     * Puts all panels to card deck, which is card layout.
     */
    public void initializeCardPanel() {
        cardPanel.add( this, "main");
        cardPanel.add( newGamePanel, "newGame");
        cardPanel.add( highScorePanel, "scores");
        cardPanel.add( optionsPanel, "options");
        cardPanel.add( helpPanel, "help");
        cardPanel.add( aboutPanel, "about");
    }
    
    /**
     * Initializes main menu buttons.
     */
    public void initializeButtons() {
    	
    	continueButton = new JButton( "Continue", new ImageIcon("images//menu//buttons//default_button.png"));
    	continueButton.setHorizontalTextPosition(SwingConstants.CENTER);
        continueButton.setFont( new Font("Arial", Font.BOLD, 18));
        continueButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        continueButton.setBounds( sm.getWidth()-175, 75, 150, 50);
        continueButton.setBorder(null);
        continueButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show( cardPanel, "game");
				gameIsPaused(false);
				game.mainMenu(false);
				game.requestFocus();
				game.grabFocus();
			}
		});
        continueButton.setVisible(false);
    	
        newSinglePlayerGameButton = new JButton( "Single Player", new ImageIcon("images//menu//buttons//default_button.png"));
        newSinglePlayerGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newSinglePlayerGameButton.setFont( new Font("Arial", Font.BOLD, 18));
        newSinglePlayerGameButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        newSinglePlayerGameButton.setBounds( sm.getWidth()-175, 75, 150, 50);
        newSinglePlayerGameButton.setBorder(null);
        newSinglePlayerGameButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGamePanel.setMultiplayer(false);
				layout.show( cardPanel, "newGame");
			}
		});
        
        newTwoPlayerGameButton = new JButton( "Two Players", new ImageIcon("images//menu//buttons//default_button.png"));
        newTwoPlayerGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newTwoPlayerGameButton.setFont( new Font("Arial", Font.BOLD, 18));
        newTwoPlayerGameButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        newTwoPlayerGameButton.setBounds( sm.getWidth()-175, 150, 150, 50);
        newTwoPlayerGameButton.setBorder(null);
        newTwoPlayerGameButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGamePanel.setMultiplayer( true);
				layout.show( cardPanel, "newGame");
			}
		});
        
        highScoresButton = new JButton( "High Scores", new ImageIcon("images//menu//buttons//default_button.png"));
        highScoresButton.setHorizontalTextPosition(SwingConstants.CENTER);
        highScoresButton.setFont( new Font("Arial", Font.BOLD, 18));
        highScoresButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        highScoresButton.setBounds( sm.getWidth()-175, 225, 150, 50);
        highScoresButton.setBorder(null);
        highScoresButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show( cardPanel, "scores");
				highScorePanel.update();
			}
		});
        
        optionsButton = new JButton( "Options", new ImageIcon("images//menu//buttons//default_button.png"));
        optionsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        optionsButton.setFont( new Font("Arial", Font.BOLD, 18));
        optionsButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        optionsButton.setBounds( sm.getWidth()-175, 300, 150, 50);
        optionsButton.setBorder(null);
        optionsButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show( cardPanel, "options");
			}
		});
        
        helpButton = new JButton( "Help", new ImageIcon("images//menu//buttons//default_button.png"));
        helpButton.setHorizontalTextPosition(SwingConstants.CENTER);
        helpButton.setFont( new Font("Arial", Font.BOLD, 18));
        helpButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        helpButton.setBounds( sm.getWidth()-175, 375, 150, 50);
        helpButton.setBorder(null);
        helpButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show( cardPanel, "help");
			}
		});
        
        aboutButton = new JButton( "About", new ImageIcon("images//menu//buttons//default_button.png"));
        aboutButton.setHorizontalTextPosition(SwingConstants.CENTER);
        aboutButton.setFont( new Font("Arial", Font.BOLD, 18));
        aboutButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        aboutButton.setBounds( sm.getWidth()-175, 450, 150, 50);
        aboutButton.setBorder(null);
        aboutButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show( cardPanel, "about");
			}
		});
        
        exitButton = new JButton( "Exit", new ImageIcon("images//menu//buttons//default_button.png"));
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setFont( new Font("Arial", Font.BOLD, 18));
        exitButton.setPressedIcon( new ImageIcon("images//menu//buttons//button_reverse.png"));
        exitButton.setBounds( sm.getWidth()-175, 525, 150, 50);
        exitButton.setBorder(null);
        exitButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
        
        add( continueButton);
        add( newSinglePlayerGameButton);
        add( newTwoPlayerGameButton);
        add( highScoresButton);
        add( optionsButton);
        add( helpButton);
        add( aboutButton);
        add( exitButton);
        repaint();
    }
    
    /**
     * Overridden method to not add a back button to main menu,
     * which would be quite pointless.
     */
    public void addBackButton() {}
    
    /**
     * When game is paused continue button is shown and new game buttons are hidden.
     * @param paused
     */
    public void gameIsPaused( boolean paused) {
    	continueButton.setVisible( paused);
    	newSinglePlayerGameButton.setVisible( !paused);
    	newTwoPlayerGameButton.setVisible( !paused);
    }
    
    public void gameOver(){
    	continueButton.setVisible( false);
    	newSinglePlayerGameButton.setVisible( true);
    	newTwoPlayerGameButton.setVisible( true);
    }
    
    public void setGame( GameEngine game) {
    	this.game = game;
    }
}
