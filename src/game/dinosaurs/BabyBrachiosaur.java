package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.grounds.Bush;
import game.grounds.Tree;
import game.items.Corpse;
import game.items.Fruit;

import java.util.HashMap;
import java.util.Map;

/**
 * A baby Brachiosaur, a herbivorous dinosaur
 */
public class BabyBrachiosaur extends BabyDino {

    private static final int STARTING_HITPOINTS = 10;
    private static final int MAX_HITPOINTS = 160;
    private static final String NAME = "Baby Brachiosaur";
    private static final char DISPLAY_CHAR = 'b';

    private static final Class<?>[] FOOD = {Fruit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>(){{
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
        super(name, displayChar, hitPoints);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Sets initial hitPoints to 10 and randomises gender
     */
    public BabyBrachiosaur() {
        super("Baby Branchiosaur", 'b', 10);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     * @param food      The Item eaten
     * @param quantity  The quantity of the food eaten
     */
    @Override
    public void eat(Item food, int quantity) {
        final int FRUIT_HEAL = 5;
        if(food.getClass()==Fruit.class){
            for(int i=0;i<quantity;i++){
                heal(FRUIT_HEAL);
            }
        }
    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        return null;
    }
}
