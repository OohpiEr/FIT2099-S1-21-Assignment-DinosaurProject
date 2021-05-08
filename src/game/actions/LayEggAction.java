package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.AdultDino;

/**
 * Lay egg action for dinosaurs laying eggs
 */
public class LayEggAction extends Action {

    /**
     * executes the lay egg action
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ((AdultDino) actor).layEgg(map);
        return actor + " at (" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") lays an egg";
    }

    /**
     * Returns a descriptive string for the menu
     *
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
