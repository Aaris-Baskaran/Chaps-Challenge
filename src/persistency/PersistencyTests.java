package persistency;

import domain.Game;
import org.junit.jupiter.api.Test;

/**
 * Test cases in the Persistency Module.
 *
 * @author Kelland Vuong 300533094
 */
public class PersistencyTests {
  
  @Test
  public void test1() {
    // initialises the state manager and game via loadGame()
    StateManager manager = new StateManager(); 
    Game game = new Game(manager.loadGame());
    
    // saves the current game state into a XML file
    manager.saveState(game);
    
    // reloads the saved state into the game
    game.loadLevel(manager.loadGame());
  }

  @Test
  public void test2() {
    // initialises the state manager and game via loadSelect()
    StateManager manager = new StateManager();
    Game game = new Game(manager.loadSelect());
    
    // saves the current level
    manager.saveLevel(game);
    
    // deletes saved state if it exists
    manager.delete();
  }

}
