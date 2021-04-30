package game.dinosaurs;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.items.Corpse;

public class BabyStegosaur extends BabyDino{

    private final int STARTING_HITPOINTS = 10;
    private final int MAX_HITPOINTS = 100;
    private final String NAME = "Baby Stegosaur";
    private final char DISPLAY_CHAR = 's';

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public BabyStegosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Sets initial hitPoints to 10 and randomises gender
     */
    public BabyStegosaur(){
        super("Baby Stegosaur", 's',10, false);
        boolean isFemale = Math.random() < 0.5;
        this.setFemale(isFemale);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Checks if the baby Stegosaur is dead, and places a Stegosaur corpse on its location in its place if it is
     * @see Corpse
     * @param map       the GameMap the dinosaur is in
     */
    @Override
    public void checkDead(GameMap map) {
        if(hitPoints<=0){
            map.locationOf(this).addItem(new Corpse(Corpse.Type.Stegosaur));
            map.removeActor(this);
        }
    }

    @Override
    public void eat(Item food) {

    }
}
