package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.Item;

import java.util.ArrayList;

/**
 * An interface for Ground classes that hold any kind of eatable Item in them
 */
public interface hasFood {
    /**
     * Returns whether the implementing object has any food in it
     * @return  False if the implementing object has food in it, True otherwise
     */
    boolean isEmpty();

    /**
     * Called when some food is being eaten from the implementing object
     * @param food      The food that is eaten
     * @param quantity  The quantity of the food that is eaten
     */
    void eatFromThis(Item food, int quantity);

    /**
     * Returns an ArrayList of the food in the implementing object
     * @return An ArrayList of the food in the implementing object
     */
    ArrayList<?> getFood();
}
