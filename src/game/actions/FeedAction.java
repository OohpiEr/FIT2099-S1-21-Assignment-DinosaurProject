package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.Player;
import game.dinosaurs.Dinosaur;
import game.dinosaurs.Stegosaur;

/**
 * A class that lets an actor feed another actor a quantity of a food
 */
public class FeedAction extends Action {

    private final int ECO_POINT_REWARD = 10;

    private Actor target;
    private Item food;
    private int quantity;

    /**
     * Constructor.
     * @param target    The actor being fed the food
     * @param food      The food being fed to the target
     * @param quantity  The quantity of food being fed to the target
     */
    public FeedAction(Actor target, Item food, int quantity){
        this.target = target;
        this.food = food;
        this.quantity = quantity;
    }

    /**
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  A description of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.removeItemFromInventory(food);
        if (target instanceof Dinosaur){
            ((Dinosaur) target).eat(food, quantity);
        }
        if (actor instanceof Player){
            Player.addEcoPoints(ECO_POINT_REWARD);
        }
        return actor.toString() + " feeds " + quantity + " " + food.toString() + "(s) to " + target.toString();
    }

    /**
     * Returns a suitable menu description of the action
     * @param actor The actor performing the action.
     * @return  A suitable menu description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " feeds " + quantity + " " + food.toString() + "(s) to " + target.toString();
    }
}
