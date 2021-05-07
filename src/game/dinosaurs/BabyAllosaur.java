package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.grounds.Bush;
import game.grounds.Tree;
import game.items.CarnivoreMealKit;
import game.items.Corpse;
import game.items.Fruit;

import java.util.HashMap;
import java.util.Map;

/**
 * A baby Allosaur, a carnivorous dinosaur
 */
public class BabyAllosaur extends BabyDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.BRANCHIOSAUR;
    private static final int STARTING_HITPOINTS = 20;
    private static final int MAX_HITPOINTS = 100;
    private static final String NAME = "Baby Allosaur";
    private static final char DISPLAY_CHAR = 'a';

    private static final Class<?>[] FOOD = {Corpse.class, CarnivoreMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>(){{
        put(Ground.class, new Class[]{Corpse.class});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyAllosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyAllosaur() {
        super("Baby Allosaur", 'a', 20);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Sets the dinosaur instance's variables to their default values as specified in the class
     */
    private void setDefaultValues(){
        hitPoints = STARTING_HITPOINTS;
        maxHitpoints = MAX_HITPOINTS;
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
        final int ALLOSAUR_CORPSE_HEAL = 50;
        final int STEGOSAUR_CORPSE_HEAL = 50;
        final int BRACHIOSAUR_CORPSE_HEAL = this.maxHitPoints;
        if (food.getClass() == Corpse.class) {
            for (int i = 0; i < quantity; i++) {
                if (((Corpse) food).getType() == DinosaurEnumType.STEGOSAUR) {
                    heal(STEGOSAUR_CORPSE_HEAL);
                } else if (((Corpse) food).getType() == DinosaurEnumType.ALLOSAUR) {
                    heal(ALLOSAUR_CORPSE_HEAL);
                } else if (((Corpse) food).getType() == DinosaurEnumType.BRANCHIOSAUR) {
                    heal(BRACHIOSAUR_CORPSE_HEAL);
                }
            }
        }
    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        return null;
    }
}
