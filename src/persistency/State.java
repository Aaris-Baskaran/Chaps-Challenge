package persistency;

import domain.Position;

/**
 * This class represents a game state that will be loaded into the Game.
 *
 * @author Kelland Vuong 300533094
 */
public class State {
	
  /**
   * the current level in State.
   */
  private final int levelNum;
  /**
   * the time left in Game.
   */
  private final int maxTime;
  /**
   * the maze layout in String.
   */
  private final String[] maze;
  /**
   * the keys Chap has in Game.
   */
  private final String keys;
  /**
   * the chapPos field in Game.
   */
  private final Position chapPos;
  /**
   * the chapDirection field in Game.
   */
  private final char chapDirection;
  /**
   * the number of chips in treasureChest in Game.
   */
  private final int chipsInChest;
  /**
   * the chips remaining in Game.
   */
  private final int chipsRemaining;
 
  /**
   * The constructor which creates the state.
   *
   * @param levelNum current level
   * @param maxTime time left
   * @param maze the maze layout
   * @param x x coordinate of Chap
   * @param y y coordinate of Chap
   * @param dir direction of Chap
   * @param keys keys that Chap holds
   * @param chipsRemaining chips left on maze
   */
  public State(int levelNum, int maxTime, String[] maze, String keys, int x, int y, char dir, 
               int chipsInChest, int chipsRemaining) {
    this.levelNum = levelNum;
    this.maxTime = maxTime;
    this.maze = maze;
    this.keys = keys;
    this.chapPos = new Position(x, y);
    this.chapDirection = dir;
    this.chipsInChest = chipsInChest;
    this.chipsRemaining = chipsRemaining;
  }
 
  /**
   * Gets the level number.
   *
   * @return the level number
   */
  public int getLevelNum() {
    return levelNum;
  }
  
  /**
   * Gets the time.
   *
   * @return the time
   */
  public int getTime() {
    return maxTime;
  }
  
  /**
   * Gets the maze as a String array.
   *
   * @return the maze
   */
  public String[] getMaze() {
    return maze;
  }
  
  /**
   * Gets the keys as a String.
   *
   * @return the keys
   */
  public String getKeys() {
    return keys;
  }
  
  /**
   * Gets the position of Chap.
   *
   * @return the position of Chap
   */
  public Position getPos() {
    return chapPos;
  }
  
  /**
   * Gets the direction of Chap.
   *
   * @return the direction of Chap
   */ 
  public char getDir() {
    return chapDirection;
  }
  
  /**
   * Gets the number of chips in treasureChest.
   *
   * @return the number of chips
   */ 
  public int getChipsInChest() {
    return chipsInChest;
  }
  
  /**
   * Gets the chips remaining on the maze.
   *
   * @return the number of chips
   */ 
  public int getChipsLeft() {
    return chipsRemaining;
  }
}
