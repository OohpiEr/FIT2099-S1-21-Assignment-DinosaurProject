package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.BabyDino;

public class GrowUpAction extends Action {


    @Override
    public String execute(Actor actor, GameMap map) {

        if (actor instanceof BabyDino){
            ((BabyDino) actor).growUp(map);
        }

        return actor + " at (" + map.locationOf(actor).x() + ", " + map.locationOf(actor).y() + ") grew up";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
