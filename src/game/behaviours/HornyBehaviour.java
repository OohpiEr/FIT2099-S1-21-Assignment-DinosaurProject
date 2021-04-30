package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.BreedAction;
import game.dinosaurs.Dinosaur;

import java.util.ArrayList;
import java.util.List;

import static game.Util.distance;

public class HornyBehaviour implements Behaviour {

    FollowBehaviour followBehaviour;

    /**
     * Get action for horny behaviour
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        Actor target = null;
        Action returnAction = null;
        List<Location> locationsWithTargets = getPossibleTargets(actor, map);

        if (locationsWithTargets != null) {
            target = getNearestTarget(actor, map, locationsWithTargets);

            if (target != null) {
                if (isTargetInExit(target, actor, map)) {
                    returnAction = new BreedAction();
                } else {
                    followBehaviour = new FollowBehaviour(target);
                    returnAction = followBehaviour.getAction(actor, map);
                }
            }
        }

        return returnAction;
    }

    /**
     * Checks if target is in exit
     *
     * @param target target actor
     * @param actor the actor itself
     * @param map the map the actor in in
     * @return true if the target is in exit
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
     * @param actor the actor itself
     * @param map the map the actor in in
     * @param locationsWithTargets list of locations with possible targets
     * @return target actor
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
     * @param actor the actor itself
     * @param map the map the actor is in
     * @return a list of possible targets
     */
    private List getPossibleTargets(Actor actor, GameMap map) {
        List<Location> locationsWithTargets = new ArrayList<>();

        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Actor possibleTarget = map.getActorAt(map.at(x, y));
                if ((possibleTarget != null) && (possibleTarget.getClass() == actor.getClass())) ;
                {
                    boolean isActorFemale = ((Dinosaur) actor).isFemale();
                    boolean isPossibleTargetFemale = ((Dinosaur) possibleTarget).isFemale();

                    if ((isActorFemale ^ isPossibleTargetFemale)) {
                        //isActorFemale XOR isPossibleTargetFemale
                        if ((isActorFemale && !((Dinosaur) actor).isPregnant()) || (isPossibleTargetFemale && !((Dinosaur) possibleTarget).isPregnant())) {
                            locationsWithTargets.add(map.locationOf(possibleTarget));
                        }
                    }
                }
            }
        }

        if (locationsWithTargets.size() != 0) {
            return locationsWithTargets;
        } else {
            return null;
        }
    }

}
