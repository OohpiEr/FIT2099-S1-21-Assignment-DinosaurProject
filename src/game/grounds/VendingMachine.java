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
     * Constructor. Instantiates the items HashMap
     */
    public VendingMachine() {
        super('$');
        items = new HashMap<Item, Integer>() {
        };
    }

    /**
     * Constructor. Instantiates the VendingMachine using the provided items HashMap
     * @param items
     */
    public VendingMachine(HashMap<Item,Integer> items){
        super('$');
        this.items = items;
    }

    /**
     * Returns True if the actor can enter the vending machine's location. False otherwise
     * @param actor the Actor to check
     * @return
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Returns the actions allowed by actors next to it. Currently only returns BuyActions to a Player instance based on their ecoPoints
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return
     */
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

    /**
     * Gets the HashMap of items available in the vending machine
     * @return A HashMap of items available in the vending machine
     */
    public HashMap<Item,Integer> getItems(){
        return items;
    }

    public void setItems(HashMap<Item,Integer> items){
        this.items = items;
    }
}
