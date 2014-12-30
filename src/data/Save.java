package data;

import java.io.Serializable;

public class Save implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int level;
	
	/**
	 * This is a {@link Serializable} object to save unlocked levels.
	 * @param level
	 */
	public Save( int level){
		this.level = level;
	}
	
	public int getLevel(){
		return level;
	}
}
