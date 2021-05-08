package game.actions;

import edu.monash.fit2099.engine.*;
import game.behaviours.FollowBehaviour;
import game.dinosaurs.AdultDino;

/**
 * Moves an actor towards a target and does an action after following
 */
public class FollowAction extends MoveActorAction {

    private Actor actor;
    private Actor target;
    private GameMap map;
    private Action finalAction;

    /**
     * Constructor
     *
     * @param moveToLocation the destination of the move
     * @param direction      the direction of the move (to be displayed in menu)
     * @param actor          actor following target
     * @param target         the target
     * @param finalAction    the action to take after following is done
     * @param map            the map the actor is on
     */
    public FollowAction(Location moveToLocation, String direction, Actor actor, Actor target, Action finalAction, GameMap map) {
        super(moveToLocation, direction);
        this.target = target;
        this.actor = actor;
        this.map = map;
        this.finalAction = finalAction;
    }

    /**
     * Allow the Actor to be moved.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of the Action suitable for the menu
     * @see Action#execute(Actor, GameMap)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return super.execute(actor, map);
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player moves east"
     */
    @Override
    public String menuDescription(Actor actor) {
        return super.menuDescription(actor);
    }


    /**
     * Returns next action to be performed after current action
     *
     * @return null
     */
    @Override
    public Action getNextAction() {
        Action action = new FollowBehaviour(target).getAction(actor, map);

        if (action == null && target instanceof AdultDino)
            action = new BreedAction((AdultDino) target);

        return action;
    }
}
