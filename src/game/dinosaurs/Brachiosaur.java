package game.dinosaurs;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.grounds.Bush;
import game.grounds.Tree;
import game.items.Corpse;
import game.items.Fruit;

public class Brachiosaur extends Dinosaur {

    private final int STARTING_HITPOINTS = 100;
    private final int MAX_HITPOINTS = 160;
    private final String NAME = "Brachiosaur";
    private final char DISPLAY_CHAR = 'B';

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
    public Brachiosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Brachiosaur() {
        super("Brachiosaur", 'B', 100, false);
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Checks if the Brachiosaur is dead, and places a Brachiosaur corpse on its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    @Override
    public void checkDead(GameMap map) {
        if (hitPoints <= 0) {
            map.locationOf(this).addItem(new Corpse(Corpse.Type.Branchiosaur));
            map.removeActor(this);
        }
    }

    /**
     * Used to let the dinosaur eat something. Adjusts hitpoints according to the food provided
     * @param food  The Item eaten
     */
    @Override
    public void eat(Item food) {
        final int FRUIT_HEAL = 5;
        if(food.getClass()==Fruit.class){
            heal(FRUIT_HEAL);
        }
    }
}
