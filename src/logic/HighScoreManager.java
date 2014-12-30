/*
 * HighScoreManager.java
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import data.Score;

/**
 *
 * @author Tansel
 */
public class HighScoreManager {
	/**
	 * Our serializable score object list
	 */
	private ArrayList<Score> scores;
	/**
	 * Our definite score file
	 */
	private static final String SCORE_FILE = "scores.pang";
	
	private ObjectOutputStream outputStream = null;
	private ObjectInputStream inputStream = null;

	private HttpURLConnection http;
	private OutputStreamWriter os;
	
	public HighScoreManager(){
		scores = new ArrayList<Score>();
	}
	
	public ArrayList<Score> getScores() {
		loadFile();
		return scores;
	}
	
	public void addScore( int score, String name) {
		/**
		 * This code is to send high score to online database
		 */
		String highscoreGlobal = "Sorry, this address has been removed from the public source code to prevent spammers and cheaters getting to system.";
		try {
			http = (HttpURLConnection) new URL(highscoreGlobal).openConnection();
			http.setRequestMethod("GET");
			
			http.setReadTimeout( 15*1000);
			http.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader( http.getInputStream()));
			if( reader.readLine().equalsIgnoreCase("1"))
				JOptionPane.showMessageDialog( null, "Your score has been sent to online database successfully!", "Connection Success!", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog( null, "Your score couldn't be sent to online database! Check your connection!", "Connection Failure!", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (MalformedURLException e) {
			System.out.println("*URL is not correct.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		/**
		 * This code is to save high score to local database
		 */
		loadFile();

		scores.add( new Score(score, name));
		Collections.sort(scores);
        for( int i = 15; i < scores.size(); i++)
            scores.remove(i);
        
        updateFile();
			
	}

    public void loadFile() {
        try {
            inputStream = new ObjectInputStream( new FileInputStream( SCORE_FILE));
            ArrayList<Score> readObject = (ArrayList<Score>) inputStream.readObject();
            if( readObject != null)
            	scores = readObject;
            inputStream.close();
        }
        catch ( FileNotFoundException e) {}
        catch ( IOException e) {}
        catch ( ClassNotFoundException e) {}

        finally {
            try {
                if( outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch ( IOException e) {}
        }
    }

    public void updateFile() {
        try {
            outputStream = new ObjectOutputStream( new FileOutputStream( SCORE_FILE));
            outputStream.writeObject( scores);
        }
        catch ( FileNotFoundException e) {}
        catch ( IOException e) {}

        finally {
            try {
                if( outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }
            catch ( IOException e) {}
        }
    }

    public int size()
    {
        loadFile();
    	return scores.size();
    }
}
