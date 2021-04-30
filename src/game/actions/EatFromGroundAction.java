package game.actions;

import edu.monash.fit2099.engine.*;

public class EatFromGroundAction extends Action {

    Item food;
    Location location;
    int quantity;

    /**
     * Constructor.
     * @param food      The Item to be eaten
     * @param location  The Location the food is to be eaten from
     * @param quantity  The quantity of food to be eaten from the location
     */
    public EatFromGroundAction(Item food, Location location, int quantity){
        this.food = food;
        this.location = location;
        this.quantity = quantity;
    }

    /**
     * Causes the provided actor to eat a number of items from a location
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
