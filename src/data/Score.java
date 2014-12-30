package data;

import java.io.Serializable;

/**
 * 
 * @author Tansel
 *
 */
public class Score implements Serializable, Comparable<Score>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int score;
	private String name;
	
	/**
	 * This is a {@link Serializable} class to save the score.
	 * @param score
	 * @param name
	 */
	public Score( int score, String name){
		this.score = score;
		this.name = name;
	}
	
	/**
	 * 
	 * @return Integer value of Score object, 
	 * which is the score that had been made by players.
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * 
	 * @return String value of Score object, 
	 * which is the name of the player who made the score.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method is a simple override for comparing objects to each other.
	 * We are using Comparable interface to sort an arraylist.
	 * @param score Another {@link Score} object to compare. 
	 * @return Comparison.
	 */
	public int compareTo( Score score) {
		return ((Integer)(score.getScore())).compareTo( getScore());
	}

}
