package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.Behaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.items.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class to represent a dinosaur
 */
public abstract class Dinosaur extends Actor {

    private final int STARTING_HITPOINTS = 10;
    private final int MAX_HITPOINTS = 100;
    protected final int WANDER_BEHAVIOUR = 0;

    protected final Item[] FOOD = {};
    protected final Ground[] EATS_FROM = {};

    /**
     * gender
     */
    protected boolean isFemale;
    protected boolean isPregnant;
    protected int pregnantTick;
    protected List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Dinosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints);
        setFemale(isFemale);
        this.isPregnant = false;
        this.maxHitPoints = MAX_HITPOINTS;
        this.pregnantTick = 0;
        actionFactories.add(new WanderBehaviour());
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
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
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     *
     * @param actor the actor experiencing time
     */
    protected void tick(Actor actor) {
        if (pregnantTick > 0) {
            pregnantTick -= 1;
        }
    }

    /**
     * Figure out what to do next.
     * <p>
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        checkDead(map);
        tick(this);
        return new DoNothingAction();
    }


}
