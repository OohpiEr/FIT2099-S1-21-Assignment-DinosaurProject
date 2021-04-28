package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.Player;

public class BuyAction extends Action {

    private Item item;
    private int price;

    public BuyAction(Item item, int price){
        setItem(item);
        setPrice(price);
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = null;
        if(actor.getClass()== Player.class){
            ((Player) actor).removeEcoPoints(getPrice());
            actor.addItemToInventory(item);
        }
        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + item.toString();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if(price>=0){
            this.price = price;
        } else {
            this.price = 0;
        }
    }
}
