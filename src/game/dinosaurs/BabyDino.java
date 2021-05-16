package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import game.actions.GrowUpAction;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.Corpse;

import java.util.HashMap;

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
     * Constructor for a baby Dinosaur with all its default values
     *
     * @param name                  the name of the Actor
     * @param displayChar           the character that will represent the Actor in the display
     * @param startingHitpoints     the Actor's starting hit points
     * @param dinoType              the Actor's dinosaur type
     * @param maxHitpoints          the Actor's maximum hit points
     * @param hungryThreshold       the Actor's threshold of hunger
     * @param startingWaterLevel    the Actor's starting water level
     * @param maxWaterLevel         the Actor's maximum water level
     * @param thirstyThreshold      the Actor's threshold of thirst
     * @param food                  an array of classes the Actor eats as food
     * @param fromTheseEatsThese    a HashMap with keys of Grounds that the Actor eats from, and values of the foods that it eats from said Grounds
     * @param growUpTick            a tracker for the number of turns until the Dinosaur grows up
     */
    public BabyDino(String name, char displayChar, int startingHitpoints, DinosaurEnumType dinoType, int maxHitpoints, int hungryThreshold, int startingWaterLevel, int maxWaterLevel, int thirstyThreshold, Class<?>[] food, HashMap<Class<?>, Class<?>[]> fromTheseEatsThese, int growUpTick) {
        super(name, displayChar, startingHitpoints, dinoType, maxHitpoints, hungryThreshold, startingWaterLevel,thirstyThreshold ,maxWaterLevel, food, fromTheseEatsThese);
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
