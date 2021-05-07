package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.LayEggAction;
import game.behaviours.HornyBehaviour;
import game.items.Egg;

public abstract class AdultDino extends Dinosaur {

    protected boolean isFemale;
    protected boolean isPregnant;
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

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    @Override
    protected void setBehaviours() {
        super.setBehaviours();
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

    @Override
    protected Action tick(GameMap map) {
        super.tick(map);
        if (isPregnant() && pregnantTick > 0) {
            pregnantTick -= 1;
        } else if (pregnantTick == 0 && isPregnant == true) {
            return new LayEggAction(this, map);
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
