package game.actions;

import edu.monash.fit2099.engine.*;
import game.behaviours.FollowBehaviour;

public class FollowAction extends MoveActorAction {

    private Actor actor;
    private Actor target;
    private GameMap map;

    public FollowAction(Location moveToLocation, String direction, Actor actor, Actor target, GameMap map) {
        super(moveToLocation, direction);
        this.target = target;
        this.actor = actor;
        this.map = map;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return super.execute(actor, map);
    }

    @Override
    public String menuDescription(Actor actor) {
        return super.menuDescription(actor);
    }

    @Override
    public Action getNextAction() {
        Action action = new FollowBehaviour(target).getAction(actor, map);

        if (action == null)
            action = new BreedAction(actor,target);

        return action;
    }
}
