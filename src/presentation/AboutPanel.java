/*
 * AboutPanel.java
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

import javax.swing.JPanel;
import javax.swing.JTextPane;

import logic.HighScoreManager;
import logic.SaveManager;
import logic.ScreenManager;
import logic.SoundManager;

/**
 *
 * @author Tansel
 */
public class AboutPanel extends PangPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6379797386388552036L;
	private JTextPane textPane = new JTextPane();
	
	/**
	 * 
	 * @param sm
	 * @param save
	 * @param sound
	 * @param score
	 * @param cardPanel
	 * @param layout
	 */
    public AboutPanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
    		JPanel cardPanel, CardLayout layout) {
        super(sm, save, sound, score, cardPanel, layout);
        
        textPane.setContentType("text/html");
        textPane.setText( "" +
        		"<center><span style=\"font-size: 24pt;\"><strong>PangPang</strong></span> v1.0</center><br /><br /><br />" +
        		"<strong>Author:</strong> Tansel Altınel<br /><br />" +
        		"<strong>Website:</strong> <a href =\"http://www.tanshaydar.com\">www.tanshaydar.com</a><br /><br />" +
        		"Source code is avaliable at tanshaydar.com<br /><br />" +
        		"<strong>Disclaimer:</strong> This game is written for a term project and is far from perfect. It's just an idea" +
        		" for Object Oriented design and implementation. Source code can be used, but credits would be needed.<br />" +
        		"All of the images and sprites (except background images) are made by author. Do not use without permission.<br /><br />" +
        		"No outside library is used, all code is written from scratch by the author.<br />" +
        		"Online HighScore PHP script is taken from http://woogley.net/misc/Highscore/");
        textPane.setEditable(false);
        textPane.setVisible(true);
        textPane.setOpaque(false);
        textPane.setBounds( 300, 100, 400, 400);
        add(textPane);
    }
}
