package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.items.CarnivoreMealKit;
import game.items.Corpse;
import game.items.Egg;

import java.util.HashMap;

/**
 * A class that represents an adult Pterodactyl
 */
public class Pterodactyl extends AdultDino{

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.PTERODACTYL;
    private static final int STARTING_HITPOINTS = 30;
    private static final int MAX_HITPOINTS = 60;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final String NAME = "Pterodactyl";
    private static final char DISPLAY_CHAR = 'P';
    private static final int MAX_PREGNANT_TICK = 10;
    public static final int HUNGRY_THRESHOLD = 50;
    private static final Class<?>[] FOOD = {Corpse.class, CarnivoreMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Ground.class, new Class[]{Corpse.class});
    }};

    /**
     * Constructor.
     * All Pterodactyls are represented by a 'P' and have 60 max hit points.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Pterodactyl(String name, boolean isFemale) {
        super(name, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , isFemale);
        setDefaultValues();
        setBehaviours();
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Pterodactyl() {
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

    @Override
    public void eat(Item food, int quantity) {

    }

    @Override
    public void drink(int sips) {

    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        return null;
    }
}
