package game.dinosaurs;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.grounds.Tree;
import game.items.Corpse;
import game.items.Fruit;

/**
 * A baby Brachiosaur, a herbivorous dinosaur
 */
public class BabyBrachiosaur extends BabyDino {

    private final int STARTING_HITPOINTS = 10;
    private final int MAX_HITPOINTS = 160;
    private final String NAME = "Baby Brachiosaur";
    private final char DISPLAY_CHAR = 'b';

    private final Item[] FOOD = {new Fruit()};
    private final Ground[] EATS_FROM = {new Tree()};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public BabyBrachiosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Sets initial hitPoints to 10 and randomises gender
     */
    public BabyBrachiosaur() {
        super("Baby Branchiosaur", 'b', 10, false);
        boolean isFemale = Math.random() < 0.5;
        this.setFemale(isFemale);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Checks if the baby Brachiosaur is dead, and places a Brachiosaur corpse on its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    @Override
    public void checkDead(GameMap map) {
        if (hitPoints <= 0) {
            map.locationOf(this).addItem(new Corpse(Corpse.Type.BRANCHIOSAUR));
            map.removeActor(this);
        }
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
}
