package game.items;

import edu.monash.fit2099.engine.*;

import java.util.List;

public class Teleporter extends Item {

    Location location;

    /***
     * Constructor for non-invisible teleporter
     *  @param name the name of this Item
     */
    public Teleporter(String name, GameMap gameMap) {
        super(name, '^', false);
    }

    /**
     * Constructor for invisible teleporter
     *
     * @param name     the name of this Item

     */
    public Teleporter(String name, Location teleporterLocation, Location destination, String direction) {
        super(name, '^', false);
        this.displayChar = getInvisibleDisplayChar(teleporterLocation);
        this.location = teleporterLocation;
        addTeleportAction(destination, direction);
    }

    public void addTeleportAction(Location destination, String direction) {
        this.allowableActions.add(new MoveActorAction(destination, direction));
    }

    private char getInvisibleDisplayChar(Location location) {
        char dispChar;

        List<Item> items = location.getItems();
        if (items.size() != 0) {
            dispChar = items.get(items.size() - 1).getDisplayChar();
        } else {
            dispChar = location.getGround().getDisplayChar();
        }

        return dispChar;
    }

}
