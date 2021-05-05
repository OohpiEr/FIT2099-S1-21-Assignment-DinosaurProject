package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.Util;
import game.actions.AttackAction;
import game.behaviours.HornyBehaviour;
import game.grounds.Bush;
import game.items.Corpse;
import game.items.Egg;
import game.items.Fruit;
import game.behaviours.HungryBehaviour;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {

    private final DinosaurEnumType DINO_TYPE = DinosaurEnumType.STEGOSAUR;
    private final int STARTING_HITPOINTS = 50;
    private final int MAX_HITPOINTS = 100;
    private final int HUNGRY_BEHAVIOUR = 1;
    private final int HORNY_BEHAVIOUR = 2;
    private final int PREGNANT_TICK = 10;
    private final String NAME = "Stegosaur";
    private final char DISPLAY_CHAR = 'S';

    private final Class<?>[] FOOD = {Fruit.class};
    private final Class<?>[] EATS_FROM = {Bush.class};

    /**
     * Constructor.
     * All Stegosaurs are represented by a 'S' and have 100 max hit points.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Stegosaur(String name, boolean isFemale) {
        super(name, 'S', 50, isFemale, DinosaurEnumType.STEGOSAUR);
        dinoType = DINO_TYPE;
        maxHitPoints = MAX_HITPOINTS;
        hitPoints = STARTING_HITPOINTS;
        setBehaviours();
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Stegosaur() {
        super("Stegosaur", 'S', 50, false, DinosaurEnumType.STEGOSAUR);
        dinoType = DINO_TYPE;
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
        setBehaviours();
    }

    private void setBehaviours() {
        actionFactories.add(new HungryBehaviour(Fruit.class));
        actionFactories.add(new HornyBehaviour());
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
            map.locationOf(this).addItem(new Corpse(Corpse.Type.STEGOSAUR));
            map.removeActor(this);
        }
    }

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    @Override
    public void eat(Item food, int quantity) {
        final int FRUIT_HEAL = 10;
        if (food.getClass() == Fruit.class) {
            for (int i = 0; i < quantity; i++) {
                heal(FRUIT_HEAL);
            }
        }
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * resets pregnant tick to stegosaur's maximum pregnant tick
     */
    @Override
    public void resetPregnantTick() {
        this.pregnantTick = PREGNANT_TICK;
    }

    /**
     * determines the highest priority behaviour based on probability
     *
     * @param map gamemap the actor is on
     * @return action to be performed this playturn
     */
    private Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints >= 90 && hitPoints <= 100) {
            //wander behaviour or horny behaviour
            if (Math.random() < 0.4) {
                action = actionFactories.get(HORNY_BEHAVIOUR).getAction(this, map);
            } else {
                action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
            }
        } else if (hitPoints >= 50 && hitPoints < 90) {
            //hungry behaviour or horny behaviour
            if (Math.random() <= 0.4) {
                action = actionFactories.get(HORNY_BEHAVIOUR).getAction(this, map);
            } else if (Math.random() <= 0.5) {
                action = actionFactories.get(HUNGRY_BEHAVIOUR).getAction(this, map);
            } else {
                action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
            }
        } else if (hitPoints < 50) {
            //hungry behaviour
            action = actionFactories.get(HUNGRY_BEHAVIOUR).getAction(this, map);
        } else {
            action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
        }

        return action;
    }

    /**
     * Figure out what to do next.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action action = super.playTurn(actions, lastAction, map, display);

        if (action == null && lastAction.getNextAction() != null) {
            action = lastAction.getNextAction();
        } else if (action == null) {
            action = determineBehaviour(map);
            if (action == null) {
                action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
            }
        }
        return action;
    }
}
