package game.dinosaurs;

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
 * A baby Allosaur, a carnivorous dinosaur
 */
public class BabyAllosaur extends BabyDino {

    private final int STARTING_HITPOINTS = 20;
    private final int MAX_HITPOINTS = 100;
    private final String NAME = "Baby Allosaur";
    private final char DISPLAY_CHAR = 'a';

    private final Item[] FOOD = {new Corpse(DinosaurEnumType.ALLOSAUR)};
    private final HashMap<Class<?>, Item[]> FROM_THESE_EATS_THESE = new HashMap<>(){{
        put(Tree.class, new Item[]{new Fruit()});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public BabyAllosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints,  DinosaurEnumType.ALLOSAUR);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyAllosaur() {
        super("Baby Allosaur", 'a', 20, DinosaurEnumType.ALLOSAUR);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Checks if the baby Allosaur is dead, and places an Allosaur corpse on its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    @Override
    public void checkDead(GameMap map) {
        if (hitPoints <= 0) {
            map.locationOf(this).addItem(new Corpse(DinosaurEnumType.ALLOSAUR));
            map.removeActor(this);
        }
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
}
