package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import game.actions.GrowUpAction;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.Corpse;

/**
 * An abstract class to represent a baby dinosaur.
 * Dinosaurs of this class will be able to grow up into an adult dinosaur.
 */
public abstract class BabyDino extends Dinosaur {

    /**
     * number of turns needed to grow into adult
     */
    protected int growUpTick;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyDino(String name, char displayChar, int hitPoints, int growUpTick) {
        super(name, displayChar, hitPoints);
        this.growUpTick = growUpTick;
    }

    /**
     * replaces baby dinosaur with adult dinosaur
     *
     * @param map the map the actor is on
     */
    public abstract void growUp(GameMap map);

    /**
     * Inform a Dino the passage of time.
     * This method is called once per turn.
     * Actions that depend on time/number of turns will be returned if conditions are met
     *
     * @param map the map the actor is in
     * @return an action if applicable
     */
    @Override
    protected Action tick(GameMap map) {
        super.tick(map);
        Action action = null;
        growUpTick -= 1;

        if (growUpTick <= 0) {
            action = new GrowUpAction();
        }
        return action;
    }

    /**
     * sets dinosaur behaviours
     */
    @Override
    protected void setBehaviours() {
        actionFactories.add(new WanderBehaviour());
        actionFactories.add(new HungryBehaviour());
    }
}
