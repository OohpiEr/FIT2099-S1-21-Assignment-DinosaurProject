package game.dinosaurs;

public class BabyAllosaur extends BabyDino{
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param isFemale    
     */
    public BabyAllosaur(String name, char displayChar, int hitPoints, boolean isFemale) {
        super(name, displayChar, hitPoints, isFemale);
    }

    /**
     * Constructor. Sets initial hitPoints to 20 and randomises gender
     */
    public BabyAllosaur(){
        super("Baby Allosaur", 's',20, false);
        boolean isFemale = Math.random() < 0.5;
        this.setFemale(isFemale);
    }
}
