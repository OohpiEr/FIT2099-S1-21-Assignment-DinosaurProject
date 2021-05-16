package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.LayEggAction;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.Egg;

import java.util.HashMap;

/**
 * Abstract adult dinosaur class, dinosaurs of this class will be able to mate, get pregnant
 * and exhibit horny behaviour
 */
public abstract class AdultDino extends Dinosaur {

    /**
     * whether dinosaur is female. True if they're female, false otherwise.
     */
    protected boolean isFemale;
    /**
     * whether dinosaur is pregnant. True if they're pregnant, false otherwise.
     */
    protected boolean isPregnant;

    /**
     * a counter to track the dinosaur's turn of pregnancy. Dinosaur will lay an egg after this reaches 0.
     */
    protected int pregnantTick;

    /**
     * number of turns required for pregnancy.
     */
    protected int maxPregnantTick;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */

    /**
     * Constructor for an adult Dinosaur with all its default values
     *
     * @param name                  the name of the Actor
     * @param displayChar           the character that will represent the Actor in the display
     * @param startingHitpoints     the Actor's starting hit points
     * @param dinoType              the Actor's dinosaur type
     * @param maxHitpoints          the Actor's maximum hit points
     * @param hungryThreshold       the Actor's threshold of hunger
     * @param startingWaterLevel    the Actor's starting water level
     * @param maxWaterLevel         the Actor's maximum water level
     * @param food                  an array of classes the Actor eats as food
     * @param fromTheseEatsThese    a HashMap with keys of Grounds that the Actor eats from, and values of the foods that it eats from said Grounds
     * @param isFemale              whether the Actor is female
     */
    public AdultDino(String name, char displayChar, int startingHitpoints, DinosaurEnumType dinoType, int maxHitpoints, int hungryThreshold, int startingWaterLevel, int maxWaterLevel, Class<?>[] food, HashMap<Class<?>, Class<?>[]> fromTheseEatsThese, boolean isFemale) {
        super(name, displayChar, startingHitpoints, dinoType, maxHitpoints, hungryThreshold, startingWaterLevel, maxWaterLevel, food, fromTheseEatsThese);
        this.isFemale = isFemale;
        isPregnant = false;
        pregnantTick = 0;
        setFemale(isFemale);
    }

    /**
     * Gets the number of turns the dinosaur will remain pregnant for, pregnantTick
     *
     * @return The number of turns the dinosaur will remain pregnant for, pregnantTick
     */
    public int getPregnantTick() {
        return pregnantTick;
    }

    /**
     * getter for isFemale
     *
     * @return true if dinosaur is female, false otherwise
     */
    public boolean isFemale() {
        return isFemale;
    }

    /**
     * setter for isFemale
     *
     * @param female true if dinosaur is female, false otherwise
     */
    public void setFemale(boolean female) {
        isFemale = female;
    }

    /**
     * setter for isPregnant
     *
     * @param pregnant True if dinosaur is pregnant, false otherwise
     */
    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    /**
     * getter for isPregnant
     *
     * @return True if dinosaur is pregnant, false otherwise
     */
    public boolean isPregnant() {
        return isPregnant;
    }

    /**
     * sets dinosaur behaviours
     */
    @Override
    protected void setBehaviours() {
        actionFactories.add(new WanderBehaviour());
        actionFactories.add(new HungryBehaviour());
        actionFactories.add(new HornyBehaviour());
    }

    /**
     * resets pregnant tick to it's maximum tick
     */
    public void resetPregnantTick(){
        pregnantTick = maxPregnantTick;
    };

    /**
     * lays egg on nearest possible ground
     *
     * @param map The GameMap the egg is on
     */
    public void layEgg(GameMap map) {
        if (isPregnant() && pregnantTick == 0) {
            setPregnant(false);
            Egg egg = null;

            switch (dinoType) {
                case STEGOSAUR -> egg = new Egg(DinosaurEnumType.STEGOSAUR);
                case BRANCHIOSAUR -> egg = new Egg(DinosaurEnumType.BRANCHIOSAUR);
                case ALLOSAUR -> egg = new Egg(DinosaurEnumType.ALLOSAUR);
            }

            if (egg != null) {
                map.locationOf(this).addItem(egg);
            }
        }
    }

    /**
     * Inform a Dino the passage of time.
     * This method is called once per turn
     * Actions that depend on time/number of turns will be returned if conditions are met
     *
     * @param map the map the actor is in
     * @return an action if applicable
     */
    @Override
    protected Action tick(GameMap map) {
        super.tick(map);
        if (isPregnant() && pregnantTick > 0) {
            pregnantTick -= 1;
        } else if (pregnantTick == 0 && isPregnant == true) {
            return new LayEggAction();
        }
        return null;
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return super.playTurn(actions, lastAction, map, display);
    }
}
