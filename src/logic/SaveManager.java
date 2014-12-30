/*
 * SaveManager.java
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.Save;

/**
 * 
 * @author Tansel
 */
public class SaveManager {

	/**
	 * Our Serializable Save ojbect.
	 */
	private Save save;
	/**
	 * Our definite save file.
	 */
	private static final String SAVES_FILE = "saves.pang";

	private ObjectOutputStream outputStream = null;
	private ObjectInputStream inputStream = null;

	/**
	 * Save Manager regulates the unlocked levels.
	 * At first, only first level is available to play. When player unlocks new levels
	 * they become available to play when a new game is started.
	 */
	public SaveManager() {
	}

	public Save getSave() {
		loadFile();
		return save;
	}

	/**
	 * Adds a new unlocked level.
	 * @param level
	 */
	public void addSave(int level) {
		loadFile();
		if ( save == null || save.getLevel() < level)
			save = new Save(level);
		updateFile();
	}

	/**
	 * Loads Save file for processing.
	 */
    public void loadFile() {
        try {
            inputStream = new ObjectInputStream( new FileInputStream(SAVES_FILE));
            save = (Save) inputStream.readObject();
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
            }
            catch ( IOException e) {}
        }
    }

    /**
     * Updates the save file.
     */
	public void updateFile(){
		try {
			outputStream = new ObjectOutputStream( new FileOutputStream( SAVES_FILE));
			outputStream.writeObject(save);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
