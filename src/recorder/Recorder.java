package recorder;

import java.util.ArrayList;

/**
 * @author Maurice.
 *
 */
public class Recorder {
	public ArrayList<String> moves = new ArrayList<String>();
	private static int totalMoves = 0;
	public void pastMoves(String s) {
		moves.add(s);
		setTotalMoves(getTotalMoves() + 1);
	}
	public static int getTotalMoves() {
		return totalMoves;
	}
	public static void setTotalMoves(int totalMoves) {
		Recorder.totalMoves = totalMoves;
	}
}
