package game.grounds;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.actions.BuyAction;
import java.util.HashMap;

/**
 * A class that represents a vending machine
 */
public class VendingMachine extends Ground {

    // A HashMap representing items in the vending machine and their prices
    private HashMap<Item, Integer> items;

    /**
     * Constructor. Instantiates the items ArrayList
     */
    public VendingMachine() {
        super('$');
        items = new HashMap<Item, Integer>() {
        };
    }

    public VendingMachine(HashMap<Item,Integer> items){
        super('$');
        this.items = items;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        if(actor.getClass()== Player.class){
            items.forEach((k,v)->{
                if(((Player) actor).getEcoPoints()>=v){
                    actions.add(new BuyAction(k,v));
                }
            });
        }
        return actions;
    }

    public HashMap<Item,Integer> getItems(){
        return items;
    }

    public void setItems(HashMap<Item,Integer> items){
        this.items = items;
    }
}
