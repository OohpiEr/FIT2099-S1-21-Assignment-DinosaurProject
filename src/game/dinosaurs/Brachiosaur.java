package game.dinosaurs;

public class Brachiosaur extends Dinosaur{

    private final int STARTING_HITPOINTS = 100;
    private final int MAX_HITPOINTS = 160;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public Brachiosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
        maxHitPoints = MAX_HITPOINTS;
    }

    /**
     * Constructor. Provides default values for name, displayChar and hitPoints. Randomises gender
     */
    public Brachiosaur(){
        super("Brachiosaur", 'A', 100, false);
        this.setFemale(Math.random()<0.5);
        this.hitPoints = STARTING_HITPOINTS;
        maxHitPoints = MAX_HITPOINTS;
    }
}
