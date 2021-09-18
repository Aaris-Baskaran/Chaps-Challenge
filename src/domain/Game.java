package domain;

import java.util.ArrayList;

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
     * Constructor for the Game.
     */
    public Game() {

        //Initialise treasure chest and chap's items
        treasureChest = new ArrayList<ChipTile>();
        keys = new ArrayList<KeyTile>();

        //Load level 1
        loadLevel(1);

    }

    /**
     * Loads a level, gets data for the level from persistency module.
     *
     * @param levelNum the level number
     */
    public void loadLevel(int levelNum) {
        //somehow get level info from persistency module

        //Set the level number
        level = 1;

        //Set the time allowed for the level

        //Set the total number of chips

        //Set the maze

        //Set the position of chap

        //Reset treasure chest and items chap has picked up
        treasureChest.clear();
        keys.clear();
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
        }

    }

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

    private void updateMaze(int a, int b, int chapX, int chapY){
        chapPos = new Position(a, b);
        Tile chap = maze[chapY][chapX];
        setTile(b, a, chap);
        setTile(chapY, chapX, new FreeTile());
    }

    private void setTile(int row, int col, Tile tile){
        maze[row][col] = tile;
    }

    private boolean isValid(char direction){
        //check if next tile is a wall


        //if next tile is a locked door, check if chap has the key

        return true;
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
     * @return
     */
    public ArrayList<KeyTile> getKeys(){
        return keys;
    }

    /**
     * Get the number of chips remaining on the board.
     *
     * @return
     */
    public int getChipsRemaining(){
        return chipsRemaining;
    }

    /**
     * Get the 2D array containing the maze.
     *
     * @return
     */
    public Tile[][] getMaze() {
        return maze;
    }
}
