package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.Game;

public class QuitGameAction extends Action {

    private Game game;
    private Display display;

    public QuitGameAction(Game game, Display display) {
        this.game = game;
        this.display = display;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        display.println(Game.getTITLE());
        display.println("Play Again?");
        game.showGameOptions();
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Quit game";
    }
}
