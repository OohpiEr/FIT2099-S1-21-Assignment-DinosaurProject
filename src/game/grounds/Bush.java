package game.grounds;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.interfaces.hasFood;
import game.dinosaurs.Brachiosaur;
import game.items.Fruit;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents a bush
 */
public class Bush extends Ground implements hasFood {

    private ArrayList<Fruit> fruits;
    private final double CHANCE_TO_PRODUCE_FRUIT = 0.1;
    private final double CHANCE_TO_BE_KILLED_BY_BRACHIOSAUR = 0.5;
    private final String NAME = "bush";

    /**
     * Constructor. Instantiates the fruits ArrayList
     *
     * @see Fruit
     */
    public Bush() {
        super('x');
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
     * Extends the parent tick() method. Has a chance to add a new Fruit to fruits. Has a chance to be killed if a Brachiosaur is on the same location
     *
     * @param location The location of the Ground
     * @see Dirt
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (Math.random() <= CHANCE_TO_PRODUCE_FRUIT) {
            this.addFruits(1);
        }
        if (location.containsAnActor() && location.getActor().getClass() == Brachiosaur.class) {
            if (Math.random() <= CHANCE_TO_BE_KILLED_BY_BRACHIOSAUR) {
                location.setGround(new Dirt());
            }
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
     * Removes the specified quantity of the specified food from this Bush
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
     * Returns an ArrayList of the Bush's food
     * @return An ArrayList of the Bush's food
     */
    @Override
    public ArrayList<?> getFood() {
        return fruits;
    }

    /**
     * Returns an instance of the Bush's food
     * @return An instance of the Bush's food
     */
    @Override
    public Item getFoodInstance() {
        if (!isEmpty()){
            return fruits.get(0);
        }
        return null;
    }

    /**
     * Returns the name of the Bush
     * @return  The name of the Bush
     */
    public String getName(){
        return NAME;
    }
}
