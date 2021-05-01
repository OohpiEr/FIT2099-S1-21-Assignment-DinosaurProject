package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.items.Corpse;
import game.items.Fruit;
import game.behaviours.AttackBehaviour;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;

import java.util.ArrayList;

/**
 * A carnivorous dinosaur
 */
public class Allosaur extends Dinosaur {
    private ArrayList<Stegosaur> offLimitsStegosaurs = new ArrayList<Stegosaur>();
    private final int STARTING_HITPOINTS = 100;
    private final int MAX_HITPOINTS = 100;
    private final String NAME = "Allosaur";
    private final char DISPLAY_CHAR = 'A';

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Allosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        //TODO change char + initialise proper HP
        //TODO why is the maxhitpoint the initial hp
        super(name, 'A', 100, isFemale);
        actionFactories.add(new HungryBehaviour(Fruit.class));
        actionFactories.add(new WanderBehaviour());
        actionFactories.add(new AttackBehaviour());

        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Allosaur() {
        super("Allosaur", 'A', 100, false);
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Provides default values for name and displayChar. Randomises gender
     *
     * @param hitPoints the Allosaur's starting hitpoints
     */
    public Allosaur(int hitPoints) {
        super("Allosaur", 'A', hitPoints, false);
        this.setFemale(Math.random() < 0.5);
        name = NAME;
        displayChar = DISPLAY_CHAR;
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Checks if the Allosaur is dead, and places an Allosaur corpse on its location in its place if it is
     *
     * @param map the GameMap the dinosaur is in
     * @see Corpse
     */
    @Override
    public void checkDead(GameMap map) {
        if (hitPoints <= 0) {
            map.locationOf(this).addItem(new Corpse(Corpse.Type.Allosaur));
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
        final int ALLOSAUR_CORPSE_HEAL = 50;
        final int STEGOSAUR_CORPSE_HEAL = 50;
        final int BRACHIOSAUR_CORPSE_HEAL = this.maxHitPoints;
        if(food.getClass()==Corpse.class){
            for(int i=0;i<quantity;i++){
                if(((Corpse) food).getType()==Corpse.Type.Stegosaur){
                    heal(STEGOSAUR_CORPSE_HEAL);
                }
                else if(((Corpse) food).getType()==Corpse.Type.Allosaur){
                    heal(ALLOSAUR_CORPSE_HEAL);
                }
                else if(((Corpse) food).getType()==Corpse.Type.Branchiosaur){
                    heal(BRACHIOSAUR_CORPSE_HEAL);
                }
            }
        }
    }

    public ArrayList<Stegosaur> getOffLimitsStegosaurs() {
        return offLimitsStegosaurs;
    }

    public void addOffLimitsStegosaurs(Stegosaur stegosaur) {
        this.offLimitsStegosaurs.add(stegosaur);
    }

    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(20, "bites a chunk off");
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    public boolean nearStegosaur(GameMap map) {
        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getActor().getClass() == Stegosaur.class) {
                return true;
            }
        }

        return false;
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
        super.playTurn(actions, lastAction, map, display);
        Action action = null;
        if (nearStegosaur(map)) {
            //attack behaviour
/*            Action attackAction = new AttackAction(stegosaur);
            attackAction.execute(this, map);
            this.hitPoints += 20;*/
            action = actionFactories.get(3).getAction(this, map);
        } else if (hitPoints > 50) {
            //horny behaviour
            action = actionFactories.get(2).getAction(this, map);
        } else if (hitPoints < 90) {
            //hungry behaviour
            action = actionFactories.get(0).getAction(this, map);
        } else {
            //wander behaviour
            action = actionFactories.get(1).getAction(this, map);
        }

        if (action != null)
            return action;
/*        for (Behaviour factory : actionFactories) {
            Action action = factory.getAction(this, map);
            if (action != null)
                return action;
        }*/
        return new DoNothingAction();
    }
}
