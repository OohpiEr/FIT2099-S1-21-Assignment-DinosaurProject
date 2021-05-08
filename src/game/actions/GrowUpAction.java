package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.BabyDino;

/**
 * Action for babies growing up into adults
 */
public class GrowUpAction extends Action {


    /**
     * Perform the growing up Action.
     *
     * @param actor The actor growing up.
     * @param map   The map the actor is on.
     * @return  A description of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String message = "";

        if (actor instanceof BabyDino) {
            message = actor + " at (" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") grew up";
            ((BabyDino) actor).growUp(map);
        }

        return message;
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return  A suitable menu description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
