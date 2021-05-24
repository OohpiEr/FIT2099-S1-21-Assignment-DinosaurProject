package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;
import game.actions.QuitGameAction;
import game.grounds.Lake;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
    private static int ecoPoints = 0;
    private Game game;
    private Menu menu = new Menu();

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     * @param game        Current game
     */
    public Player(String name, char displayChar, int hitPoints, Game game) {
        super(name, displayChar, hitPoints);
        this.game = game;
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        actions.add(new QuitGameAction(game, display));
        game.tick(ecoPoints);
        display.println(String.format("Eco Points: %d", getEcoPoints()));
        // Handle multi-turn Actions
        if (Lake.getRainfall() != 0) {
            display.println("It's raining!");
        }
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();
        return menu.showMenu(this, actions, display);
    }

    /**
     * Returns the player's number of eco points
     *
     * @return the player's number of eco points
     */
    public static int getEcoPoints() {
        return ecoPoints;
    }

    /**
     * Sets the player's number of eco points
     *
     * @param ecoPoints The new number of eco points the player should have
     */
    public static void setEcoPoints(int ecoPoints) {
        if (ecoPoints >= 0) {
            Player.ecoPoints = ecoPoints;
        }
    }

    /**
     * Adds the provided number of eco points to the player's total eco points
     *
     * @param ecoPoints The number of eco points to be added to the player's total eco points
     */
    public static void addEcoPoints(int ecoPoints) {
        if (ecoPoints >= 0) {
            Player.ecoPoints += ecoPoints;
        }
    }

    /**
     * Removes the provided number of eco points from the player's total eco points
     *
     * @param ecoPoints The number of eco points to be removed from the player's total eco points
     */
    public static void removeEcoPoints(int ecoPoints) {
        if (ecoPoints >= 0) {
            Player.ecoPoints -= ecoPoints;
        }
    }

    /**
     * Resets Eco Points to 0
     */
    public static void resetEcoPoints(){
        ecoPoints = 0;
    }
}
