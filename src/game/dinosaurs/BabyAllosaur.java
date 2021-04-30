package game.dinosaurs;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Fruit;

public class BabyAllosaur extends BabyDino {

    private final int STARTING_HITPOINTS = 20;
    private final int MAX_HITPOINTS = 100;
    private final String NAME = "Baby Allosaur";
    private final char DISPLAY_CHAR = 'a';

    private final Item[] FOOD = {new Corpse(Corpse.Type.Stegosaur)};    // The corpse type doesn't matter here, just adding it in for initialisation purposes
    private final Ground[] EATS_FROM = {};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public BabyAllosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyAllosaur() {
        super("Baby Allosaur", 'a', 20, false);
        boolean isFemale = Math.random() < 0.5;
        this.setFemale(isFemale);
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
            map.locationOf(this).addItem(new Corpse(Corpse.Type.Allosaur));
            map.removeActor(this);
        }
    }

    @Override
    public void eat(Item food) {

    }
}
