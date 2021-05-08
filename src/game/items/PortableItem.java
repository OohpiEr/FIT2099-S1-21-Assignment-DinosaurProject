package game.items;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {

    /**
     * Constructor.
     * @param name          The name of the item
     * @param displayChar   The item's display character
     */
    public PortableItem(String name, char displayChar) {
        super(name, displayChar, true);
    }
}
