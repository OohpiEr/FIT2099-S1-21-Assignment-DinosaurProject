package game.items;

import edu.monash.fit2099.engine.Location;
import game.dinosaurs.DinosaurEnumType;

/**
 * A class that represents a dinosaur corpse. Can currently represent a Stegosaur, Branchiosaur or ALlosaur corpse with an enum attribute 'type' to represent which.
 *
 * @see game.dinosaurs.Dinosaur
 */
public class Corpse extends PortableItem {

    private static final int STEGOSAUR_CORPSE_FOODPOINTS = 60;
    private static final int ALLOSAUR_CORPSE_FOODPOINTS = 60;
    private static final int BRACHIOSAUR_CORPSE_FOODPOINTS = 100;

    private int foodPoints;
    DinosaurEnumType type;

    /**
     * Constructor. Creates a new Corpse instance based on what dinosaur it is
     *
     * @param type The type of dinosaur corpse
     */
    public Corpse(DinosaurEnumType type) {
        super(type.getName() + " corpse", '%');
        this.type = type;
        switch (type){
            case STEGOSAUR: foodPoints = STEGOSAUR_CORPSE_FOODPOINTS;
            case ALLOSAUR: foodPoints = ALLOSAUR_CORPSE_FOODPOINTS;
            case BRANCHIOSAUR: foodPoints = BRACHIOSAUR_CORPSE_FOODPOINTS;
        }
    }

    /**
     * Returns the type of corpse this is
     * @return  The type of corpse this is
     */
    public DinosaurEnumType getType() {
        return type;
    }

    /**
     * Allows an amount of foodPoints to be eaten from this corpse, up to a maximum of the food points it has left
     * @param amount    The amount of food points attempted to be eaten
     * @return  The amount of food points eaten
     */
    public int eat(int amount){
        if (foodPoints>=amount){
            foodPoints -= amount;
            return amount;
        } else {
            int points = foodPoints;
            foodPoints = 0;
            return points;
        }
    }

    /**
     * Check if the Corpse has been fully eaten. Removes it from the ground if so
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        if (foodPoints<=0){
            currentLocation.removeItem(this);
        }
    }
}
