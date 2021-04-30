package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.Player;

public class BuyAction extends Action {

    private Item item;
    private int price;

    /**
     * Constructor.
     * @param item      the Item to be bought
     * @param price     the price of the item in eco points
     */
    public BuyAction(Item item, int price){
        setItem(item);
        setPrice(price);
    }

    /**
     * Should only be called on a player. Adds the item to their inventory and deducts the price from their eco points. Price should never be too expensive (Checked in VendingMachine)
     * @see game.grounds.VendingMachine
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return  A description of the purchase
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = null;
        if(actor.getClass()== Player.class){
            ((Player) actor).removeEcoPoints(getPrice());
            actor.addItemToInventory(item);
            result = "Player bought " + item.toString() + " for " + getPrice();
        }
        return result;
    }

    /**
     * Returns a description of this transaction suitable to display in the menu.
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + item.toString();
    }

    /**
     * Gets the Item to be bought
     * @return      the Item to be bought
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the Item to be bought
     * @param item      the Item to be bought
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Gets the price of the item to be bought
     * @return      the price of the item to be bought
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the item to be bought
     * @param price     the price of the item to be bought. Cannot be negative
     */
    public void setPrice(int price) {
        if(price>=0){
            this.price = price;
        } else {
            this.price = 0;
        }
    }
}
