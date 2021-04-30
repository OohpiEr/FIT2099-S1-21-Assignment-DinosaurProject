package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.Util;
import game.actions.AttackAction;
import game.behaviours.Behaviour;
import game.behaviours.HornyBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Fruit;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {

    private final int STARTING_HITPOINTS = 50;
    private final int MAX_HITPOINTS = 100;
    private final int HUNGRY_BEHAVIOUR = 1;
    private final int HORNY_BEHAVIOUR = 2;
    private final String NAME = "Stegosaur";
    private final char DISPLAY_CHAR = 'S';

    private final Item[] FOOD = {new Fruit()};
    private final Ground[] EATS_FROM = {new Bush()};

    /**
     * Constructor.
     * All Stegosaurs are represented by a 'd' and have 100 hit points.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Stegosaur(String name, boolean isFemale) {
        super(name, 'S', 50, isFemale);
        maxHitPoints = MAX_HITPOINTS;
        actionFactories.add(new HungryBehaviour(Fruit.class));
        actionFactories.add(new HornyBehaviour());
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Stegosaur() {
        super("Stegosaur", 'S', 50, false);
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: use lastAction.getNextAction --> continue behaviour?? how
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        super.playTurn(actions, lastAction, map, display);

        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        Action action = determineBehaviour(map);
        if (action != null) {
            return action;
        } else {
            return actions.get(Util.random(0, actions.size() - 1));
        }
    }

    /**
     * determines the highest priority behaviour based on probability
     * FIXME: use lastAction.getNextAction --> continue behaviour?? how
     * FIXME: make it default to hundry behaviour if no mate (how??)
     * FIXME: have to remove behaviour from collection so it doesn't keep adding more
     */
    private Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints > 50 && hitPoints < 90) {
            //hungry behaviour or horny behaviour
            if (Util.getBooleanProbability(0.4)) {
                action = actionFactories.get(HORNY_BEHAVIOUR).getAction(this, map);
            } else {
                action = actionFactories.get(HUNGRY_BEHAVIOUR).getAction(this, map);
            }
        } else if (hitPoints <= 50) {
            //hungry behaviour
            action = actionFactories.get(HUNGRY_BEHAVIOUR).getAction(this, map);
        }

        return action;
    }

    /**
     * Checks if the Stegosaur is dead, and places a Stegosaur corpse on its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    @Override
    public void checkDead(GameMap map) {
        if (hitPoints <= 0) {
            map.locationOf(this).addItem(new Corpse(Corpse.Type.Stegosaur));
            map.removeActor(this);
        }
    }

    @Override
    public void eat(Item food) {

    }

}
