package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.behaviours.HornyBehaviour;
import game.items.CarnivoreMealKit;
import game.items.Corpse;
import game.behaviours.AttackBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.Egg;
import game.items.VegetarianMealKit;

import java.util.HashMap;

/**
 * A carnivorous dinosaur
 */
public class Allosaur extends AdultDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.ALLOSAUR;
    private static final int STARTING_HITPOINTS = 100;
    private static final int MAX_HITPOINTS = 100;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final int THIRSTY_THRESHOLD = 40;
    private static final String NAME = "Allosaur";
    private static final char DISPLAY_CHAR = 'A';
    private static final int MAX_PREGNANT_TICK = 20;
    public static final int HUNGRY_THRESHOLD = 90;
    private static final Class<?>[] FOOD = {Corpse.class, Egg.class, CarnivoreMealKit.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Ground.class, new Class[]{Corpse.class, Egg.class});
    }};

    /**
     * Constructor.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Allosaur(String name, boolean isFemale) {
        super(name, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE ,isFemale, MAX_PREGNANT_TICK);
        setBehaviours();
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Allosaur() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE , false, MAX_PREGNANT_TICK);
        this.setFemale(Math.random() < 0.5);
    }

    /**
     * Constructor. Provides default values for name and displayChar. Randomises gender
     *
     * @param hitPoints the Allosaur's starting hitpoints
     */
    public Allosaur(int hitPoints) {
        super(NAME, DISPLAY_CHAR, hitPoints,DINO_TYPE,MAX_HITPOINTS,HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL,THIRSTY_THRESHOLD, FOOD,FROM_THESE_EATS_THESE , false, MAX_PREGNANT_TICK);
        this.setFemale(Math.random() < 0.5);
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
        final int CARNIVORE_MEAL_KIT_HEAL = maxHitPoints;
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
     * Creates and returns an intrinsic weapon for Allosaur.
     *
     * @return returns an intrinsic weapon for Allosaur.
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(20, "bites a chunk off");
    }

    /**
     * Check if there are any targets in the dinosaur's exits
     * @param map   The map the dinosaur is in
     * @return  True if there are any targets in the dinosaur's exits, False otherwise
     */
    private boolean isTargetInExits(GameMap map) {
        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor() &&
                    (destination.getActor() instanceof Stegosaur || destination.getActor() instanceof BabyStegosaur || (destination.getActor() instanceof Pterodactyl || destination.getActor() instanceof BabyPterodactyl))) {
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

        if (isTargetInExits(map)) {
            //attack behaviour
            action = getBehaviourAction(AttackBehaviour.class, map);
        } else if (hitPoints >= 90 && hitPoints <= 100) {
            //wander behaviour or horny behaviour
            if (!isPregnant() && Math.random() < 0.4) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else {
                action = getBehaviourAction(WanderBehaviour.class, map);
            }
        } else if (hitPoints >= 50 && hitPoints < 90) {
            //hungry, horny or wander behaviour
            if (!isPregnant() && Math.random() <= 0.4) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else if (Math.random() <= 0.5) {
                action = getBehaviourAction(HungryBehaviour.class, map);
            } else {
                action = getBehaviourAction(WanderBehaviour.class, map);
            }
        } else if (hitPoints < 50) {
            //hungry behaviour
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

}
