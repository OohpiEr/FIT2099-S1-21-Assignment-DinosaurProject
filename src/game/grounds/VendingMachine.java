package game.grounds;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.actions.BuyAction;
import game.dinosaurs.DinosaurEnumType;
import game.dinosaurs.Stegosaur;
import game.items.*;

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
        items = new HashMap<Item, Integer>() {{
            put(new Fruit(), 30);
            put(new VegetarianMealKit(), 100);
            put(new CarnivoreMealKit(), 500);
            put(new Egg(DinosaurEnumType.STEGOSAUR), 200);
            put(new Egg(DinosaurEnumType.BRANCHIOSAUR), 500);
            put(new Egg(DinosaurEnumType.ALLOSAUR), 1000);
            put(new LaserGun(), 500);
        }};
    }

    /**
     * Constructor. Instantiates the VendingMachine using the provided items HashMap
     *
     * @param items
     */
    public VendingMachine(HashMap<Item, Integer> items) {
        super('$');
        this.items = items;
    }

    /**
     * Returns True if the actor can enter the vending machine's location. False otherwise
     *
     * @param actor the Actor to check
     * @return  True if the actor can enter the vending machine's location. False otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Returns the actions allowed by actors next to it. Currently only returns BuyActions to a Player instance based on their ecoPoints
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return  An Actions instance with the actions allowed
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        if (actor instanceof Player) {
            items.forEach((k, v) -> {
                if (Player.getEcoPoints() >= v) {
                    actions.add(new BuyAction(k, v));
                }
            });
        }
        return actions;
    }

    /**
     * Gets the HashMap of items available in the vending machine
     *
     * @return A HashMap of items available in the vending machine
     */
    public HashMap<Item, Integer> getItems() {
        return items;
    }

    /**
     * Sets the vending machine's HashMap of items to the provided one
     * @param items
     */
    public void setItems(HashMap<Item, Integer> items) {
        this.items = items;
    }

    /**
     * Adds all the items in the provided HashMap into the vending machine's list of items
     * @param items A HashMap of the items to add to the vending machine
     */
    public void addItems(HashMap<Item, Integer> items) {
        this.items.putAll(items);
    }

    /**
     * Adds an item with the specified cost to the vending machine. Will override the price of the item instead if it already exists
     * @param item  The item to be added
     * @param cost  The price of the item, cannot be negative
     */
    public boolean addItem(Item item, int cost) {
        if(cost>=0){
            this.items.put(item, cost);
            return true;
        }
        return false;
    }
}
