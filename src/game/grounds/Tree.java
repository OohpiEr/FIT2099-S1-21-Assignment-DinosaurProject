package game.grounds;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.interfaces.hasFood;
import game.items.Fruit;

import java.util.ArrayList;

/**
 * A class that represents a tree
 */
public class Tree extends Ground implements hasFood {
    private int age = 0;
    private ArrayList<Fruit> fruits;
    private final double CHANCE_TO_PRODUCE_FRUIT = 0.5;
    private final double CHANCE_TO_DROP_FRUIT = 0.05;
    private final String NAME = "tree";

    /**
     * Constructor. Instantiates the fruits ArrayList
     *
     * @see Fruit
     */
    public Tree() {
        super('+');
        fruits = new ArrayList<>();
    }

    /**
     * Getter for fruits
     *
     * @return An ArrayList of Fruits the Bush has
     */
    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    /**
     * Adds the specified number of fruits to the Bush's fruits
     *
     * @param numFruits The number of fruits to add to the Bush's fruits
     * @return True if the operation was a success. False otherwise
     */
    public boolean addFruits(int numFruits) {
        boolean success = false;
        if (numFruits > 0) {
            for (int i = 0; i < numFruits; i++) {
                fruits.add(new Fruit());
            }
            success = true;
        }
        return success;
    }

    /**
     * Removes the specified number of fruits to the Bush's fruits
     *
     * @param numFruits The number of fruits to remove from the Bush's fruits
     * @return True if the operation was a success. False otherwise
     */
    public boolean removeFruits(int numFruits) {
        boolean success = false;
        if (numFruits > 0 && numFruits <= fruits.size()) {
            for (int i = 0; i < numFruits; i++) {
                fruits.remove(fruits.size() - 1);
            }
            success = true;
        }
        return success;
    }

    /**
     * Extends the parent tick() method. Changes the displayChar depending on the Tree's age. Also has a chance to add a Fruit to fruits, and drop a Fruit from fruits to the location this Tree is on
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        age++;
        if (age == 10)
            displayChar = 't';
        if (age == 20)
            displayChar = 'T';

        if (Math.random() <= CHANCE_TO_PRODUCE_FRUIT) {
            this.addFruits(1);
        }
        if (Math.random() <= CHANCE_TO_DROP_FRUIT && !fruits.isEmpty()) {
            this.removeFruits(1);
            location.addItem(new Fruit());
        }
    }

    /**
     * Checks if the Bush has any Fruits in fruits
     *
     * @return True if fruits is empty. False otherwise
     */
    @Override
    public boolean isEmpty() {
        return fruits.isEmpty();
    }

    /**
     * Removes the specified quantity of the specified food from this Tree
     * @param food      The food that is eaten
     * @param quantity  The quantity of the food that is eaten
     */
    @Override
    public void eatFromThis(Item food, int quantity) {
        if(food.getClass()==Fruit.class){
            removeFruits(quantity);
        }
    }

    /**
     * Returns the name of the Tree
     * @return  The name of the Tree
     */
    public String getName(){
        return NAME;
    }
}
