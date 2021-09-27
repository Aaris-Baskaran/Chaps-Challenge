package recorder;

import java.util.ArrayList;

/**
 * @author Maurice.
 *
 */
public class Recorder {
	/*
	 * List that stores all the previous moves of the player.
	 */
	public ArrayList<String> moves = new ArrayList<String>();
	
	/*
	 * Keeps track of the total moves of the player.
	 */
	private static int totalMoves = 0;
	
	/*
	 * Method that gets called by the GUI to add a step to the list.
	 */
	public void pastMoves(String s) {
		moves.add(s);
		setTotalMoves(getTotalMoves() + 1);
	}
	
	/*
	 * Record the previous game.
	 */
	public void recordGame() {
		// Change Arraylist of moves into XML file
		System.out.print(totalMoves);
		moves.clear();
	}
	
	/*
	 * Load the previous game.
	 */
	public void loadRecordedGame() {
		// Pass the recorded game to GUI.
		
	}
	
	/*
	 * Replay the previous game.
	 */
	public void replayRecordedGame() {
		// Replay recorded game.
		// Add panel with 3 buttons: step by step, auto-replay, set replay speed.
		
	}
	
	public static int getTotalMoves() {
		return totalMoves;
	}
	public static void setTotalMoves(int totalMoves) {
		Recorder.totalMoves = totalMoves;
	}
}
