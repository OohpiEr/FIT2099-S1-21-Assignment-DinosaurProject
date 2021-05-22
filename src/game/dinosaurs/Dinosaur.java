package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.actions.AttackAction;
import game.actions.DieAction;
import game.actions.FeedAction;
import game.behaviours.Behaviour;
import game.behaviours.AttackBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Lake;
import game.items.Corpse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract class to represent a dinosaur
 */
public abstract class Dinosaur extends Actor {

    protected DinosaurEnumType dinoType;
    protected int startingHitpoints;
    protected int hungryThreshold;
    protected int waterLevel;
    protected int maxWaterLevel;
    protected int thirstyThreshold;

    /**
     * food stores the classes that the dinosaur eats as food
     * fromTheseEatsThese is a HashMap with Ground classes that the dinosaur eats from, which values are the foods that they eat from them
     */
    protected Class<?>[] food;
    protected HashMap<Class<?>, Class<?>[]> fromTheseEatsThese;

    /**
     * Constructor for a Dinosaur with all its starting values
     *
     * @param name               the name of the Actor
     * @param displayChar        the character that will represent the Actor in the display
     * @param startingHitpoints  the Actor's starting hit points
     * @param dinoType           the Actor's dinosaur type
     * @param maxHitpoints       the Actor's maximum hit points
     * @param hungryThreshold    the Actor's threshold of hunger
     * @param startingWaterLevel the Actor's starting water level
     * @param maxWaterLevel      the Actor's maximum water level
     * @param thirstyThreshold   the Actor's threshold of thirst
     * @param food               an array of classes the Actor eats as food
     * @param fromTheseEatsThese a HashMap with keys of Grounds that the Actor eats from, and values of the foods that it eats from said Grounds
     */
    public Dinosaur(String name, char displayChar, int startingHitpoints, DinosaurEnumType dinoType, int maxHitpoints, int hungryThreshold, int startingWaterLevel, int maxWaterLevel, int thirstyThreshold, Class<?>[] food, HashMap<Class<?>, Class<?>[]> fromTheseEatsThese) {
        super(name, displayChar, startingHitpoints);
        this.dinoType = dinoType;
        this.maxHitPoints = maxHitpoints;
        this.hungryThreshold = hungryThreshold;
        this.waterLevel = startingWaterLevel;
        this.maxWaterLevel = maxWaterLevel;
        this.thirstyThreshold = thirstyThreshold;
        this.food = food;
        this.fromTheseEatsThese = fromTheseEatsThese;
        setBehaviours();
    }

    /**
     * sets dinosaur behaviours
     */
    protected abstract void setBehaviours();


    /**
     * list of behaviors that a dinosaur might exhibit
     */
    protected List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    /**
     * getter for Dinosaur Enum Type
     *
     * @return DinosaurEnumType of dinosaur type
     */
    public DinosaurEnumType getDinoType() {
        return dinoType;
    }

    /**
     * getter for starting hitpoints
     *
     * @return returns the staring hitpoints of the dinosaur as an integer
     */
    public int getStartingHitpoints() {
        return startingHitpoints;
    }

    /**
     * getter for maximum hitpoints
     *
     * @return returns the maximum hitpoints of the dinosaur as an integer
     */
    public int getMaxHitpoints() {
        return maxHitPoints;
    }

    /**
     * getter for water level
     *
     * @return the dinosaur's water level
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * adjusts the dinosaur's water level
     *
     * @param waterLevel the amount to adjust the dinosaur's water level by. Can be negative
     */
    public void adjustWaterLevel(int waterLevel) {
        this.waterLevel += waterLevel;
    }

    /**
     * getter for the display character of the dinosaur
     *
     * @return a char of the display character of the dinosaur
     */
    @Override
    public char getDisplayChar() {
        return displayChar;
    }

    /**
     * Gets an array of foods the dinosaur eats
     *
     * @return An array of foods the dinosaur eats
     */
    public Class<?>[] getFood() {
        return food;
    }

