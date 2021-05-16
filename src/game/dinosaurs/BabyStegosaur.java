package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Fruit;
import game.items.VegetarianMealKit;

import java.util.HashMap;
import java.util.Map;

/**
 * A baby Stegosaur, a herbivorous dinosaur
 */
public class BabyStegosaur extends BabyDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.STEGOSAUR;
    private static final int STARTING_HITPOINTS = 10;
    private static final int MAX_HITPOINTS = 100;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final int STEGOSAUR_GROW_UP_TICK = 10;
    private static final String NAME = "Baby Stegosaur";
    private static final char DISPLAY_CHAR = 's';
    public static final int HUNGRY_THRESHOLD = 90;
    private static final Class<?>[] FOOD = {Fruit.class, VegetarianMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Bush.class, new Class[]{Fruit.class});
        put(Ground.class, new Class[]{Fruit.class});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyStegosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , STEGOSAUR_GROW_UP_TICK);
        setDefaultValues();
    }

    /**
     * Constructor. Sets initial hitPoints to 10 and randomises gender
     */
    public BabyStegosaur() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , STEGOSAUR_GROW_UP_TICK);
        setDefaultValues();
    }

    /**
     * Sets the dinosaur instance's variables to their default values as specified in the class
     */
    private void setDefaultValues() {
        hitPoints = STARTING_HITPOINTS;
        maxHitpoints = MAX_HITPOINTS;
        maxWaterLevel = MAX_WATER_LEVEL;
        startingWaterLevel = STARTING_WATER_LEVEL;
        name = NAME;
        displayChar = DISPLAY_CHAR;
        food = FOOD;
        hungryThreshold = HUNGRY_THRESHOLD;
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
     * determines the highest priority behaviour
     *
     * @param map gamemap the actor is on
     * @return action
     */
    @Override
    protected Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints <= 90) {
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

    /**
     * replaces baby stegosaur with new instance of Stegosaur
     *
     * @param map the map the actor is on
     */
    @Override
    public void growUp(GameMap map) {
        Location actorLocation = map.locationOf(this);
        map.removeActor(this);
        map.addActor(new Stegosaur(), actorLocation);
    }
}
