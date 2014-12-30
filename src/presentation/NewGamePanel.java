/*
 * NewGamePanel.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import logic.GameEngine;
import logic.HighScoreManager;
import logic.SaveManager;
import logic.ScreenManager;
import logic.SoundManager;

/**
 *
 * @author Tansel
 */
public class NewGamePanel extends PangPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5236176098130470092L;
	/**
	 * Levels to be chosen.
	 */
	private JButton[] newGameButton;
	/**
	 * If the game to be created is multiplayer
	 */
	private boolean multiplayer;
	/**
	 * {@link MainPanel}
	 */
	private MainPanel mainPanel;

	/**
	 * 
	 * @param sm
	 * @param save
	 * @param sound
	 * @param score
	 * @param cardPanel
	 * @param layout
	 * @param mainPanel
	 */
	public NewGamePanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
    		JPanel cardPanel, CardLayout layout, MainPanel mainPanel) {
		super(sm, save, sound, score, cardPanel, layout);
        this.mainPanel = mainPanel;
        newGameButton = new JButton[10];
        
        multiplayer = false;

        initializeButtons();
    }
    
	/**
	 * Initializes the new game buttons for first ten levels. If they are unlocked, they will be shown.
	 * If not, they will be greyed out.
	 */
    public void initializeButtons() {
		for( int i = 0; i < 10; i++) {
			newGameButton[i] = new JButton("Level "+ (i+1) );
			newGameButton[i].setBorder(null);
			if( i < 5)
				newGameButton[i].setBounds( sm.getWidth() - 300, 60+i*60, 100, 25);
			else
				newGameButton[i].setBounds( sm.getWidth() - 150, 60+(i%5)*60, 100, 25);
			if( save.getSave().getLevel() < i+1)
				newGameButton[i].setEnabled(false);
			
			newGameButton[i].addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
//	            	System.out.println( getPlayerNo() + " | " + Integer.parseInt (( (JButton)e.getSource()).getText().substring(6, ((JButton)e.getSource()).getText().length() )));
//	            	System.out.println(""+ Integer.parseInt (( (JButton)arg0.getSource()).getText().substring(6, ((JButton)arg0.getSource()).getText().length() )));
					System.out.println("*Game creation is started...");
					final int i = Integer.parseInt (( (JButton)arg0.getSource()).getText().substring(6, ((JButton)arg0.getSource()).getText().length() ));
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							try {
								//We are creating our game engine here
								GameEngine game = new GameEngine(sm, save, sound, score, cardPanel, layout, mainPanel, multiplayer, i);
								//We are adding GameEngine as a JPanel to CardLayot list to show it
								mainPanel.setGame( game);
								cardPanel.add( game, "game");
								//Finally, start the game!
				            	game.startGame();
				            	//Show the game panel!
				            	layout.show(cardPanel, "game");
				            	//Let it take the request for key inputs
				            	game.requestFocus();
							} catch (Exception e) {
								System.out.print("Game creation is interrupted!");
							}
						}
					});
	            	System.out.println("*Game creation is finished...");
	            	//game.startGame( Integer.parseInt (( (JButton)arg0.getSource()).getText().substring(6, ((JButton)arg0.getSource()).getText().length() )), multiplayer);
	            }
	        });
			newGameButton[i].setFocusable(false);
			add( newGameButton[i]);
		}
    }

	public void setMultiplayer( boolean multiplayer) {
		this.multiplayer = multiplayer;
	}
}
