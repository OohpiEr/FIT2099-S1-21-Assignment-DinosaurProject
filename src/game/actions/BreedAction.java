package game.actions;

import edu.monash.fit2099.demo.mars.Floor;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Util;
import game.dinosaurs.AdultDino;
import game.dinosaurs.Dinosaur;

/**
 * breed action
 */
public class BreedAction extends Action {

    private AdultDino target;

    public BreedAction(AdultDino target) {
        this.target = target;
    }

    private void resetPregnantTick(AdultDino actor) {
        if (actor.isFemale() && actor.isPregnant()) {
            actor.resetPregnantTick();
        } else if (target.isFemale() && target.isPregnant()) {
            target.resetPregnantTick();
        }
    }

    private void setPregnant(AdultDino actor) {
        if (actor.isFemale()) {
            actor.setPregnant(true);
        } else {
            target.setPregnant(true);
        }
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof AdultDino){
            setPregnant((AdultDino) actor);
            resetPregnantTick((AdultDino) actor);

            if (Math.random() <= 0.9) {
                return actor + " at (" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") breeds with " + target + " at (" + map.locationOf(target).x() + ", " + map.locationOf(target).y() + ")";
            } else {
                return actor + " at (" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") woohoo in the bushes with " + target + " at (" + map.locationOf(target).x() + ", " + map.locationOf(target).y() + ")";
            }
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }


}
