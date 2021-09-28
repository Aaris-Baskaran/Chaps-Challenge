package renderer;

import javax.sound.sampled.*;
import java.io.*;

/**
 * SoundEffect class
 * 
 * @author gemip
 *
 */

public class SoundEffect {
	
	private String fileSelect;
	private Clip clip;

	/**
	 * SoundEffect constructor
	 * 
	 * @param file The name of the WAV file
	 */
	public SoundEffect(String file) {
		this.fileSelect = file;
		setSound();
	}
	
	/**
	 * The setSound() method will use a sound effect's file name to set the sound effect of this SoundEffect 
	 * object and open it.
	 */
	public void setSound() {
		try {
			File file = new File(fileSelect);
			AudioInputStream soundEffect = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(soundEffect);
		}
		catch(Exception e) {
			System.out.println("File could not be found.");
		}
	}
	
	/**
	 * The playSound() method will play a sound effect from the starting point of the clip.
	 */
	public void playSound() {
		clip.setFramePosition(0);
		clip.start();
	}
}
