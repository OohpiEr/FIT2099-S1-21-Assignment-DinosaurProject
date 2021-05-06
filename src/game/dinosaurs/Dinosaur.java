package game.dinosaurs;

import edu.monash.fit2099.engine.*;
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

    private final int STARTING_HITPOINTS = 10;
    private final int MAX_HITPOINTS = 100;
    protected final int WANDER_BEHAVIOUR = 0;
    protected final Class<?>[] FOOD = {};
    private final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>();

    /**
     * gender
     */
    protected List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    protected DinosaurEnumType dinoType;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints, DinosaurEnumType dinoType) {
        super(name, displayChar, hitPoints);
        this.dinoType = dinoType;
        this.maxHitPoints = MAX_HITPOINTS;
        setBehaviours();
    }

    /**
     * sets dinosaur behaviours
     */
    protected void setBehaviours() {
        actionFactories.add(new WanderBehaviour());
    }


    /**
     * Used to check if a dinosaur is dead, and execute the required functions
     *
     * @param map the GameMap the dinosaur is in
     */
    public abstract void checkDead(GameMap map);

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    public abstract void eat(Item food, int quantity);

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
        checkDead(map);
        Action action = tick(map);
        if (action != null) {
            return action;
        } else {
            return null;
        }
    }

}
