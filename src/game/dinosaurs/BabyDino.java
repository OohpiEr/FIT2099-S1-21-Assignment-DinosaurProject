package game.dinosaurs;

import edu.monash.fit2099.engine.GameMap;
import game.items.Corpse;

/**
 * An abstract class to represent a baby dinosaur
 */
public abstract class BabyDino extends Dinosaur {
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public BabyDino(String name, char displayChar, int hitPoints, boolean isFemale, Type dinoType) {
        super(name, displayChar, hitPoints, isFemale, dinoType);
    }

    /**
     * Used to check if a dinosaur is dead, and execute the required functions
     *
     * @param map the GameMap the dinosaur is in
     */
    @Override
    public abstract void checkDead(GameMap map);
}
