package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;
import game.grounds.Lake;

/**
 * An action that causes an actor (currently only Dinosaurs) to drink from a lake
 */
public class DrinkAction extends Action {

    private Lake source;

    /**
     * Constructor.
     * @param source    The source where the actor will drink from
     */
    public DrinkAction(Lake source){
        this.source = source;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        source.adjustSips(-1);
        if (actor instanceof Dinosaur){
            ((Dinosaur) actor).drink(1);
        }
        return actor + " takes a sip from a " + source.toString();
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " takes a sip from a " + source.toString();
    }
}
