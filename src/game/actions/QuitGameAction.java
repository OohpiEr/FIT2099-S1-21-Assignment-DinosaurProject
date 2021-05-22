package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.MainMenu;

public class QuitGameAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        MainMenu.quitGame();
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Quit game";
    }
}
