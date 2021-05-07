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

    private AdultDino actor;
    private AdultDino target;

    public BreedAction(Actor actor, Actor target) {
        setActor(actor);
        setTarget(target);
        setPregnant();
        resetPregnantTick();
    }

    private void resetPregnantTick() {
        if (actor.isFemale() && actor.isPregnant()) {
            actor.resetPregnantTick();
        } else if (target.isFemale() && target.isPregnant()) {
            target.resetPregnantTick();
        }
    }

    private void setPregnant() {
        if (actor.isFemale()) {
            actor.setPregnant(true);
        } else {
            target.setPregnant(true);
        }
    }

    public void setActor(Actor actor) {
        this.actor = (AdultDino) actor;
    }

    public void setTarget(Actor target) {
        this.target = (AdultDino) target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (Math.random() <= 0.9) {
            return actor + " breeds with " + actor;
        } else {
            return actor + " woohoo in the bushes with " + actor;
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }


}
