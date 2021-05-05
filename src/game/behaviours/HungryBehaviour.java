package game.behaviours;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.hasFood;
import game.actions.EatAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static game.Util.distance;


public class HungryBehaviour implements Behaviour {
    private Class<?>[] foodClasses;
    private HashMap<Class<?>, Class<?>[]> groundToFoodMap;

    public HungryBehaviour(Class<?>[] foodClasses, HashMap<Class<?>, Class<?>[]> groundClasses) {
        this.foodClasses = foodClasses;
        this.groundToFoodMap = groundClasses;
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {

        Location here = map.locationOf(actor);
        Location there = getFoodLocation(here, map, foodClasses, groundToFoodMap);

        if (there == null) {
            return null;
        }

        if (there == here) {
            if (there.getGround() instanceof hasFood && !((hasFood) there.getGround()).isEmpty()) {
                return new EatAction((Item) (((hasFood) there.getGround()).getFood().get(0)), (hasFood) there.getGround(), 1);
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

        return null;


    }

    public Location getFoodLocation(Location here, GameMap map, Class<?>[] foodClasses, HashMap<Class<?>, Class<?>[]> groundToFoodMap) {

        List<Location> locationsWithFood = new ArrayList<>();

        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                if (groundToFoodMap.containsKey(map.at(x, y).getGround().getClass())) {
                    List<Item> items = map.at(x, y).getItems();
                    if (items != null) {
                        for (Item item : items) {
                            //TODO test if below if statement works
                            if (Arrays.asList(foodClasses).contains(item.getClass())) {
                                locationsWithFood.add(map.at(x, y));
                            }
                        }
                    }
                    if (map.at(x, y).getGround() instanceof hasFood && ((hasFood) map.at(x, y).getGround()).isEmpty()) {
                        locationsWithFood.add(map.at(x, y));
                    }
                }
            }
        }

        //compare distance of each location to actor
        int distance = Integer.MAX_VALUE;
        Location closestFoodLocation = null;
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
