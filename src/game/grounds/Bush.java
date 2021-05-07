package game.grounds;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.hasFood;
import game.Player;
import game.actions.PickFruitAction;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Dinosaur;
import game.items.Fruit;

import java.util.ArrayList;

/**
 * A class that represents a bush
 */
public class Bush extends Ground implements hasFood {

    private ArrayList<Fruit> fruits;
    private final double CHANCE_TO_PRODUCE_FRUIT = 0.1;
    private final double CHANCE_TO_BE_KILLED_BY_BRACHIOSAUR = 0.5;
    private final String NAME = "bush";
    private final int ECO_POINT_REWARD = 1;

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
            Player.addEcoPoints(ECO_POINT_REWARD);
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
     * Causes a dinosaur to eat a quantity of food from this object
     * @param dinosaur  The dinosaur eating the food
     * @param food      The food that is eaten
     * @param quantity  The quantity of the food that is eaten
     */
    @Override
    public void eatFromThis(Dinosaur dinosaur, Item food, int quantity) {
        if(food.getClass()==Fruit.class){
            removeFruits(quantity);
            dinosaur.eat(food, quantity);
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
     * Removes the specified number of fruits to the Bush's fruits
     *
     * @return True if the operation was a success. False otherwise
     */
    @Override
    public boolean removeFood(int quantity) {
        return removeFruits(quantity);
    }

    /**
     * Returns the name of the Bush
     * @return  The name of the Bush
     */
    public String getName(){
        return NAME;
    }

    /**
     * Returns the actions allowed by actors next to it.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        if (actor instanceof Player && !isEmpty()){
            return new Actions(new PickFruitAction(this, 1));
        }
        return null;
    }
}
