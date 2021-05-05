package game.items;

import game.dinosaurs.DinosaurEnumType;

/**
 * A class that represents a dinosaur corpse. Can currently represent a Stegosaur, Branchiosaur or ALlosaur corpse with an enum attribute 'type' to represent which.
 *
 * @see game.dinosaurs.Dinosaur
 */
public class Corpse extends PortableItem {

    /**
     * Returns the type of corpse this is
     * @return  The type of corpse this is
     */
    public DinosaurEnumType getType() {
        return type;
    }

    DinosaurEnumType type;

    /**
     * Constructor. Creates a new Corpse instance based on what dinosaur it is
     *
     * @param type The type of dinosaur corpse
     */
    public Corpse(DinosaurEnumType type) {
        super(type.getName() + " corpse", 'C');
        this.type = type;
    }
}
