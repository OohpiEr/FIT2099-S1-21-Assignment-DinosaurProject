package game.dinosaurs;

import edu.monash.fit2099.engine.*;
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
 * A baby Brachiosaur, a herbivorous dinosaur
 */
public class BabyBrachiosaur extends BabyDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.BRANCHIOSAUR;
    private static final int STARTING_HITPOINTS = 10;
    private static final int MAX_HITPOINTS = 160;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 200;
    private static final int THIRSTY_THRESHOLD = 40;
    private static final int BRACHIOSAUR_GROW_UP_TICK = 10;
    private static final String NAME = "Baby Brachiosaur";
    private static final char DISPLAY_CHAR = 'b';
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
     */
    public BabyBrachiosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD ,FOOD,FROM_THESE_EATS_THESE , BRACHIOSAUR_GROW_UP_TICK);
    }


    /**
     * Constructor. Sets initial hitPoints to 10 and randomises gender
     */
    public BabyBrachiosaur() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD ,FOOD,FROM_THESE_EATS_THESE , BRACHIOSAUR_GROW_UP_TICK);
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
     * determines the highest priority behaviour
     *
     * @param map gamemap the actor is on
     * @return actor to perform this turn
     */
    @Override
    protected Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints <= 90){
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

    /**
     * replaces baby brachiosaur with new instance of Brachiosaur
     *
     * @param map the map the actor is on
     */
    @Override
    public void growUp(GameMap map) {
        Location actorLocation = map.locationOf(this);
        map.removeActor(this);
        map.addActor(new Brachiosaur(), actorLocation);
    }
}
