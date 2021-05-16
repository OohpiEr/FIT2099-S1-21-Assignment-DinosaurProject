package game.items;

public class Fish extends PortableItem{

    private static final String NAME = "fish";
    private static final char DISPLAY_CHAR = 'f';

    /**
     * Constructor.
     *
     * @param name        The name of the item
     * @param displayChar The item's display character
     */
    public Fish(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * Constructor using default values
     */
    public Fish(){
        super(NAME, DISPLAY_CHAR);
    }
}
