package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Util;
import game.actions.EatAction;
import game.actions.LayEggAction;
import game.behaviours.HornyBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;
import game.grounds.Lake;
import game.grounds.Tree;
import game.items.CarnivoreMealKit;
import game.items.Corpse;
import game.items.Egg;
import game.items.Fish;

import java.util.HashMap;

/**
 * A class that represents an adult Pterodactyl
 */
public class Pterodactyl extends AdultDino {

    private static final DinosaurEnumType DINO_TYPE = DinosaurEnumType.PTERODACTYL;
    private static final int STARTING_HITPOINTS = 30;
    private static final int MAX_HITPOINTS = 60;
    private static final int STARTING_WATER_LEVEL = 60;
    private static final int MAX_WATER_LEVEL = 100;
    private static final int THIRSTY_THRESHOLD = 40;
    private static final String NAME = "Pterodactyl";
    private static final char DISPLAY_CHAR = 'P';
    private static final int MAX_PREGNANT_TICK = 10;
    public static final int HUNGRY_THRESHOLD = 50;
    private static final Class<?>[] FOOD = {Corpse.class, CarnivoreMealKit.class, Fish.class};
    private static final HashMap<Class<?>, Class<?>[]> FROM_THESE_EATS_THESE = new HashMap<>() {{
        put(Ground.class, new Class[]{Corpse.class});
    }};
    private static final int MAX_FLIGHT_TIME = 30;
    private boolean flying = true;
    private int flightTimeCounter = MAX_FLIGHT_TIME;

    /**
     * Constructor.
     * All Pterodactyls are represented by a 'P' and have 60 max hit points.
     *
     * @param name     the name of the Actor
     * @param isFemale whether the dinosaur is female
     */
    public Pterodactyl(String name, boolean isFemale) {
        super(name, DISPLAY_CHAR, STARTING_HITPOINTS, DINO_TYPE, MAX_HITPOINTS, HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, THIRSTY_THRESHOLD, FOOD, FROM_THESE_EATS_THESE, isFemale, MAX_PREGNANT_TICK);
        setBehaviours();
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Pterodactyl() {
        super(NAME, DISPLAY_CHAR, STARTING_HITPOINTS, DINO_TYPE, MAX_HITPOINTS, HUNGRY_THRESHOLD, STARTING_WATER_LEVEL, MAX_WATER_LEVEL, THIRSTY_THRESHOLD, FOOD, FROM_THESE_EATS_THESE, false, MAX_PREGNANT_TICK);
        this.setFemale(Math.random() < 0.5);
        setBehaviours();
    }

    @Override
    public void eat(Item food, int quantity) {
        final int CORPSE_HEAL = 10;
        final int CARNIVORE_MEAL_KIT_HEAL = maxHitpoints;
        final int FISH_HEAL = 5;
        final int EGG_HEAL = 10;
        if (food.getClass() == Corpse.class) {
            for (int i = 0; i < quantity; i++) {
                ((Corpse) food).eat(CORPSE_HEAL);
            }
        } else if (food.getClass() == CarnivoreMealKit.class) {
            for (int i = 0; i < quantity; i++) {
                heal(CARNIVORE_MEAL_KIT_HEAL);
            }
        } else if (food.getClass() == Fish.class) {
            for (int i = 0; i < quantity; i++) {
                heal(FISH_HEAL);
            }
        } else if (food.getClass() == Egg.class) {
            for (int i = 0; i < quantity; i++) {
                heal(EGG_HEAL);
            }
        }
    }

    @Override
    public void drink(int sips) {
        adjustWaterLevel(30);
    }

    public boolean isFlying() {
        return flying;
    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        Action action = null;

        if (hitPoints >= 50 && hitPoints <= 60) {
            //wander behaviour or horny behaviour
            if (!isPregnant() && Math.random() < 0.4) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            }
        } else if (hitPoints >= 30 && hitPoints < 50) {
            if (!isPregnant() && Math.random() <= 0.5) {
                action = getBehaviourAction(HornyBehaviour.class, map);
            } else if (Math.random() <= 0.5) {
                action = getBehaviourAction(HungryBehaviour.class, map);
            }
        } else if (hitPoints < 30) {
            //hungry behaviour
            action = getBehaviourAction(HungryBehaviour.class, map);
        } else {
            action = getBehaviourAction(WanderBehaviour.class, map);
        }

        return action;
    }

    /**
     * Inform a Dino the passage of time.
     * This method is called once per turn
     * Actions that depend on time/number of turns will be returned if conditions are met
     * This implementation in particular checks if the Pterodactyl is ready to lay an egg. If it is, it seeks out a Tree to lay it on
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
            if (map.locationOf(this).getGround() instanceof Tree) {
                return new LayEggAction();
            } else {
                Location closestTree = null;
                Location here = map.locationOf(this);
                for (int x : map.getXRange()) {
                    for (int y : map.getYRange()) {
                        Location there = map.at(x, y);
                        if (there.getGround() instanceof Tree && !there.containsAnActor()) {
                            if (closestTree == null || Util.distance(closestTree, here) > Util.distance(there, here)) {
                                closestTree = there;
                            }
                        }
                    }
                }
                for (Exit exit : here.getExits()) {
                    Location there = exit.getDestination();
                    if (there.canActorEnter(this) && Util.distance(there, closestTree) < Util.distance(here, closestTree)) {
                        return new MoveActorAction(there, exit.getName());
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location actorLocation = map.locationOf(this);
        if (actorLocation.getGround() instanceof Lake && hitPoints < maxHitpoints) {
            eat(new Fish(), ((Lake) actorLocation.getGround()).eatFish((int) (Math.round(Math.random() * 3))));
        }
        if (flightTimeCounter > 0) {
            flightTimeCounter--;
        }
        if (actorLocation.getGround() instanceof Tree) {
            flightTimeCounter = MAX_FLIGHT_TIME;
        } else {
            for (Item item : actorLocation.getItems()) {
                if (item.getClass() == Corpse.class) {
                    flightTimeCounter = MAX_FLIGHT_TIME;
                }
            }
        }
        if (flightTimeCounter <= 0) {
            flying = false;
        } else {
            flying = true;
        }
        if (super.playTurn(actions, lastAction, map, display) instanceof EatAction) {
            flying = false;
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}
