package game.items;

import edu.monash.fit2099.engine.*;

import java.util.List;

public class Teleporter extends Item {


    /**
     * Constructor for non-invisible teleporter
     *
     * @param name        the name of this Item
     * @param destination location to be teleported to
     * @param direction   String describing the direction to move in, e.g. "North"
     */
    public Teleporter(String name, Location destination, String direction) {
        super(name, '^', false);
        addTeleportAction(destination, direction);
    }

    /**
     * Constructor for invisible teleporter
     *
     * @param name               the name of this Item
     * @param teleporterLocation current teleporter location
     * @param destination        location to be teleported to
     * @param direction          String describing the direction to move in, e.g. "North"
     */
    public Teleporter(String name, Location teleporterLocation, Location destination, String direction) {
        super(name, '^', false);
        setDisplayChar(getInvisibleDisplayChar(teleporterLocation));
        addTeleportAction(destination, direction);
    }

    /**
     * Setter for displayChar
     * @param displayChar char of item to display
     */
    private void setDisplayChar(char displayChar) {
        this.displayChar = displayChar;
    }

    /**
     * Adds a MoveActorAction to teleporter's allowableActions
     *
     * @param destination location to be teleported to
     * @param direction   String describing the direction to move in, e.g. "North"
     */
    public void addTeleportAction(Location destination, String direction) {
        this.allowableActions.add(new MoveActorAction(destination, direction));
    }

    /**
     * <p>Changes the teleporter's display char to match the display char of the ground
     * or item on the ground (if there's an item) </>
     *
     * @param teleporterLocation current teleporter location
     * @return a char to be displayed
     */
    private char getInvisibleDisplayChar(Location teleporterLocation) {
        char dispChar;

        List<Item> items = teleporterLocation.getItems();
        if (items.size() > 1) {
            dispChar = items.get(items.size() - 1).getDisplayChar();
        } else {
            dispChar = teleporterLocation.getGround().getDisplayChar();
        }

        return dispChar;
    }

    /**
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        setDisplayChar(getInvisibleDisplayChar(currentLocation));
    }
}
