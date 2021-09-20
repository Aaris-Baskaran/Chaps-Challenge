package persistency;

import domain.Position;

public class State {
	
	/**
    * the current level in State
    */
	private final int levelNum;
	/**
    * the time left in Game
    */
	private final int maxTime;
	/**
    * the maze layout in String
    */
	private final String[] maze;
	/**
    * the chapPos field in Game
    */
	private final Position chapPos;
	/**
    * the chapDirection field in Game
    */
	private final char chapDirection;
	/**
    * the chips remaining in Game
    */
	private final int chipsRemaining;
	
	/**
     * @param levelNum current level
     * @param maxTime time left
     * @param maze the maze layout
     * @param pos position of Chap
     * @param dir direction of Chap
     * @param keys keys that Chap holds
     * @param chipsRemaining chips left on maze
     */
	public State(int levelNum, int maxTime, String[] maze, int x, int y, char dir, int chipsRemaining){
	   this.levelNum = levelNum;
	   this.maxTime = maxTime;
	   this.maze = maze;
	   this.chapPos = new Position(x, y);
	   this.chapDirection = dir;
	   this.chipsRemaining = chipsRemaining;
	}
	
	/**
     * Gets the level number
     * @return the level number
     */
    public int getLevelNum() {
        return levelNum;
    }
    
    public int getTime() {
    	return maxTime;
    }
    
    public String[] getMaze() {
    	return maze;
    }
    
    public Position getPos() {
    	return chapPos;
    }
    
    public char getDir() {
    	return chapDirection;
    }
    
    public int getChipsLeft() {
    	return chipsRemaining;
    }
}
