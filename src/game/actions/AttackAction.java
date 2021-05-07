package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.*;
import game.items.Corpse;
import game.items.PortableItem;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    private Actor target;
    /**
     * The Actor that is to be attacked
     */
    private Actor attacker;
    /**
     * corpse of target if they die during attack
     */
    private Item corpse;
    /**
     * location of the corpse of target if they die during attack
     */
    private Location corpseLocation;
    /**
     * Random number generator
     */
    private Random rand = new Random();


    /**
     * Constructor.
     *
     * @param target The Actor that is to be attacked
     */
    public AttackAction(Actor target) {
        this.target = target;
    }

    /**
     * Constructor.
     *
     * @param target The Actor that is to be attacked
     */
    public AttackAction(Actor attacker, Actor target) {
        this.target = target;
        this.attacker = attacker;
    }



    /**
     * setter for corpse of target
     *
     * @param corpse
     */
    private void setCorpse(Item corpse) {
        this.corpse = corpse;
    }

    /**
     * setter for location of target corpse
     *
     * @param corpseLocation
     */
    public void setCorpseLocation(Location corpseLocation) {
        this.corpseLocation = corpseLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        Weapon weapon = actor.getWeapon();

/* todo rmb to uncomment
		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}
*/

        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

        target.hurt(damage);

        if (actor instanceof Allosaur && target instanceof Stegosaur) {
            actor.heal(20);
        }


        if (!target.isConscious()) {
            if (actor instanceof Dinosaur){
                corpse = new Corpse(((Dinosaur) actor).getDinoType());
            } else {
                corpse = new PortableItem("dead " + target, '%');
            }
            setCorpse(corpse);
            setCorpseLocation(map.locationOf(target));
            map.locationOf(target).addItem(corpse);

            Actions dropActions = new Actions();
            for (Item item : target.getInventory())
                dropActions.add(item.getDropAction());
            for (Action drop : dropActions)
                drop.execute(target, map);
            map.removeActor(target);

            result += System.lineSeparator() + target + " is killed.";
        }

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target;
    }

    @Override
    public Action getNextAction() {
        if (corpse != null && (attacker instanceof Allosaur || attacker instanceof BabyAllosaur) ) {
            return new EatAction(corpse, corpseLocation, 1);
        }
        return null;
    }
}
