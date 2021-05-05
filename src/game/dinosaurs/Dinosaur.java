package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.LayEggAction;
import game.behaviours.Behaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Egg;
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
    protected final Class<?>[] FOOD = {};
    protected final Class<?>[] EATS_FROM = {};

    /**
     * gender
     */
    protected boolean isFemale;
    protected boolean isPregnant;
    protected int pregnantTick;
    protected List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    protected DinosaurEnumType dinoType;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Dinosaur(String name, char displayChar, int hitPoints, boolean isFemale, DinosaurEnumType dinoType) {
        super(name, displayChar, hitPoints);
        this.dinoType = dinoType;
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
     * Inform a Dino the passage of time.
     * This method is called once per turn
     *
     * @param map the map the actor is in
     * @return an action if applicable
     */
    protected Action tick(GameMap map) {
        hitPoints -= 1;
        if (pregnantTick > 0 && isPregnant == true) {
            pregnantTick -= 1;
        } else if (pregnantTick == 0 && isPregnant == true) {
            return new LayEggAction(this, map);
        }
        return null;
    }

    /**
     * lays egg on nearest possible ground
     */
    public void layEgg(GameMap map) {
        if (isPregnant() && pregnantTick == 0) {
            setPregnant(false);
            Egg egg = null;

            switch (dinoType) {
                case STEGOSAUR -> egg = new Egg(DinosaurEnumType.STEGOSAUR);
                case BRANCHIOSAUR -> egg = new Egg(DinosaurEnumType.BRANCHIOSAUR);
                case ALLOSAUR -> egg = new Egg(DinosaurEnumType.ALLOSAUR);
            }

            if (egg != null) {
                map.locationOf(this).addItem(egg);
            }
        }
    }

    /**
     * resets pregnant tick to it's maximum tick
     */
    public void resetPregnantTick() {
    }

    ;

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
        Action action = tick(map);
        if (action != null) {
            return action;
        } else {
            return null;
        }
    }

}
