package game;

import edu.monash.fit2099.engine.Location;

public final class Util {

    /**
     * Compute the Manhattan distance between two locations.
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    public int getDistance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
