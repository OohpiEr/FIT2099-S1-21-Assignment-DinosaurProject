package game.items;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A class representing a laser gun
 */
public class LaserGun extends WeaponItem {
    /**
     * Constructor. Provides fixed values for the name, displayChar, damage and verb for the parent constructor
     *
     */
    public LaserGun() {
        super("Laser Gun", '>', 50, "zaps");
    }
}
