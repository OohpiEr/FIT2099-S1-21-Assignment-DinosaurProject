package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Fruit;
import game.behaviours.HungryBehaviour;
import game.behaviours.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {

    // Will need to change this to a collection if Stegosaur gets additional Behaviours.


    /**
     * Constructor.
     * All Stegosaurs are represented by a 'd' and have 100 hit points.
     *
     * @param name the name of the Actor
     */
    public Stegosaur(String name) {
        //TODO change char + initialise proper HP
        //TODO why is the maxhitpoint the initial hp
        super(name, 'S', 100);
        actionFactories.add(new HungryBehaviour(Fruit.class));
        actionFactories.add(new WanderBehaviour());
        //TODO horny behaviour
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
/*	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Action wander = behaviour.getAction(this, map);
		if (wander != null)
			return wander;

		return new DoNothingAction();
	}*/
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        super.playTurn(actions, lastAction, map, display);
        Action action;
        if (hitPoints < 90){
            //hungry behaviour
            action = actionFactories.get(0).getAction(this, map);
        } else if (hitPoints > 50){
            //horny behaviour
            action = actionFactories.get(2).getAction(this, map);
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
