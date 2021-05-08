package game.dinosaurs;

/**
 * A class that details the type of dinosaurs that exist
 */
public enum DinosaurEnumType {
    STEGOSAUR("Stegosaur"),
    BRANCHIOSAUR("Branchiosaur"),
    ALLOSAUR("Allosaur");

    private String name;

    /**
     * Constructor.
     *
     * @param name  The dinosaur type's name
     */
    DinosaurEnumType(String name) {
        this.name = name;
    }

    /**
     * Gets the dinosaur type's name
     *
     * @return The dinosaur type's name
     */
    public String getName() {
        return name;
    }
}
