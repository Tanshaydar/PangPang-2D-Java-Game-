/*
 * HelpPanel.java
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

import javax.swing.JPanel;
import javax.swing.JTextArea;

import logic.HighScoreManager;
import logic.SaveManager;
import logic.ScreenManager;
import logic.SoundManager;

/**
 *
 * @author Tansel
 */
public class HelpPanel extends PangPanel{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -7296181222270346541L;
	private static final String help = "This is the help section for first time players!\n\n" +
			"PangPang is a mere imitation of original Pang game (aka Buster Bros.)," +
			" which was made by Capcom in 1989.\n\n\n" +
			"How to play?:\n\n" +
			"   Main aim in PangPang is to make highest score ever. To do this, pop all the balloons in the screen to advance\n" +
			"to the next level. You can play as Single Player or Two Players with a friend.\n\n" +
			"   Arrow keys are used for first player, simply Right and Left to move and Up to shoot.\n" +
			"   For second player, keys are A and D to move and W to shoot.\n\n" +
			"   There will be bonuses if you are lucky enough, and these bonuses will make your way to the top easier." +
			"\n\n\n" +
			"Bonuses will be used automatically when you take them. But be careful, some bonuses might have disatvantages\n" +
			"as they have advantages!";
	private JTextArea pane;
	
	/**
	 * 
	 * @param sm
	 * @param save
	 * @param sound
	 * @param score
	 * @param cardPanel
	 * @param layout
	 */
    public HelpPanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
    		JPanel cardPanel, CardLayout layout) {
    	super(sm, save, sound, score, cardPanel, layout);
        pane = new JTextArea(help);
        pane.setEditable(false);
        pane.setFocusable(false);
        pane.setName("About");
        pane.setVisible(true);
        pane.setBounds( 100, 100, 680, 500);
        pane.setOpaque(false);
        pane.setFont( new Font("Arial", Font.TRUETYPE_FONT, 13));
        add(pane);
    }
    
}
