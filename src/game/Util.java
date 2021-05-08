package game;

import edu.monash.fit2099.engine.Location;

import java.util.Random;

public final class Util {

    /**
     * Compute the Manhattan distance between two locations.
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    public static int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Get a random integer between 2 integers (inclusive)
     * @param lowerBound lowerbound (inclusive)
     * @param upperBound upperbound (inclusive)
     * @return random number as an integer
     */
    public static int random(int lowerBound, int upperBound){
        int randNum = (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
        return randNum;
    }

}
