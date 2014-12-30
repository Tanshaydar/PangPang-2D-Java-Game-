/*
 * OptionsPanel.java
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
public class OptionsPanel extends PangPanel{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6337470176486265217L;
	private static final String help = "Sorry, nothing implemented here. Also game has no sound.\n" +
			"You can use the source code and implement them though!";
	private JTextArea pane;

	public OptionsPanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
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
