package domain;

import java.awt.Color;
import java.util.ArrayList;

import persistency.State;

/**
 * This module is responsible for representing and maintaining the state of the game, 
 * such as what types of objects there are  and which actions are allowed to change 
 * the state of those objects.
 * 
 * @author aarisbaskaran
 *
 */
public class Game {

    /**
     * Level number - either 1 or 2
     */
    private int level;

    /**
     * The total time allowed for the level
     */

    private int maxTime;
    /**
     * The maze, a 2D array of Tiles.
     */
    private Tile[][] maze;

    /**
     * The position of chap on the maze.
     */
    private Position chapPos;

    /**
     * Total number of chips on the maze
     */
    private int chipsRemaining;

    /**
     * The treasure chest, list of chips.
     */
    private ArrayList<ChipTile> treasureChest;

    /**
     * The keys chap has collected.
     */
    private ArrayList<KeyTile> keys;

    /**
     * The last direction chap moved in - used for displaying the chap tile
     */
    private static char chapDirection;

    /**
     * Holds the current tile that chap has stepped over
     */
    private Tile currentTile;

    /**
     * Constructor for the Game.
     */
    public Game(State state) {

        //Initialise treasure chest and chap's items
        treasureChest = new ArrayList<ChipTile>();
        keys = new ArrayList<KeyTile>();
        
        loadLevel(state);

    }

    /**
     * Loads a level, gets data for the level from persistency module.
     *
     * @param state the level number
     */
    public void loadLevel(State state) {
        //somehow get level info from persistency module

        //Set the level number
        level = state.getLevelNum();

        //Set the time allowed for the level
        maxTime = state.getTime();

        //Set the total number of chips
        chipsRemaining = state.getChipsLeft();

        //Set the maze
        initMaze(state.getMaze());

        //Set the position and direction of chap
        chapPos = state.getPos();
        chapDirection = state.getDir();

        //Initialise currentTile
        currentTile = new FreeTile();

        //Reset treasure chest and items chap has picked up
        treasureChest.clear();
        keys.clear();
    }

    private void tempInitMaze() {

        maze = new Tile[10][10];

        for (int i = 0; i < 10; i++){
            maze[0][i] = new WallTile();
        }

        for (int i = 0; i < 10; i++){
            maze[9][i] = new WallTile();
        }

        for (int i = 1; i < 9; i++){
            maze[i][0] = new WallTile();
        }

        for (int i = 1; i < 9; i++){
            maze[i][9] = new WallTile();
        }

        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++){
                maze[j][i] = new FreeTile();
            }
        }

        maze[5][5] = new ChapTile();
    }

    //Initialise the maze array for the level
    private void initMaze(String[] input) {
    	
    	maze = new Tile[10][10];
    	
    	for(int row = 0; row < 10; ++row) {
    		for(int col = 0; col < 10; ++col) {
    			char c = input[col].charAt(row);
    			maze[row][col] = createTile(c);
    		}
    	}
    }

    //Returns a Tile to add into the maze
    private Tile createTile(char c) {
        if(c == 'P') {
            return new ChapTile();
        }
    	else if(c == '_') {
    	    return new FreeTile();
        }
    	else if(c == '#') {
    	    return new WallTile();
        }
    	else if(c == 'K'){
    	    return new KeyTile(Color.YELLOW);
        }
        else if(c == 'L'){
            return new LockedDoorTile(Color.YELLOW);
        }
        else if(c == 'T'){
            return new ChipTile();
        }
    	return null;
    }


    /**
     * Get the current position of chap on the maze.
     *
     * @return chapPos
     */
    public Position getChapPos() {
        return chapPos;
    }

    /**
     * Moves chap in the direction specified, if its a valid move.
     *
     * @param direction the direction to move - u, d, l, or r.
     */
    public void move(char direction){
        //check if move is valid
        if(isValid(direction)){
            moveChap(direction);
            chapDirection = direction;
        }
    }

    //Moves chap in the specified direction
    private void moveChap(char direction){
        int x = chapPos.getX();
        int y = chapPos.getY();

        if(direction == 'u'){
            updateMaze(x, y-1, x, y);
        }
        else if (direction == 'd'){
            updateMaze(x, y+1, x, y);
        }
        else if (direction == 'l'){
            updateMaze(x-1, y, x, y);
        }
        else if (direction == 'r'){
            updateMaze(x+1, y, x, y);
        }
    }

    //Updates the maze for a chap move
    private void updateMaze(int a, int b, int chapX, int chapY){
        chapPos = new Position(a, b);
        currentTile = maze[a][b];
        Tile chap = maze[chapX][chapY];
        setTile(a, b, chap);
        setTile(chapX, chapY, currentTile);
    }

    //Updates the specified tile in the maze array
    private void setTile(int row, int col, Tile tile){
        maze[row][col] = tile;
    }

    //Check if the tile in the given direction can we moved onto
    private boolean isValid(char direction){
        int x = chapPos.getX();
        int y = chapPos.getY();

        Tile nextTile;
        //check direction first
        if(direction == 'u'){
            nextTile = maze[x][y-1];
            return checkNextValid(nextTile);
        }
        else if (direction == 'd'){
            nextTile = maze[x][y+1];
            return checkNextValid(nextTile);
        }
        else if (direction == 'l'){
            nextTile = maze[x-1][y];
            return checkNextValid(nextTile);
        }
        else if (direction == 'r'){
            nextTile = maze[x+1][y];
            return checkNextValid(nextTile);
        }

        //code never reaches this point
        return false;
    }

    //checks if the next tile is a wall, or if its a locked door and chap has the key
    private boolean checkNextValid(Tile nextTile){
        //check if next tile is a wall
        if (nextTile.getClass() == WallTile.class){
            return false;
        }

        //if next tile is a locked door, check if chap has the key
        if (nextTile.getClass() == LockedDoorTile.class && !hasKey(nextTile)){
            return false;
        }

        return true;
    }

    //checks if chap has the key to a locked door
    private boolean hasKey(Tile door){
        for(KeyTile key: keys){
            if(key.getColor() == ((KeyTile) door).getColor()){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current level number.
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the keys that have been collected.
     *
     * @return keys
     */
    public ArrayList<KeyTile> getKeys(){
        return keys;
    }

    /**
     * Get the number of chips remaining on the board.
     *
     * @return chipsRemaining
     */
    public int getChipsRemaining(){
        return chipsRemaining;
    }

    /**
     * Get the 2D array containing the maze.
     *
     * @return maze
     */
    public Tile[][] getMaze() {
        return maze;
    }

    /**
     * Return the current direction of chap - for renderer to display the correct chap image
     *
     * @return direction
     */
    public static char getChapDirection() {
        return chapDirection;
    }
}
