package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.Game;

public class QuitGameAction extends Action {

    private Game game;
    private Display display;

    /**
     * Constructor for QuitGameAction
     *
     * @param game    current game
     * @param display the I/O object to which messages may be written
     */
    public QuitGameAction(Game game, Display display) {
        this.game = game;
        this.display = display;
    }

    /**
     * Quits the current game and asks if user wants to play again
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        display.println(Game.getTITLE());
        display.println("Play Again?");
        game.showGameOptions();
        return null;
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Quit game";
    }
}
