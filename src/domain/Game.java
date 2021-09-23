package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private Set<ChipTile> treasureChest;

    /**
     * The keys chap has collected.
     */
    private Set<KeyTile> keys;

    /**
     * The last direction chap moved in - used for displaying the chap tile
     */
    private static char chapDirection;

    /**
     * Holds the current tile that chap is on
     */
    private Tile currentTile;

    /**
     * Hold the tile that chap is going to step on.
     */
    private Tile theNextTile;

    /**
     * Constructor for the Game.
     */
    public Game(State state) {

        //Initialise treasure chest and chap's keys
        treasureChest = new HashSet<ChipTile>();
        keys = new HashSet<KeyTile>();
        
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
        else if(c == 'k'){
            return new KeyTile(Color.BLUE);
        }
        else if(c == 'l'){
            return new LockedDoorTile(Color.BLUE);
        }
        else if(c == 'T'){
            return new ChipTile();
        }
        else if(c == 'X'){
            return new ExitTile();
        }
        else if(c == 'I'){
            return new InfoTile();
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
        chapPos = new Position(a, b);           //update chap position to the new position
        theNextTile = maze[a][b];               //before moving, remember the tile on the position chap is moving onto
        Tile chap = maze[chapX][chapY];         //get the chap tile
        setTile(a, b, chap);                    //set the new position on the maze to the chap tile
        setTile(chapX, chapY, currentTile);     //set the old position, where chap was, to the tile that was there before chap moved onto it
        if(theNextTile.isA(KeyTile.class)){
            currentTile = new FreeTile();       //if chap has stepped onto a key, then the key should disappear
            keys.add((KeyTile)theNextTile);     //add the key to chaps collection
        }
        else if (theNextTile.isA(ChipTile.class)){
            currentTile = new FreeTile();       //if chap has stepped onto a chip, then the chip should disappear
            treasureChest.add((ChipTile)theNextTile);   //add the chip to treasure chest
            chipsRemaining--;                           //update remaining chips
            if (chipsRemaining == 0){                   //check if exit can be unlocked
                unlockExit();
            }
        }
        else {
            currentTile = theNextTile;          // set remember the tile that chap stepped onto as the current tile
        }

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
    //also adds keys and chips to chaps collections
    private boolean checkNextValid(Tile nextTile){

        if (nextTile.canMove) {
            return true;
        }
        else if (nextTile.isA(LockedDoorTile.class) && hasKey(nextTile)){
            return true;
        }

        return false;
    }

    private void unlockExit() {
        for (int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze.length; j++){
                if (maze[i][j].isA(ExitTile.class)){
                    ((ExitTile)maze[i][j]).setCanMove();
                }
            }
        }
    }

    //checks if chap has the key to a locked door
    private boolean hasKey(Tile door){
        for(KeyTile key: keys){
            if(key.getColor() == ((LockedDoorTile) door).getColor()){
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
    public Set<KeyTile> getKeys(){
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
