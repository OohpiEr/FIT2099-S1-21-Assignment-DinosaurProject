package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.actions.AttackAction;
import game.actions.DieAction;
import game.actions.FeedAction;
import game.actions.LayEggAction;
import game.behaviours.Behaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Egg;
import game.items.Fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract class to represent a dinosaur
 */
public abstract class Dinosaur extends Actor {


    private static final int STARTING_HITPOINTS = 10;
    private static final int MAX_HITPOINTS = 100;
    protected static final Class<?>[] FOOD = {};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>();

    /**
     * gender
     */
    protected List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    public DinosaurEnumType getDinoType() {
        return dinoType;
    }

    public int getStartingHitpoints() {
        return startingHitpoints;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    @Override
    public char getDisplayChar() {
        return displayChar;
    }

    public Class<?>[] getFood() {
        return food;
    }

    public HashMap<Class<?>, Class<?>[]> getFromTheseEatsThese() {
        return fromTheseEatsThese;
    }

    protected DinosaurEnumType dinoType;
    protected int startingHitpoints;
    protected int maxHitpoints;
    protected String name;
    protected char displayChar;
    protected Class<?>[] food;
    protected HashMap<Class<?>, Class<?>[]> fromTheseEatsThese;


    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        setBehaviours();
    }

    /**
     * sets dinosaur behaviours
     */
    protected void setBehaviours() {
        actionFactories.add(new WanderBehaviour());
    }


    /**
     * Checks if the Dinosaur is dead
     *
     * @return  True if the dinosaur is dead, False otherwise
     * @see Corpse
     */
    public boolean isDead() {
        if (hitPoints <= -20) {
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
     * This method is called once per turn
     *
     * @param map the map the actor is in
     * @return an action if applicable
     */
    protected Action tick(GameMap map) {
        hitPoints -= 1;
        return null;
    }


    /**
     * Select and return an action to perform on the current turn.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action action = tick(map);
        if (isDead()) {
            return new DieAction();
        } else {
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
