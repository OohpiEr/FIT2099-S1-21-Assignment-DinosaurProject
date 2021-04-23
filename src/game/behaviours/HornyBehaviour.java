package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.dinosaurs.Allosaur;
import game.dinosaurs.Dinosaur;
import game.dinosaurs.Stegosaur;

import java.util.ArrayList;
import java.util.List;

public class HornyBehaviour implements Behaviour {

    FollowBehaviour followBehaviour;

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Action returnAction = null;
        Actor target = getNearestTarget(actor, map);

        if (isTargetInExit(target, actor, map)) {
            //TODO return BreedAction @see BreedAction
        } else {
            followBehaviour = new FollowBehaviour(target);
            returnAction = followBehaviour.getAction(actor, map);
        }

        return returnAction;
    }


    public boolean isTargetInExit(Actor target, Actor actor, GameMap map) {
        Location here = map.locationOf(actor);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getActor() == target) {
                return true;
            }
        }
        return false;
    }

    public Actor getNearestTarget(Actor actor, GameMap map) {

        List<Location> locationsWithTargets = new ArrayList<>();
        //FIXME: use actorlocations


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

        //compare distance of each location to actor
        int distance = Integer.MAX_VALUE;
        Location closestActorLocation = null;
        Location here = map.locationOf(actor);
        for (Location location : locationsWithTargets) {
            int newDistance = getDistance(location, here);
            if (distance > newDistance) {
                distance = newDistance;
                closestActorLocation = location;
            }
        }

        return map.getActorAt(closestActorLocation);

    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int getDistance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
