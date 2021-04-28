package game.grounds;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.interfaces.hasFood;
import game.items.Fruit;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents a bush
 */
public class Bush extends Ground implements hasFood{

    private ArrayList<Fruit> fruits;
    private final double CHANCE_TO_PRODUCE_FRUIT = 0.10;

    /**
     * Constructor. Instantiates the fruits ArrayList
     *
     */
    public Bush() {
        super('b');
        fruits = new ArrayList<>();
    }

    /**
     * Getter for fruits
     * @return An ArrayList of Fruits the Bush has
     */
    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    /**
     * Adds the specified number of fruits to the Bush's fruits
     * @param numFruits The number of fruits to add to the Bush's fruits
     * @return True if the operation was a success. False otherwise
     */
    public boolean addFruits(int numFruits){
        boolean success = false;
        if(numFruits>0){
            for(int i=0; i<numFruits;i++){
                fruits.add(new Fruit());
            }
            success = true;
        }
        return success;
    }

    /**
     * Removes the specified number of fruits to the Bush's fruits
     * @param numFruits The number of fruits to remove from the Bush's fruits
     * @return True if the operation was a success. False otherwise
     */
    public boolean removeFruits(int numFruits){
        boolean success = false;
        if(numFruits>0 && numFruits<=fruits.size()){
            for(int i=0; i<numFruits;i++){
                fruits.remove(fruits.size()-1);
            }
            success = true;
        }
        return success;
    }

    /**
     * Extends the parent tick() method. Has a chance to add a new Fruit to fruits
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if(Math.random()<=CHANCE_TO_PRODUCE_FRUIT){
            this.addFruits(1);
        }
    }

    /**
     * Checks if the Bush has any Fruits in fruits
     * @return True if fruits is empty. False otherwise
     */
    @Override
    public boolean isEmpty() {
        return fruits.isEmpty();
    }
}
