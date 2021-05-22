package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Bush;
import game.grounds.Tree;
import game.items.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A baby Allosaur, a carnivorous dinosaur
 */
public class BabyAllosaur extends BabyDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.ALLOSAUR;
    private static final int STARTING_HITPOINTS = 20;
    private static final int MAX_HITPOINTS = 100;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final int THIRSTY_THRESHOLD = 40;
    private static final int ALLOSAUR_GROW_UP_TICK = 20;
    public static final int HUNGRY_THRESHOLD = 90;
    private static final String NAME = "Baby Allosaur";
    private static final char DISPLAY_CHAR = 'a';

    private static final Class<?>[] FOOD = {Corpse.class, Egg.class, CarnivoreMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Ground.class, new Class[]{Corpse.class, Egg.class});
    }};

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public BabyAllosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD ,FOOD,FROM_THESE_EATS_THESE , ALLOSAUR_GROW_UP_TICK);
    }


    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyAllosaur() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD ,FOOD,FROM_THESE_EATS_THESE , ALLOSAUR_GROW_UP_TICK);
    }

    /**
     * sets dinosaur behaviours
     */
    @Override
    protected void setBehaviours() {
        super.setBehaviours();
        actionFactories.add(new AttackBehaviour());
    }

    /**
     * Creates and returns an intrinsic weapon.
     *
     * @return returns an intrinsic weapon for BabyAllosaur.
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(10, "bites a chunk off");
    }


    /**
     * Used to let the dinosaur eat a quantity of a food Item. Adjusts hitpoints according to the food provided
     *
     * @param food     The Item eaten
     * @param quantity The quantity of the food eaten
     */
    @Override
    public void eat(Item food, int quantity) {
        final int ALLOSAUR_CORPSE_HEAL = 50;
        final int STEGOSAUR_CORPSE_HEAL = 50;
        final int BRACHIOSAUR_CORPSE_HEAL = this.maxHitPoints;
        final int CARNIVORE_MEAL_KIT_HEAL = maxHitpoints;
        final int EGG_HEAL = 10;
        if (food.getClass() == Corpse.class) {
            for (int i = 0; i < quantity; i++) {
                switch (((Corpse) food).getType()){
                    case STEGOSAUR: heal(((Corpse) food).eat(STEGOSAUR_CORPSE_HEAL));
                    case ALLOSAUR: heal(((Corpse) food).eat(ALLOSAUR_CORPSE_HEAL));
                    case BRANCHIOSAUR: heal(((Corpse) food).eat(BRACHIOSAUR_CORPSE_HEAL));
                }
            }
        } else if (food.getClass() == CarnivoreMealKit.class){
            for (int i = 0; i < quantity; i++) {
                heal(CARNIVORE_MEAL_KIT_HEAL);
            }
        } else if (food.getClass() == Egg.class){
            for (int i = 0; i < quantity; i++) {
                heal(EGG_HEAL);
            }
        }
    }

    @Override
    public void drink(int sips) {
        adjustWaterLevel(30);
    }

    /**
     * Checks if there is a stegosaur or a baby stegosaur in the exits
     *
     * @param map the map the current dinosaur is on
     * @return true if there is a stegosaur or a baby stegosaur in the exits
     */
    private boolean isStegosaurInExits(GameMap map) {
        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() && (destination.getActor() instanceof Stegosaur || destination.getActor() instanceof BabyStegosaur)) {
                return true;

            }
        }

        return false;
    }

    /**
     * determines the highest priority behaviour based on probability
     *
     * @param map gamemap the actor is on
     * @return action to be performed this playturn
     */
    @Override
    protected Action determineBehaviour(GameMap map) {
        Action action = null;

        if (isStegosaurInExits(map)) {
            //attack behaviour
            action = getBehaviourAction(AttackBehaviour.class, map);
        } else if (hitPoints <= 90) {
            //hungry behaviour
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

    /**
     * replaces baby allosaur with new instance of Allosaur
     *
     * @param map the map the actor is on
     */
    @Override
    public void growUp(GameMap map) {
        Location actorLocation = map.locationOf(this);
        map.removeActor(this);
        map.addActor(new Allosaur(), actorLocation);
    }
}
