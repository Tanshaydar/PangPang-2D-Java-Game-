/*
 * SoundManager.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author Tansel
 */
public class SoundManager {
	
	/*
	 * 
	 */
	private String[] musics = new String[10];
	private Clip success;
	private Clip obstacleBreak;
	private Clip death;
	private Clip balloon;
	private AudioInputStream audioInputStream; 
	/**
	 * 
	 */
	private AudioPlayer player = AudioPlayer.player;
	private AudioStream stream;
	private AudioData data;
	private ContinuousAudioDataStream loop = null;
	
	public SoundManager(){
		musics[0] = "sfx//music//town.mid";
		musics[1] = "sfx//music//stillhere.mid";
		musics[2] = "sfx//music//rockon.mid";
		musics[3] = "sfx//music//allusionworld.mid";
		musics[4] = "sfx//music//flourish.mid";
		musics[5] = "sfx//music//innerstampede.mid";
		musics[6] = "sfx//music//spiritwithin.mid";
		musics[7] = "sfx//music//remix.mid";
		musics[8] = "sfx//music//joyfullife.mid";
		musics[9] = "sfx//music//remix2.mid";
//		initializeEffects();		
	}

	public void initializeEffects(){
		try {
			audioInputStream = AudioSystem.getAudioInputStream( new File("sfx//effects//drip.wav").getAbsoluteFile());
			balloon = AudioSystem.getClip();
			balloon.open(audioInputStream);
			
			audioInputStream = AudioSystem.getAudioInputStream( new File("sfx//effects//success.wav").getAbsoluteFile());
			success = AudioSystem.getClip();
			success.open(audioInputStream);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeBackgroundMusic( int level){
		if( level <= 10) {
			try {
				stream = new AudioStream( new FileInputStream(musics[level - 1]));
				data = stream.getData();
				loop = new ContinuousAudioDataStream(data);
			} catch (FileNotFoundException e) {
				System.out.println("*Music file is not found.");
			} catch (IOException e) {
				System.out.println("*Unexpected music error.");
			}
		} else {
			try {
				stream = new AudioStream( new FileInputStream("//sfx//music//onestop.mid"));
				data = stream.getData();
				loop = new ContinuousAudioDataStream(data);
			} catch (FileNotFoundException e) {
				System.out.println("*Music file is not found.");
			} catch (IOException e) {
				System.out.println("*Unexpected music error.");
			}
		}
	}
	
	public void playBackgroundMusic(){
		player.start( loop);
	}
	
	public void stopBackgroundMusic(){
		player.stop(loop);
	}
	
	public void playLevelEnd() {
		success.start();
	}
	
	public void playBalloon() {
		balloon.start();
	}
    
}
