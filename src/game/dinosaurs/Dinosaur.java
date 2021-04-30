package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.Behaviour;
import game.behaviours.WanderBehaviour;

import java.util.ArrayList;
import java.util.List;

public abstract class Dinosaur extends Actor {

    private final int STARTING_HITPOINTS = 10;
    private final int MAX_HITPOINTS = 100;
    private final int WANDER_BEHAVIOUR = 0;

    private ArrayList<Item> food;
    private ArrayList<Ground> eatsFrom;

    /**
     * gender
     */
    private boolean isFemale;
    private boolean isPregnant;
    // TODO diet (capability) ???
    // TODO why is this public?? -> demo/mars/bug has it public -> chechk why
    public List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Dinosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints);
        setFemale(isFemale);
        this.isPregnant = false;
        this.maxHitPoints = MAX_HITPOINTS;
        actionFactories.add(new WanderBehaviour());
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    /**
     * Used to check if a dinosaur is dead, and execute the required functions
     * @param map       the GameMap the dinosaur is in
     */
    public abstract void checkDead(GameMap map);

    /**
     * Figure out what to do next.
     * <p>
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        checkDead(map);
        return new DoNothingAction();
    }


}
