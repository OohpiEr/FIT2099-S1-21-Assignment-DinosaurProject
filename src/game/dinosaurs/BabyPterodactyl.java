package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.items.CarnivoreMealKit;
import game.items.Corpse;

import java.util.HashMap;

/**
 * A baby Pterodactyl, a herbivorous dinosaur
 */
public class BabyPterodactyl extends BabyDino{

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.PTERODACTYL;
    private static final int STARTING_HITPOINTS = 30;
    private static final int MAX_HITPOINTS = 60;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final String NAME = "Pterodactyl";
    private static final char DISPLAY_CHAR = 'p';
    public static final int HUNGRY_THRESHOLD = 50;
    private static final int PTERODACTYL_GROW_UP_TICK = 20;
    private static final Class<?>[] FOOD = {Corpse.class, CarnivoreMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Ground.class, new Class[]{Corpse.class});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyPterodactyl(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , PTERODACTYL_GROW_UP_TICK);
    }


    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyPterodactyl() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, FOOD,FROM_THESE_EATS_THESE , PTERODACTYL_GROW_UP_TICK);
    }

    @Override
    public void growUp(GameMap map) {

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
