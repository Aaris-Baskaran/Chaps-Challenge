package domain;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import persistency.State;
import renderer.SoundEffect;

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
     * The time remaining in the level
     */

    public int time;
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
     * The last direction bug moved in - used for displaying the bug tile
     */
    private static char bugDirection;

    /**
     * Holds the current tile that chap is on
     */
    private Tile currentTile;

    /**
     * Hold the tile that chap is going to step on.
     */
    private Tile theNextTile;

    /**
     * Flag to indicate if the level is finished.
     */
    private boolean finished;

    /**
     * Flag to indicate if chap is on an info tile.
     */
    private boolean onInfo;

    /**
     * Positions of the portals
     */
    private Position portalPos0;
    private Position portalPos1;

    /**
     * Positions of the bugs
     */
    private Position bugPosH;
    private Position bugPosV;

    /**
     * Flag to indicate if chap is eaten by a bug.
     */
    private boolean isDead;

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
        time = state.getTime();

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
        
        //Initialise chest and keys
        initChest(state.getChipsInChest());
        initKeys(state.getKeys());

        //reset vars
        finished = false;
        isDead = false;

        //find portal positions
        setPortalPositions();

        //find bug positions
        setBugPositions();

    }

    private void setBugPositions() {
        if (level == 1){
            return;
        }

        int i = 0;
        for (Tile[] tiles : maze) {
            for (int j = 0; j < maze.length; j++) {
                if (tiles[j].isA(BugTile.class)) {
                    //System.out.println("yeet");
                    if (((BugTile) tiles[j]).getType() == 'h'){
                        //System.out.println("yeet");
                        bugPosH = new Position(i,j);
                    }
                    else {
                        bugPosV = new Position(i,j);
                    }
                }
            }
            i++;
        }
    }

    private void setPortalPositions() {
        int i = 0;
        for (Tile[] tiles : maze) {
            for (int j = 0; j < maze.length; j++) {
                if (tiles[j].isA(PortalTile.class)) {
                    if (((PortalTile) tiles[j]).portalNum == 0){
                        portalPos0 = new Position(i,j);
                    }
                    else if (((PortalTile) tiles[j]).portalNum == 1){
                        portalPos1 = new Position(i,j);
                    }
                }
            }
            i++;
        }
    }

    //Initialise the maze array for the level
    private void initMaze(String[] input) {

        int dimensions = input.length;

    	maze = new Tile[dimensions][dimensions];
    	
    	for(int row = 0; row < dimensions; ++row) {
    		for(int col = 0; col < dimensions; ++col) {
    			char c = input[col].charAt(row);
    			maze[row][col] = createTile(c);
    		}
    	}
    }
    
    private void initKeys(String input) {
    	if(input.length() == 0) return;
    	for(int i = 0; i < input.length(); ++i) {
    		char c = input.charAt(i);
    		keys.add((KeyTile) createTile(c));
    	}
    }
    
    private void initChest(int chips) {
    	if(chips == 0) return;
    	for(int i = 0; i < chips; ++i) {
    		treasureChest.add(new ChipTile());
    	}
    }

    //Returns a Tile to add into the maze
    private Tile createTile(char c) {
        if(c == 'C') {
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
        else if(c == 'P'){
            return new PortalTile(0);
        }
        else if(c == 'p'){
            return new PortalTile(1);
        }
        else if(c == 'B'){
            return new BugTile('h');
        }
        else if(c == 'b'){
            return new BugTile('v');
        }

    	return null;
    }

    /**
     * Moves the bugs.
     */
    public void moveBugs(){


        //move the horizontal bug
        int x = bugPosH.getX();
        int y = bugPosH.getY();
        BugTile bug = (BugTile) maze[x][y];

        Tile next;
        if (bug.getDirection()) {
            next = maze[x - 1][y];
        }
        else{
            next = maze[x + 1][y];
        }

        if (next.isA(WallTile.class)){
            bug.toggleDirection();
        }
        else if (next.isA(ChapTile.class)){
            bug.toggleDirection();
            playSound('B');
            isDead = true;
        }

        if (bug.getDirection()){    //move bug left
            maze[x-1][y] = bug;
            maze[x][y] = new FreeTile();
            bugPosH = new Position(x-1,y);
        }
        else {                      //move bug right
            maze[x+1][y] = bug;
            maze[x][y] = new FreeTile();
            bugPosH = new Position(x+1,y);
        }

        //move the vertical bug
        x = bugPosV.getX();
        y = bugPosV.getY();
        bug = (BugTile) maze[x][y];

        if (bug.getDirection()) {
            next = maze[x][y-1];
        }
        else{
            next = maze[x][y+1];
        }

        if (next.isA(WallTile.class)){
            bug.toggleDirection();
        }
        else if (next.isA(ChapTile.class)){
            bug.toggleDirection();
            playSound('B');
            isDead = true;
        }

        if (bug.getDirection()){    //move bug left
            maze[x][y-1] = bug;
            maze[x][y] = new FreeTile();
            bugPosV = new Position(x,y-1);
        }
        else {                      //move bug right
            maze[x][y+1] = bug;
            maze[x][y] = new FreeTile();
            bugPosV = new Position(x,y+1);
        }

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

    private void playSound(char tile) {
        SoundEffect s;

        if (tile == 'X'){
            s = new SoundEffect("ExitJingle.wav");
        }
        else if (tile == 'W'){
            s = new SoundEffect("HittingWall.wav");
        }
        else if (tile == 'I'){
            s = new SoundEffect("InfoField.wav");
        }
        else if (tile == 'K' || tile == 'k'){
            s = new SoundEffect("KeyCollected.wav");
        }
        else if (tile == 'P' || tile == 'p'){
            s = new SoundEffect("PortalSound.wav");
        }
        else if (tile == 'T'){
            s = new SoundEffect("TreasureCollected.wav");
        }
        else if (tile == 'B'){
            s = new SoundEffect(getRandomDeadSound());
        }
        else {//(tile == 'L' || tile == 'l') {
            s = new SoundEffect("UnlockDoor.wav");
        }

        s.playSound();

    }

    private String getRandomDeadSound() {
        int i = (int) (Math.random()*7.0);

        if (i == 0){
            return "confusion.wav";
        }
        else if (i == 1){
            return "ohmadays.wav";
        }
        else if (i == 2){
            return "oof1.wav";
        }
        else if (i == 3){
            return "oof2.wav";
        }
        else if (i == 4){
            return "oof3.wav";
        }
        else if (i == 5){
            return "weenak1.wav";
        }
        else if (i == 6){
            return "weenak2.wav";
        }
        return "oof3.wav";
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
        onInfo = false;                         //reset var
        chapPos = new Position(a, b);           //update chap position to the new position
        theNextTile = maze[a][b];               //before moving, remember the tile on the position chap is moving onto
        Tile chap = maze[chapX][chapY];         //get the chap tile
        setTile(a, b, chap);                    //set the new position on the maze to the chap tile
        setTile(chapX, chapY, currentTile);     //set the old position, where chap was, to the tile that was there before chap moved onto it
        if(theNextTile.isA(KeyTile.class)){
            playSound('K');
            currentTile = new FreeTile();       //if chap has stepped onto a key, then the key should disappear
            keys.add((KeyTile)theNextTile);     //add the key to chaps collection
        }
        else if (theNextTile.isA(ChipTile.class)){
            playSound('T');
            collectTreasure();
        }
        else if (theNextTile.isA(ExitTile.class)){
            playSound('X');
            finished = true;
        }
        else if (theNextTile.isA(InfoTile.class)){
            playSound('I');
            onInfo = true;
            currentTile = theNextTile;          // set remember the tile that chap stepped onto as the current tile
        }
        else if (theNextTile.isA(PortalTile.class)){
            playSound('P');
            enterPortal((PortalTile) theNextTile, a, b);

        }
        else {
            currentTile = theNextTile;          // set remember the tile that chap stepped onto as the current tile
        }

    }

    private void enterPortal(PortalTile portal, int chapX, int chapY) {
        currentTile = portal;                                                   // set remember the tile that chap stepped onto as the current tile

        if (portal.portalNum == 0){
            chapPos = new Position(portalPos1.getX(), portalPos1.getY()-1);      //update chap position to the new position
        }
        else if (portal.portalNum == 1){
            chapPos = new Position(portalPos0.getX(), portalPos0.getY()-1);      //update chap position to the new position
        }

        theNextTile = maze[chapPos.getX()][chapPos.getY()];                     //before moving, remember the tile on the position chap is moving onto
        Tile chap = maze[chapX][chapY];                                         //get the chap tile
        setTile(chapPos.getX(), chapPos.getY(), chap);                          //set the new position on the maze to the chap tile
        setTile(chapX, chapY, currentTile);                                     //set the old position, where chap was, to the tile that was there before chap moved onto it
        currentTile = theNextTile;
    }

    private void collectTreasure(){
        //PRECONDITION CHECK
        if (!theNextTile.isA(ChipTile.class)){
            throw new IllegalStateException("there is no treasure here: " + theNextTile);
        }
        int uncollectedTreasureCount = chipsRemaining;

        //PICK UP THE TREASURE
        currentTile = new FreeTile();       //if chap has stepped onto a chip, then the chip should disappear
        treasureChest.add((ChipTile)theNextTile);   //add the chip to treasure chest
        chipsRemaining--;                           //update remaining chips
        if (chipsRemaining == 0){                   //check if exit can be unlocked
            unlockExit();
        }

        //POST-CONDITION CHECK
        int uncollectedTreasureCount2 = chipsRemaining;
        assert uncollectedTreasureCount2 == uncollectedTreasureCount-1;

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
            playSound('L');
            return true;
        }
        else if (nextTile.isA(BugTile.class)){
            playSound('B');
            isDead = true;
            return false;
        }

        //PRECONDITION CHECKS
        throwIllegalArgumentException(nextTile);

        return false;
    }

    private void throwIllegalArgumentException(Tile nextTile) throws IllegalArgumentException{
        playSound('W');
        if (nextTile.isA(WallTile.class)){
            throw new IllegalArgumentException("chap cannot be moved into a wall");
        }
        else if (nextTile.isA(LockedDoorTile.class)){
            throw new IllegalArgumentException("chap cannot enter a locked door without the correct key");
        }
        else if (nextTile.isA(ExitTile.class)){
            throw new IllegalArgumentException("chap cannot exit without collecting all treasures");
        }

    }

    private void unlockExit() {
        for (Tile[] tiles : maze) {
            for (int j = 0; j < maze.length; j++) {
                if (tiles[j].isA(ExitTile.class)) {
                    ((ExitTile) tiles[j]).setCanMove();
                    break;
                }
            }
        }
        playSound('L');
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
     * Get the current time.
     *
     * @return level
     */
    public int getTime() {
        return time;
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

    /**
     * Return the current direction of bug - for renderer to display the correct bug image
     *
     * @return direction
     */
    public static char getBugDirection() {
        return bugDirection;
    }

    /**
     * Used to check if the player has finished the level or not.
     *
     * @return finished
     */
    public boolean isFinished(){
        return finished;
    }

    /**
     * Used to check if the player has been eaten by a bug.
     *
     * @return finished
     */
    public boolean isDead(){
        return isDead;
    }

    /**
     * Used to check if the player is on an infoTile.
     *
     * @return onInfo
     */
    public boolean isOnInfo(){
        return onInfo;
    }

    /**
     * Return the message that the is displayed when player steps on the info tile.
     *
     * @return info
     */
    public String getInfo() {
        if (level == 1){
            return "Take the RIGHT path for the Yellow Key\n" +
                    "Take the wrong path to get the chips";
        }
        return "Here you stand, stuck on what to do\n" +
                "before you enter the void, find\n" +
                "that which is blue\n" +
                "(Notice how that riddle rhymes :D";
    }

    /**
     * Get the number of treasures collected.
     *
     * @return size
     */
    public int getTreasureChestSize(){
        return treasureChest.size();
    }

    public String getInventory(){
        String ret = "";

        for (KeyTile k: keys){
            if (k.getColor() == Color.BLUE){
                ret += "k";
            }
            else{
                ret += "K";
            }
        }

        return ret;
    }

    /**
     * Returns the current maze as a formatted String.
     *
     * @return ret
     */
    public String toString(){
        String ret = "";

        int i = 0;
        for (Tile[] tiles : maze) {
            for (int j = 0; j < maze.length; j++) {
                ret += maze[j][i].toString();
            }
            ret += "\n";
            i++;
        }
        return ret;
    }

}
