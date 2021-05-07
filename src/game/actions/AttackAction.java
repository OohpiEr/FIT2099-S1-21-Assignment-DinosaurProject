package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.items.PortableItem;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;
    /**
     * The Actor that is attacking
     */
    protected Actor attacker;
    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param attacker the Actor that is attacking
     * @param target   The Actor that is to be attacked
     */
    public AttackAction(Actor attacker, Actor target) {
        this.attacker = attacker;
        this.target = target;
    }

    /**
     * Constructor.
     *
     * @param target The Actor that is to be attacked
     */
    public AttackAction(Actor target) {
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

        Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

        target.hurt(damage);

        if (actor instanceof Allosaur && target instanceof Stegosaur) {
            actor.heal(20);
        }


        if (!target.isConscious()) {
            Item corpse = new PortableItem("dead " + target, '%');
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
}
