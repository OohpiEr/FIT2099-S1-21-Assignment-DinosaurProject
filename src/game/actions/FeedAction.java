package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.Player;
import game.dinosaurs.Dinosaur;
import game.dinosaurs.Stegosaur;

public class FeedAction extends Action {

    private final int ECO_POINT_REWARD = 10;

    private Actor target;
    private Item food;
    private int quantity;

    public FeedAction(Actor target, Item food, int quantity){
        this.target = target;
        this.food = food;
        this.quantity = quantity;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        actor.removeItemFromInventory(food);
        if (target instanceof Dinosaur){
            ((Dinosaur) target).eat(food, quantity);
        }
        if (actor instanceof Player){
            Player.addEcoPoints(ECO_POINT_REWARD);
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
