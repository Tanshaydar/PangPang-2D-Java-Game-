/*
 * HighScorePanel.java
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
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JLabel;
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
public class HighScorePanel extends PangPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -81358183442103059L;
	private JLabel line = new JLabel();
	private JLabel line2 = new JLabel();
	
	private JLabel local = new JLabel("Local Scores");
	private JLabel global = new JLabel("Global Scores");
	private String temp = "";
	private String[] first = new String[2];
	private String scoresLocal = "";
	private String scoresGlobal = "";
	private JTextArea paneLocal = new JTextArea();
	private JTextArea paneGlobal = new JTextArea();
	
	private URL url;
	private InputStream in;
	private BufferedReader reader;
    
    public HighScorePanel( ScreenManager sm, SaveManager save, SoundManager sound, HighScoreManager score,
    		JPanel cardPanel, CardLayout layout) {
    	super(sm, save, sound, score, cardPanel, layout);
    	
    	local.setFont( new Font("Arial", Font.BOLD, 18));
    	local.setBounds( 150, 20, 200, 20);
    	paneLocal.setBounds( 40, 50, 350, 400);
    	global.setFont( new Font("Arial", Font.BOLD, 18));
    	global.setBounds(550, 20, 200, 20);
    	paneGlobal.setBounds(400, 50, 350, 400);

    	update();
    	
        paneLocal.setEditable(false);
        paneLocal.setFocusable(false);
        paneLocal.setVisible(true);
        paneLocal.setOpaque(false);
        paneGlobal.setEditable(false);
        paneGlobal.setFocusable(false);
        paneGlobal.setVisible(true);
        paneGlobal.setOpaque(false);
        
        line.setOpaque(true);
        line.setBounds(390, 0, 2, 520);
        line.setForeground( Color.BLACK);
        line.setBackground( Color.BLACK);
        line2.setOpaque(true);
        line2.setBounds(0, 520, 800, 2);
        line2.setForeground( Color.BLACK);
        line2.setBackground( Color.BLACK);
        
        add( local);
        add( global);
        add( paneGlobal);
        add( paneLocal);
        add( line);
        add( line2);
    }
    
    public void update() {
    	scoresLocal = "";
    	scoresGlobal = "";
    	
    	for( int i = 0; i < score.getScores().size(); i++) {
    		scoresLocal += (i+1) + ")   " + score.getScores().get(i).getName() + "\t\t" + score.getScores().get(i).getScore() + "\n\n"; 
    	}
    	
    	try {
			url = new URL("http://javaproject.netii.net/highscore.php?action=list&access_code=nyanCatTrolFaceabc123??date=xx");
			in = url.openStream();
			reader = new BufferedReader( new InputStreamReader(in));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for( int i = 0; i < 15; i++) {
			try {
				temp = reader.readLine();
				if( temp == null)
					break;
				else {
					first = temp.split(" ");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			scoresGlobal += (i+1) + ")   " + first[0] + "\t\t" + first[1] +"\n\n";
		}
    	paneLocal.setText(scoresLocal);
    	paneGlobal.setText(scoresGlobal);
    }
}
