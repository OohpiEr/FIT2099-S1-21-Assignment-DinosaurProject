package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.dinosaurs.BabyPterodactyl;
import game.dinosaurs.BabyStegosaur;
import game.dinosaurs.Pterodactyl;
import game.dinosaurs.Stegosaur;

import java.util.*;

/**
 * Attack Behaviour
 */
public class AttackBehaviour implements Behaviour {

    /**
     * hashmap of actors that can't be attacked currently and the number of turns left for it to be attackable again
     */
    private HashMap<Actor, Integer> unattackableActors;
    /**
     * max number of turns to wait until stegosaur can be attacked again
     */
    private final int STEGOSAUR_UNATTACKABLE_TICK = 20;

    /**
     * constructor
     */
    public AttackBehaviour() {
        unattackableActors = new HashMap<>();
    }

    /**
     * gets action for current behaviour
     *
     * @param attacker the Actor attacking
     * @param map      the GameMap containing the Actor
     * @return The action to be taken by the actor
     */
    @Override
    public Action getAction(Actor attacker, GameMap map) {
        Action attackAction = null;
        ArrayList<Class<?>> targetClasses = new ArrayList<>();
        targetClasses.add(Stegosaur.class);
        targetClasses.add(BabyStegosaur.class);
        targetClasses.add(Pterodactyl.class);
        targetClasses.add(BabyPterodactyl.class);

        Actor target = getTargetsInExit(attacker, map, targetClasses);
        if (target != null) {
            attackAction = new AttackAction(attacker, target);
            if (target instanceof Stegosaur || target instanceof BabyStegosaur){
                addUnattackableActor(target);
            }
        }

        return attackAction;
    }

    /**
     * returns the first target of the provided class in the actor's exits
     *
     * @param actor         actor that is going to attack
     * @param map           map the actor is in
     * @param targetClasses the class of targets
     * @return arraylist of targets
     */
    private Actor getTargetsInExit(Actor actor, GameMap map, ArrayList<Class<?>> targetClasses) {
        Location here = map.locationOf(actor);
        Actor target = null;

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() &&
                    isATarget(targetClasses, destination) &&
                    isAttackable(destination.getActor())) {
                target = destination.getActor();
            }
        }

        return target;
    }

    /**
     * adds attacked actor to unattackableActors Hashmap with the number of turns it will not be attackable
     *
     * @param actor The actor to be made unnattackable
     */
    private void addUnattackableActor(Actor actor) {
        if (actor instanceof Stegosaur || actor instanceof BabyStegosaur)
            unattackableActors.put(actor, STEGOSAUR_UNATTACKABLE_TICK);
    }

    /**
     * Checks if the target actor is attackable
     * returns false if target is found in unattackableActors
     *
     * @param target the target actor
     * @return false if target is found in unattackableActors
     */
    private boolean isAttackable(Actor target) {
        boolean attackable = true;

        for (Actor unattackableActor : unattackableActors.keySet()) {
            if (target == unattackableActor) {
                attackable = false;
                break;
            }
        }

        return attackable;
    }

    /**
     * checks if the actor at a location is a viable target given an ArrayList of attackable classes.
     *
     * @param targetClasses ArrayList of classes that the attacker can attack
     * @return True if actor at destination can be attacked, False otherwise
     */
    private boolean isATarget(ArrayList<Class<?>> targetClasses, Location destination) {
        for (Class<?> clazz : targetClasses) {
            if (destination.getActor().getClass() == clazz)
                return true;
        }
        return false;
    }

    /**
     * Ticks down each unattackable actor's number of turns of immunity. Removes the actor from the HashMap if the number of rounds is 0 or less
     */
    public void tick() {
        ArrayList<Actor> removeThese = new ArrayList<>();
        unattackableActors.forEach((k, v) -> {
            v--;
            if (v <= 0) {
                removeThese.add(k);
            }
        });
        for (Actor actor : removeThese) {
            unattackableActors.remove(actor);
        }
    }

}
