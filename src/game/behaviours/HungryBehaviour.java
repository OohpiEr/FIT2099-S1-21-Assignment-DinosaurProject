package game.behaviours;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.hasFood;
import game.actions.EatAction;
import game.dinosaurs.BabyBrachiosaur;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Dinosaur;
import game.grounds.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static game.Util.distance;

/**
 * A class that determines the behavior of a hungry actor
 */
public class HungryBehaviour implements Behaviour {
    private Class<?>[] foodClasses;
    private HashMap<Class<?>, Class<?>[]> groundToFoodMap;

    /**
     * Constructor.
     */
    public HungryBehaviour() {
    }

    /**
     * Gets the appropriate action for the hungry actor. Will automatically return null if the actor is not a Dinosaur
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return  An appropriate action that the actor can take
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        if (actor instanceof Dinosaur) {
            foodClasses = ((Dinosaur) actor).getFood();
            groundToFoodMap = ((Dinosaur) actor).getFromTheseEatsThese();
            Location here = map.locationOf(actor);
            Location there = getFoodLocation(here, map, foodClasses, groundToFoodMap);

            if (there == null) {
                return null;
            }

            if (there == here) {
                if (there.getGround() instanceof hasFood && !((hasFood) there.getGround()).isEmpty()) {
                    hasFood ground = (hasFood) there.getGround();
                    if ((actor.getClass() == Brachiosaur.class || actor.getClass() == BabyBrachiosaur.class) && ground.getClass() == Tree.class) {
                        return new EatAction((ground.getFoodInstance()), ground, ground.getFood().size());
                    }
                    return new EatAction((ground.getFoodInstance()), ground, 1);
                } else {
                    for (Item item : there.getItems()) {
                        if (Arrays.asList(foodClasses).contains(item.getClass())) {
                            return new EatAction(item, there, 1);
                        }
                    }
                }
            }

            int currentDistance = distance(here, there);
            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(destination, there);
                    if (newDistance < currentDistance) {
                        return new MoveActorAction(destination, exit.getName());
                    }
                }
            }
        }
        return null;


    }

    /**
     * Gets the location of the nearest/most optimal source of food
     * @param here              The actor's Location
     * @param map               The GameMap the actor is on
     * @param foodClasses       The classes of food the actor eats
     * @param groundToFoodMap   The HashMap of Grounds the actor eats from, along the the foods they eat from those Grounds
     * @return                  The location of the nearest/most optimal source of food
     */
    public Location getFoodLocation(Location here, GameMap map, Class<?>[] foodClasses, HashMap<Class<?>, Class<?>[]> groundToFoodMap) {

        List<Location> locationsWithFood = new ArrayList<>();
        Location closestFoodLocation = null;
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                if (groundToFoodMap.containsKey(map.at(x, y).getGround().getClass())) {
                    List<Item> items = map.at(x, y).getItems();
                    if (items != null && items.size() > 0) {
                        for (Item item : items) {
                            if (Arrays.asList(foodClasses).contains(item.getClass())) {
                                locationsWithFood.add(map.at(x, y));
                            }
                        }
                    }
                    if (map.at(x, y).getGround() instanceof hasFood && !((hasFood) map.at(x, y).getGround()).isEmpty()) {
                        locationsWithFood.add(map.at(x, y));
                    }
                }
            }
        }

        //compare distance of each location to actor
        int distance = Integer.MAX_VALUE;
        for (Location location : locationsWithFood) {
            int newDistance = distance(location, here);
            if (distance > newDistance) {
                distance = newDistance;
                closestFoodLocation = location;
            }
        }
        return closestFoodLocation;
    }

}
