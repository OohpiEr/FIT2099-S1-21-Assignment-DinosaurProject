package game.actions;

import edu.monash.fit2099.demo.mars.Floor;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Util;
import game.dinosaurs.Dinosaur;

/**
 * breed action
 */
public class BreedAction extends Action {

    private Dinosaur actor;
    private Dinosaur target;

    public BreedAction(Actor actor, Actor target) {
        setActor(actor);
        setTarget(target);
        setPregnant();
    }

    private void setPregnant(){
        if(actor.isFemale()){
            actor.setPregnant(true);
        } else {
            target.setPregnant(true);
        }
    }

    public void setActor(Actor actor) {
        this.actor = (Dinosaur) actor;
    }

    public void setTarget(Actor target) {
        this.target = (Dinosaur) target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (Util.getBooleanProbability(0.90)) {
            return actor + " breeds with " + actor;
        } else {
            return actor + " woohoo in the bushes with " + actor;
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }

    @Override
    public Action getNextAction() {
        return super.getNextAction();
    }


}
