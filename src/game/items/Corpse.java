package game.items;

/**
 * A class that represents a dinosaur corpse. Can currently represent a Stegosaur, Branchiosaur or ALlosaur corpse with an enum attribute 'type' to represent which.
 */
public class Corpse extends PortableItem{

    private enum Type {
        Stegosaur ("Stegosaur"),
        Branchiosaur ("Branchiosaur"),
        Allosaur ("Allosaur");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    Type type;

    /**
     * Constructor. Creates a new Corpse instance based on what dinosaur it is
     * @param type The type of dinosaur corpse
     */
    public Corpse(Type type) {
        super(type.getName()+" corpse", 'C');
        this.type = type;
    }
}
