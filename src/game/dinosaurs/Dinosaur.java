package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.Behaviour;

import java.util.ArrayList;
import java.util.List;

public abstract class Dinosaur extends Actor {
    private boolean isFemale;
    //diet (capability) what is this why
    // TODO why is this public?? -> demo/mars/bug has it public (maybe justify in design rational??)
    public List<Behaviour> actionFactories = new ArrayList<Behaviour>();

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }


}
