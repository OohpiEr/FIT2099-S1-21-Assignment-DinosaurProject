package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.LayEggAction;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.Egg;

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
     * number of turns required for pregnancy. Dinosaur will lay an egg after this reaches 0.
     */
    protected int pregnantTick;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public AdultDino(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints);
        this.isFemale = isFemale;
        isPregnant = false;
        pregnantTick = 0;
        setFemale(isFemale);
    }

    /**
     * getter for pregantTick
     *
     * @return pregantTick
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
     * @param female whether dinosaur is female
     */
    public void setFemale(boolean female) {
        isFemale = female;
    }

    /**
     * setter for isPregnant
     *
     * @param pregnant whether dinosaur is pregnant
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
    public abstract void resetPregnantTick();

    /**
     * lays egg on nearest possible ground
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
