package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;
import game.dinosaurs.DinosaurEnumType;
import game.items.Corpse;
import game.items.PortableItem;

/**
 * A class that represents an actor dying
 */
public class DieAction extends Action {

    /**
     * Removes an actor from the map and places a corpse at its location
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  An appropriate menu description of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Dinosaur){
            map.locationOf(actor).addItem(new Corpse(((Dinosaur) actor).getDinoType()));
        } else {
            map.locationOf(actor).addItem(new PortableItem("dead " + actor.toString(), '%'));
        }
        map.removeActor(actor);
        return actor + " at (" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") died!";
    }

    /**
     * Returns an appropriate menu description of the action
     * @param actor The actor performing the action.
     * @return  An appropriate menu description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + "dies";
    }
}
