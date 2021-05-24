package game;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 */
public class Application {

    public static void main(String[] args) {
        Display display = new Display();
        Game game = new Game(display);
        game.showMainMenu();
    }
}
