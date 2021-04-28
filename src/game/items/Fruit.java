package game.items;

import edu.monash.fit2099.engine.Item;

/**
 * TODO empty Fruit class
 */
public class Fruit extends PortableItem {
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     */
    public Fruit(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * A default, parameterless constructor for Fruit
     */
    public Fruit(){
        super("Fruit", 'f');
    }
}
