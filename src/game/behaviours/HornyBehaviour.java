package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.Util;
import game.actions.BreedAction;
import game.dinosaurs.AdultDino;
import game.dinosaurs.Dinosaur;
import game.dinosaurs.Pterodactyl;
import game.grounds.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static game.Util.distance;

/**
 * A Behaviour for dinosaurs being horny and wanting to mate
 */
public class HornyBehaviour implements Behaviour {

    FollowBehaviour followBehaviour;

    /**
     * Gets the action for horny behaviour
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        AdultDino dino = (AdultDino) actor;

        if (dino.isFemale() && dino.isPregnant()) {
            return null;
        }

        AdultDino target = null;
        Action returnAction = null;
        List<Location> locationsWithTargets = getPossibleTargets(dino, map);

        if (locationsWithTargets != null) {
            target = (AdultDino) getNearestTarget(dino, map, locationsWithTargets);

            if (target != null) {
                if (actor instanceof Pterodactyl) {
                    if (dino.isFemale()) {
                        return goToNearestTree((Pterodactyl) dino, map);
                    } else {
                        if (isTargetInExit(target, dino, map)) {
                            if (map.locationOf(dino).getGround() instanceof Tree){
                                returnAction = new BreedAction(target);
                            } else {
                                return goToNearestTree((Pterodactyl) dino, map);
                            }
                        } else {
                            followBehaviour = new FollowBehaviour(target);
                            returnAction = followBehaviour.getAction(dino, map);
                        }
                    }
                } else {
                    if (isTargetInExit(target, dino, map)) {
                        returnAction = new BreedAction(target);
                    } else {
                        followBehaviour = new FollowBehaviour(target);
                        returnAction = followBehaviour.getAction(dino, map);
                    }
                }
            }
        }

        return returnAction;
    }

    /**
     * Checks if target is in exit
     *
     * @param target target actor
     * @param actor  the actor itself
     * @param map    the map the actor in in
     * @return true if the target is in exit, false otherwise
     */
    private boolean isTargetInExit(Actor target, Actor actor, GameMap map) {
        Location here = map.locationOf(actor);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getActor() == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets nearest target in the map containing the actor being horny
     *
     * @param actor                the actor itself
     * @param map                  the map the actor in in
     * @param locationsWithTargets list of locations with possible targets
     * @return The target actor
     */
    private Actor getNearestTarget(Actor actor, GameMap map, List<Location> locationsWithTargets) {
        Location closestActorLocation = null;

        //compare distance of each location to actor
        int distance = Integer.MAX_VALUE;
        Location here = map.locationOf(actor);

        for (Location location : locationsWithTargets) {
            int newDistance = distance(location, here);
            if (distance > newDistance) {
                distance = newDistance;
                closestActorLocation = location;
            }
        }

        return map.getActorAt(closestActorLocation);
    }

    /**
     * gets possible targets in the same map as the actor being horny
     *
     * @param dino the dinosaur itself
     * @param map  the map the actor is in
     * @return a list of possible targets
     */
    private List getPossibleTargets(AdultDino dino, GameMap map) {
        List<Location> locationsWithTargets = new ArrayList<>();

        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Actor possibleTarget = map.getActorAt(map.at(x, y));
                if ((possibleTarget != null) &&
                        (possibleTarget != dino) &&
                        (possibleTarget.getClass() == dino.getClass()) &&
                        (dino.isFemale() ^ ((AdultDino) possibleTarget).isFemale()) &&
                        ((dino.isFemale() && !(dino.isPregnant())) || (((AdultDino) possibleTarget).isFemale() && !((AdultDino) possibleTarget).isPregnant()))) {
                    locationsWithTargets.add(map.locationOf(possibleTarget));
                }
            }
        }

        if (locationsWithTargets.size() != 0) {
            return locationsWithTargets;
        } else {
            return null;
        }
    }

    /**
     * Returns an action to let the Pterodactyl go towards the nearest Tree with an adjacent Tree. The Pterodactyl does nothing if it is already on a Tree. If the Pterodactyl is a male, it also checks if there is a female on an adjacent Tree
     *
     * @param actor The Pterodactyl to be moved
     * @param map   The map the actor is on
     * @return An appropriate action
     */
    private Action goToNearestTree(Pterodactyl actor, GameMap map) {
        if (!(map.locationOf(actor).getGround() instanceof Tree)) {
            Location closestTree = null;
            Location here = map.locationOf(actor);
            for (int x : map.getXRange()) {
                for (int y : map.getYRange()) {
                    Location there = map.at(x, y);
                    boolean hasAdjacentTree = false;
                    boolean hasAdjacentFemale = false;
                    if (there.getGround() instanceof Tree && !there.containsAnActor()) {
                        for (Exit exit : there.getExits()){
                            if (exit.getDestination().getGround() instanceof Tree){
                                hasAdjacentTree = true;
                            }
                            if (exit.getDestination().containsAnActor() && exit.getDestination().getActor() instanceof Pterodactyl && ((Pterodactyl) exit.getDestination().getActor()).isFemale()){
                                hasAdjacentFemale = true;
                            }
                        }
                        if (actor.isFemale()){
                            if (hasAdjacentTree && (closestTree == null || Util.distance(closestTree, here) > Util.distance(there, here))) {
                                closestTree = there;
                            }
                        } else {
                            if (hasAdjacentFemale && hasAdjacentTree && (closestTree == null || Util.distance(closestTree, here) > Util.distance(there, here))) {
                                closestTree = there;
                            }
                        }

                    }
                }
            }
            for (Exit exit : here.getExits()) {
                Location there = exit.getDestination();
                if (there.canActorEnter(actor) && Util.distance(there, closestTree) < Util.distance(here, closestTree)) {
                    return new MoveActorAction(there, exit.getName());
                }
            }
        }
        return new DoNothingAction();
    }
}