    /**
     * Gets a HaspMap of Grounds the dinosaur eats from as keys, each with an array of foods that it eats from those grounds as values
     *
     * @return A HaspMap of Grounds the dinosaur eats from as keys, each with an array of foods that it eats from those grounds as values
     */
    public HashMap<Class<?>, Class<?>[]> getFromTheseEatsThese() {
        return fromTheseEatsThese;
    }

    @Override
    public boolean isConscious() {
        return (super.isConscious() && waterLevel > 0);
    }

    /**
     * Checks if the Dinosaur is dead
     *
     * @return True if the dinosaur is dead, False otherwise
     * @see Corpse
     */
    public boolean isDead() {
        if (hitPoints <= -20 || waterLevel <= -15) {
            return true;
        }
        return false;
    }

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    public abstract void eat(Item food, int quantity);

    /**
     * Used to let the dinosaur drink a number of sips of water. Adjusts waterLevel accordingly
     *
     * @param sips The number of sips taken
     */
    public abstract void drink(int sips);

    /**
     * Returns a collection of the Actions that the otherActor can do to the current Actor.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of the Actions that the otherActor can do to the current Actor.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = super.getAllowableActions(otherActor, direction, map);
        actions.add(new AttackAction(this));
        if (otherActor instanceof Player) {
            for (Item item : otherActor.getInventory()) {
                for (Class<?> food : getFood()) {
                    if (item.getClass() == food) {
                        actions.add(new FeedAction(this, item, 1));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * abstract method to be implemented
     * determines the highest priority behaviour based on probability
     *
     * @param map gamemap the actor is on
     * @return action to be performed this playturn
     */
    protected abstract Action determineBehaviour(GameMap map);

    /**
     * gets the required behaviour from actionfactories and returns it's getAction
     *
     * @param clazz class of the Behaviour
     * @param map   gamemap the actor is on
     * @return Action performed by the behaviour
     */
    protected Action getBehaviourAction(Class<?> clazz, GameMap map) {
        Action action = null;
        for (Behaviour behaviour : actionFactories) {
            if (behaviour.getClass() == clazz) {
                action = actionFactories.get(actionFactories.indexOf(behaviour)).getAction(this, map);
                break;
            }
        }
        return action;
    }

    /**
     * Inform a Dino the passage of time.
     * This method is called once per turn.
     * Actions that depend on time/number of turns will be returned if conditions are met
     *
     * @param map the map the actor is in
     * @return an action if applicable
     */
    protected Action tick(GameMap map) {
        hitPoints -= 1;
        waterLevel-=1;
        for (Behaviour behaviour : actionFactories) {
            if (behaviour instanceof AttackBehaviour) {
                ((AttackBehaviour) behaviour).tick();
            }
        }
        return null;
    }


    /**
     * Select and return an action to perform on the current turn. Also tells the player when the dinosaur starts to get hungry.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (hitPoints == hungryThreshold - 1) {
            display.println(this + " at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is getting hungry!");
        }
        if (waterLevel == thirstyThreshold - 1) {
            display.println(this + " at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is getting thirsty!");
        }
        Action action = tick(map);
        if (waterLevel <= 0 && Lake.getRainfall() > 0) {
            waterLevel = 10;
        }
        if (isDead()) {
            return new DieAction();
        } else if (!isConscious()) {
            display.println(this + " at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") is unconscious");
            return new DoNothingAction();
        } else {
            for (Exit exit : map.locationOf(this).getExits()) {
                if (exit.getDestination().getGround() instanceof Lake || map.locationOf(this).getGround() instanceof Lake) {
                    this.drink(1);
                    ((Lake) exit.getDestination().getGround()).adjustSips(-1);
                    display.println(this + " at (" + map.locationOf(this).x() + ", " + map.locationOf(this).y() + ") takes a sip of water");
                    break;
                }
            }
            if (action == null && lastAction != null && lastAction.getNextAction() != null) {
                action = lastAction.getNextAction();
            } else if (action == null) {
                action = determineBehaviour(map);
                if (action == null) {
                    action = getBehaviourAction(WanderBehaviour.class, map);
                }
            }
            return action;
        }
    }
}
