package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Game;

public class QuitGameAction extends Action {

    private Game game;

    public QuitGameAction(Game game) {
        this.game = game;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        game.showGameOptions();
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Quit game";
    }
}
