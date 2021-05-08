package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.grounds.Tree;
import game.items.Corpse;
import game.items.Fruit;
import game.items.VegetarianMealKit;

import java.util.HashMap;
import java.util.Map;

/**
 * A herbivorous dinosaur
 */
public class Brachiosaur extends AdultDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.BRANCHIOSAUR;
    private static final int STARTING_HITPOINTS = 100;
    private static final int MAX_HITPOINTS = 160;
    private static final String NAME = "Brachiosaur";
    private static final char DISPLAY_CHAR = 'B';
    private static final int PREGNANT_TICK = 30;
    private static final Class<?>[] FOOD = {Fruit.class, VegetarianMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Tree.class, new Class[]{Fruit.class});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Brachiosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
        setDefaultValues();
        setBehaviours();
    }

    /**
     * Constructor.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Brachiosaur(String name, boolean isFemale) {
        super(name, 'B', 100, isFemale);
        setDefaultValues();
        setBehaviours();
    }

    /**
     * Constructor. Randomises gender
     */
    public Brachiosaur() {
        super("Brachiosaur", 'B', 100, false);
        this.setFemale(Math.random() < 0.5);
        setDefaultValues();
        setBehaviours();
    }

    /**
     * Sets the dinosaur instance's variables to their default values as specified in the class
     */
    private void setDefaultValues() {
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
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    @Override
    public void eat(Item food, int quantity) {
        final int FRUIT_HEAL = 5;
        if (food.getClass() == Fruit.class) {
            for (int i = 0; i < quantity; i++) {
                heal(FRUIT_HEAL);
            }
        }
    }

    /**
     * resets pregnant tick to Brachiosaur's maximum pregnant tick
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
    @Override
    protected Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints >= 140 && hitPoints <= MAX_HITPOINTS) {
            //wander behaviour or horny behaviour
            if (Math.random() < 0.4) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else {
                action = getBehaviourAction(WanderBehaviour.class, map);
            }
        } else if (hitPoints >= 100 && hitPoints < 140) {
            //hungry behaviour or horny behaviour
            if (Math.random() <= 0.2) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else if (Math.random() <= 0.7) {
                action = getBehaviourAction(HungryBehaviour.class, map);
            } else {
                action = getBehaviourAction(WanderBehaviour.class, map);
            }
        } else if (hitPoints < 100) {
            //hungry behaviour
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

}
