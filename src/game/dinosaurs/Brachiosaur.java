package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.grounds.Bush;
import game.grounds.Tree;
import game.items.Corpse;
import game.items.Fruit;

import java.util.HashMap;
import java.util.Map;

/**
 * A herbivorous dinosaur
 */
public class Brachiosaur extends AdultDino {

    private final DinosaurEnumType DINO_TYPE = DinosaurEnumType.BRANCHIOSAUR;
    private final int STARTING_HITPOINTS = 100;
    private final int MAX_HITPOINTS = 160;
    private final String NAME = "Brachiosaur";
    private final char DISPLAY_CHAR = 'B';
    private final int PREGNANT_TICK = 30;

    private final int HUNGRY_BEHAVIOUR = 1;
    private final int HORNY_BEHAVIOUR = 2;

    private final Class<?>[] FOOD = {Fruit.class};
    private final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>(){{
        put(Tree.class, new Class[]{Fruit.class});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Brachiosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale, DinosaurEnumType.BRANCHIOSAUR);
        maxHitPoints = MAX_HITPOINTS;
        pregnantTick = PREGNANT_TICK;
        setBehaviours();
    }

    /**
     * Constructor.
     *TODO THIS IS TEMP - FOR TESTING
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Brachiosaur(String name, boolean isFemale) {
        super(name, 'B', 100, isFemale, DinosaurEnumType.STEGOSAUR);
        dinoType = DINO_TYPE;
        maxHitPoints = MAX_HITPOINTS;
        hitPoints = STARTING_HITPOINTS;
        setBehaviours();
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Brachiosaur() {
        super("Brachiosaur", 'B', 100, false, DinosaurEnumType.BRANCHIOSAUR);
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
        this.pregnantTick = PREGNANT_TICK;
        setBehaviours();
    }

    private void setBehaviours() {
        actionFactories.add(new HungryBehaviour(FOOD, FROM_THESE_EATS_THESE));
        actionFactories.add(new HornyBehaviour());
    }

    /**
     * Checks if the Brachiosaur is dead, and places a Brachiosaur corpse on its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    @Override
    public void checkDead(GameMap map) {
        if (hitPoints <= 0) {
            map.locationOf(this).addItem(new Corpse(DinosaurEnumType.BRANCHIOSAUR));
            map.removeActor(this);
        }
    }

    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     * @param food      The Item eaten
     * @param quantity  The quantity of the food eaten
     */
    @Override
    public void eat(Item food, int quantity) {
        final int FRUIT_HEAL = 5;
        if(food.getClass()==Fruit.class){
            for(int i=0;i<quantity;i++){
                heal(FRUIT_HEAL);
            }
        }
    }

    /**
     * resets pregnant tick to Brachiosaur's maximum pregnant tick
     */
    @Override
    public void resetPregnantTick() {
        this.pregnantTick = PREGNANT_TICK;
    }

    private Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints >= 140 && hitPoints <= MAX_HITPOINTS) {
            //wander behaviour or horny behaviour
            if (Math.random() < 0.4) {
                action = actionFactories.get(HORNY_BEHAVIOUR).getAction(this, map);
            } else {
                action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
            }
        } else if (hitPoints >= 100 && hitPoints < 140) {
            //hungry behaviour or horny behaviour
            if (Math.random() <= 0.2) {
                action = actionFactories.get(HORNY_BEHAVIOUR).getAction(this, map);
            } else if (Math.random() <= 0.7) {
                action = actionFactories.get(HUNGRY_BEHAVIOUR).getAction(this, map);
            } else {
                action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
            }
        } else if (hitPoints < 100) {
            //hungry behaviour
            action = actionFactories.get(HUNGRY_BEHAVIOUR).getAction(this, map);
        } else {
            action = actionFactories.get(WANDER_BEHAVIOUR).getAction(this, map);
        }

        return action;
    }

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
