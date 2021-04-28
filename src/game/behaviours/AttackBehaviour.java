package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Allosaur;
import game.actions.AttackAction;
import game.dinosaurs.Stegosaur;

import java.util.ArrayList;

public class AttackBehaviour implements Behaviour {
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Action attackAction = null;
        Actor stegosaur = getNearbyStegosaur(actor, map);
        Boolean isOffLimits = false;

        ArrayList<Stegosaur> offLimitsStegosaurs = ((Allosaur) actor).getOffLimitsStegosaurs();
        for (Stegosaur offLimitsStegosaur : offLimitsStegosaurs) {
            if (stegosaur == offLimitsStegosaur) {
                isOffLimits = true;
            }
        }

        if (!isOffLimits) {
            attackAction = new AttackAction(stegosaur);
            //TODO attackAction might miss
            actor.heal(20);
            ((Allosaur) actor).addOffLimitsStegosaurs((Stegosaur) stegosaur);
        }

        return attackAction;
    }

    public Actor getNearbyStegosaur(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getActor().getClass() == Stegosaur.class) {
                return destination.getActor();
            }
        }

        return null;

    }
}
