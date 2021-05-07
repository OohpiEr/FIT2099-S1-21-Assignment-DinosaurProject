package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.interfaces.hasFood;
import game.Player;
import game.items.Fruit;

/**
 * A class that lets an actor pick a fruit from an object
 */
public class PickFruitAction extends Action {

    private hasFood object;
    private int quantity;
    private final int ECO_POINT_REWARD = 10;

    /**
     * Constructor.
     * @param object    The object that the fruit will be picked from
     * @param quantity  The quantity of fruit that will be picked from the object
     */
    public PickFruitAction(hasFood object, int quantity) {
        this.object = object;
        this.quantity = quantity;
    }

    /**
     * Causes the provided actor to pick a fruit from the provided object if possible
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  A description of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (object.getFoodInstance().getClass() == Fruit.class && !object.isEmpty()){
            object.removeFood(1);
            actor.addItemToInventory(new Fruit());
            if (actor instanceof Player){
                Player.addEcoPoints(ECO_POINT_REWARD);
            }
            return actor.toString() + " picks a fruit from " + object.toString();
        }
        return null;
    }

    /**
     * Returns a suitable menu description of the action
     *
     * @param actor The actor performing the action.
     * @return A suitable menu description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " picks a fruit from " + object.toString();
    }
}
