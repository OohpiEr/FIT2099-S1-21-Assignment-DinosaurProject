package game.actions;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.hasFood;
import game.dinosaurs.Dinosaur;

/**
 * A class that lets an actor eat a quantity of food from somewhere/something
 */
public class EatAction extends Action {

    private Item food;
    private Location location = null;
    private hasFood object = null;
    private int quantity;

    /**
     * Constructor. Used when food is being eaten from the ground
     *
     * @param food     The Item to be eaten
     * @param location The Location the food is to be eaten from
     * @param quantity The quantity of food to be eaten from the location
     */
    public EatAction(Item food, Location location, int quantity) {
        this.food = food;
        this.location = location;
        this.quantity = quantity;
    }

    /**
     * Constructor. Used when food is being eaten from an object (like a plant)
     *
     * @param food     The Item to be eaten
     * @param object   The object the food is to be eaten from
     * @param quantity The quantity of food to be eaten from the location
     */
    public EatAction(Item food, hasFood object, int quantity) {
        this.food = food;
        this.object = object;
        this.quantity = quantity;
    }

    /**
     * Causes the provided actor to eat a number of items from a location or an object if it's a dinosaur
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A description of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = null;

        if (actor instanceof Dinosaur) {
            if (object == null) {
                ((Dinosaur) actor).eat(food, quantity);
                for (int i = 0; i < quantity; i++) {
                    location.removeItem(food);
                }

                result = actor.toString() + " eats " + quantity + " " + food.toString() + "(s) from the ground";
            } else if (location == null) {
                object.eatFromThis((Dinosaur) actor, food, quantity);

                result = actor.toString() + " eats " + quantity + " " + food.toString() + "(s) from a " + object.toString();
            }
        }
        return result;
    }

    /**
     * Returns a suitable menu description of the action
     *
     * @param actor The actor performing the action.
     * @return A suitable menu description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        String output = null;

        if (object == null) {
            output = actor.toString() + " eats " + quantity + " " + food.toString() + "(s) from the ground";
        } else if (location == null) {
            output = actor.toString() + " eats " + quantity + " " + food.toString() + "(s) from a " + object.toString();
        }

        return output;
    }
}
