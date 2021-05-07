package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.Util;
import game.actions.AttackAction;
import game.behaviours.Behaviour;
import game.behaviours.HornyBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Egg;
import game.items.Fruit;
import game.behaviours.HungryBehaviour;
import game.items.VegetarianMealKit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends AdultDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.STEGOSAUR;
    private static final int STARTING_HITPOINTS = 50;
    private static final int MAX_HITPOINTS = 100;
    private static final int PREGNANT_TICK = 10;
    private static final String NAME = "Stegosaur";
    private static final char DISPLAY_CHAR = 'S';
    private static final Class<?>[] FOOD = {Fruit.class, VegetarianMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Bush.class, new Class[]{Fruit.class});
        put(Ground.class, new Class[]{Fruit.class});
    }};

    /**
     * Constructor.
     * All Stegosaurs are represented by a 'S' and have 100 max hit points.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Stegosaur(String name, boolean isFemale) {
        super(name, 'S', 50, isFemale);
        setDefaultValues();
        setBehaviours();

    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Stegosaur() {
        super("Stegosaur", 'S', 50, false);
        this.setFemale(Math.random() < 0.5);
        setDefaultValues();
        setBehaviours();
    }

    /**
     * Sets the dinosaur instance's variables to their default values as specified in the class
     */
    private void setDefaultValues(){
        hitPoints = STARTING_HITPOINTS;
        maxHitpoints = MAX_HITPOINTS;
        pregnantTick = PREGNANT_TICK;
        name = NAME;
        displayChar = DISPLAY_CHAR;
        food = FOOD;
        fromTheseEatsThese = FROM_THESE_EATS_THESE;
        dinoType = DINO_TYPE;
    }

    /**
     * todo temp constructor for testing
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Stegosaur(int hitPoints) {
        super("Stegosaur", 'S', 50, false);
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = hitPoints;
        maxHitPoints = MAX_HITPOINTS;
        setBehaviours();
    }


    /**
     * sets dinosaur behaviours
     */
    @Override
    protected void setBehaviours() {
        super.setBehaviours();
        actionFactories.add(new HungryBehaviour());
    }

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    @Override
    public void eat(Item food, int quantity) {
        final int FRUIT_HEAL = 10;
        if (food.getClass() == Fruit.class) {
            for (int i = 0; i < quantity; i++) {
                heal(FRUIT_HEAL);
            }
        }
    }

    /**
     * resets pregnant tick to stegosaur's maximum pregnant tick
     */
    @Override
    public void resetPregnantTick() {
        this.pregnantTick = PREGNANT_TICK;
    }

    /**
     * determines the highest priority behaviour based on probability
     *
     * @param map gamemap the actor is on
     * @return action to be performed this playturn
     */
    protected Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints >= 90 && hitPoints <= 100) {
            //wander behaviour or horny behaviour
            if (!isPregnant() && Math.random() < 0.4) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else {
                action = getBehaviourAction(WanderBehaviour.class, map);
            }
        } else if (hitPoints >= 50 && hitPoints < 90) {
            //hungry, horny or wander behaviour
            if (!isPregnant() && Math.random() <= 1) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else if (Math.random() <= 0.5) {
                action = getBehaviourAction(HungryBehaviour.class, map);
            } else {
                action = getBehaviourAction(WanderBehaviour.class, map);
            }
        } else if (hitPoints < 50) {
            //hungry behaviour
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }



    /**
     * Select and return an action to perform on the current turn.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action action = super.playTurn(actions, lastAction, map, display);

        if (action == null && lastAction.getNextAction() != null) {
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
