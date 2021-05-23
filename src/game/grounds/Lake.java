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
    private int numFish = 5;
    private final static int MAX_FISH = 25;
    private final String NAME = "lake";

    /**
     * Constructor.
     */
    public Lake() {
        super('~');
        maxSips = MAX_SIPS;
        sips = maxSips;
        rainTimer = DEFAULT_RAIN_TIMER;
    }

    public static double getRainfall() {
        return rainfall;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor instanceof Pterodactyl) {
            return ((Pterodactyl) actor).isFlying();
        } else if (actor instanceof BabyPterodactyl){
            return ((BabyPterodactyl) actor).isFlying();
        }
        else {
            return false;
        }
    }

    /**
     * Lets the lake experience the passage of time. Every 10 turns, there is a 20% chance that it rains, refilling the sips of all lakes by a random amount, up to its maxSips.
     * There is also a 60% chance each turn for a new fish to be born in the lake.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        if (controllerLake == null) {
            controllerLake = this;
        }
        if (controllerLake == this) {
            rainfall = 0;
            rainTimer--;
            if (rainTimer <= 0) {
                rainTimer = DEFAULT_RAIN_TIMER;
                if (Math.random() <= 0.2) {
                    rainfall = 0.1 + (0.5 * Math.random());
                }
            }
        }
        adjustSips((int) Math.round(20 * rainfall));

        if (Math.random() < 0.6) {
            numFish++;
            if (numFish > MAX_FISH) {
                numFish = MAX_FISH;
            }
        }
    }

    /**
     * Returns whether the lake has fish
     *
     * @return  True if the lake has fish, False otherwise
     */
    public boolean hasFish(){
        if(numFish>0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Eats a fish from the lake if there are fish in the lake.
     *
     * @param quantity The number of fishes to be eaten from the Lake
     * @return  The number of fishes actually eaten
     */
    public int eatFish(int quantity){
        if (numFish>=quantity){
            numFish-=quantity;
            return quantity;
        } else {
            int fish = numFish;
            numFish = 0;
            return fish;
        }

    }

    /**
     * Adjusts the Lake's sips by the provided amount. The Lake's sips cannot exceed its maximum capacity of sips
     *
     * @param sips The number of sips to adjust the Lake's sips by
     */
    public void adjustSips(int sips) {
        this.sips += sips;
        if (this.sips > maxSips) {
            this.sips = maxSips;
        }
    }

    @Override
    public String toString() {
        return NAME;
    }
}
