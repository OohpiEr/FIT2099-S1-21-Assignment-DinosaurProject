package game.items;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a fruit
 */
public class Fruit extends PortableItem {

    private int rotLevel=0;
    private final int MAX_ROTLEVEL_BEFORE_ROTTING = 15;
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

    /**
     * Extends the parent tick() method for Items on the ground (in a Location). Handles the Fruit's rotting
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rotLevel++;
        if(rotLevel>=MAX_ROTLEVEL_BEFORE_ROTTING){
            currentLocation.removeItem(this);
        }
    }
}
