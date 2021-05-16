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
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final int MAX_PREGNANT_TICK = 10;
    private static final String NAME = "Stegosaur";
    private static final char DISPLAY_CHAR = 'S';
    private static final int HUNGRY_THRESHOLD = 90;
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
        super(name, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , isFemale);
        setDefaultValues();
        setBehaviours();
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Stegosaur() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , false);
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
        maxWaterLevel = MAX_WATER_LEVEL;
        startingWaterLevel = STARTING_WATER_LEVEL;
        maxPregnantTick = MAX_PREGNANT_TICK;
        pregnantTick = maxPregnantTick;
        name = NAME;
        displayChar = DISPLAY_CHAR;
        hungryThreshold = HUNGRY_THRESHOLD;
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
        final int FRUIT_HEAL = 10;
        final int VEGETARIAN_MEAL_KIT_HEAL = maxHitpoints;
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
        adjustWaterLevel(30);
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

        if (hitPoints >= 90 && hitPoints <= 100) {
            //wander behaviour or horny behaviour
            if (!isPregnant() && Math.random() < 0.4) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            }
        } else if (hitPoints >= 50 && hitPoints < 90) {
            if (!isPregnant() && Math.random() <= 0.5) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else if (Math.random() <= 0.5) {
                action = getBehaviourAction(HungryBehaviour.class, map);
            }
        } else if (hitPoints < 50) {
            //hungry behaviour
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

}
