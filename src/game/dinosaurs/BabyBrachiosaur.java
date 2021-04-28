package game.dinosaurs;

public class BabyBrachiosaur extends BabyDino{
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    whether the dinosaur is female
     */
    public BabyBrachiosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
    }

    /**
     * Constructor. Sets initial hitPoints to 10 and randomises gender
     */
    public BabyBrachiosaur(){
        super("Baby Branchiosaur", 's',10, false);
        boolean isFemale = Math.random() < 0.5;
        this.setFemale(isFemale);
    }
}
