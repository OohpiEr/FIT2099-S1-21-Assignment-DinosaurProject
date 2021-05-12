package game.grounds;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.dinosaurs.BabyPterodactyl;
import game.dinosaurs.Pterodactyl;

/**
 * A class that represents a Lake
 */
public class Lake extends Ground {

    private static final int MAX_SIPS = 25;

    private int sips;
    private int maxSips;
    private static Lake controllerLake;
    private static final int DEFAULT_RAIN_TIMER = 10;
    private static int rainTimer;
    private static double rainfall = 0;

    /**
     * Constructor.
     */
    public Lake() {
        super('~');
        maxSips = MAX_SIPS;
        sips = maxSips;
        rainTimer = DEFAULT_RAIN_TIMER;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor instanceof Pterodactyl || actor instanceof BabyPterodactyl){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lets the lake experience the passage of time. Every 10 turns, there is a 20% chance that it rains, refilling the sips of all lakes by a random amount, up to its maxSips
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if(controllerLake==null){
            controllerLake = this;
        }
        if(controllerLake == this){
            rainfall = 0;
            rainTimer--;
            if (rainTimer<=0){
                rainTimer = DEFAULT_RAIN_TIMER;
                if (Math.random()<0.2){
                    rainfall = 0.1 + 0.5*Math.random();
                }
            }
        }
        sips += 20*rainfall;
        if (sips>maxSips){
            sips = maxSips;
        }
    }
}
