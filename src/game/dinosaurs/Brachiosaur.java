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
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 200;
    private static final int THIRSTY_THRESHOLD = 40;
    private static final String NAME = "Brachiosaur";
    private static final char DISPLAY_CHAR = 'B';
    private static final int MAX_PREGNANT_TICK = 30;
    public static final int HUNGRY_THRESHOLD = 140;
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
        super(name, displayChar, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE , isFemale, MAX_PREGNANT_TICK);
        setBehaviours();
    }

    /**
     * Constructor.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Brachiosaur(String name, boolean isFemale) {
        super(name, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE , isFemale, MAX_PREGNANT_TICK);
        setBehaviours();
    }

    /**
     * Constructor. Randomises gender
     */
    public Brachiosaur() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS, DINO_TYPE, MAX_HITPOINTS, HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD, FROM_THESE_EATS_THESE, false, MAX_PREGNANT_TICK);
        this.setFemale(Math.random() < 0.5);
        setBehaviours();
    }

    public boolean isDead() {
        if (hitPoints <= -15 || waterLevel <= -15) {
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
    @Override
    public void eat(Item food, int quantity) {
        final int FRUIT_HEAL = 5;
        final int VEGETARIAN_MEAL_KIT_HEAL = maxHitPoints;
        if (food.getClass() == Fruit.class) {
            for (int i = 0; i < quantity; i++) {
                heal(FRUIT_HEAL);
            }
        } else if (food.getClass() == VegetarianMealKit.class){
            for (int i = 0; i < quantity; i++) {
                heal(VEGETARIAN_MEAL_KIT_HEAL);
            }
        }
    }

    @Override
    public void drink(int sips) {
        adjustWaterLevel(80);
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
