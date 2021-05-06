package game.items;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;
import game.Player;
import game.dinosaurs.BabyAllosaur;
import game.dinosaurs.BabyBrachiosaur;
import game.dinosaurs.BabyStegosaur;
import game.dinosaurs.DinosaurEnumType;

import java.util.concurrent.TimeoutException;

/**
 * A class representing a dinosaur egg. Has an enum attribute 'type' to represent what type of dinosaur egg it is
 *
 * @see game.dinosaurs.Dinosaur
 */
public class Egg extends PortableItem {

    DinosaurEnumType type;

    private int roundsPassed = 0;
    private int timeToHatch;
    private final int STEGOSAUR_TIME_TO_HATCH = 40;
    private final int BRANCHIOSAUR_TIME_TO_HATCH = 70;
    private final int ALLOSAUR_TIME_TO_HATCH = 50;

    private final int STEGOSAUR_HATCH_ECO_POINT_REWARD = 100;
    private final int BRACHIOSAUR_HATCH_ECO_POINT_REWARD = 100;
    private final int ALLOSAUR_HATCH_ECO_POINT_REWARD = 100;

    /**
     * Constructor. Sets timeToHatch to the appropriate number of turns based on the dinosaur type
     *
     * @param type
     */
    public Egg(DinosaurEnumType type) {
        super(type.getName() + " egg", 'e');
        this.type = type;
        setTimeToHatch();
    }

    public void setTimeToHatch() {
        if (type == DinosaurEnumType.STEGOSAUR) {
            this.timeToHatch = STEGOSAUR_TIME_TO_HATCH;
        } else if (type == DinosaurEnumType.BRANCHIOSAUR) {
            this.timeToHatch = BRANCHIOSAUR_TIME_TO_HATCH;
        } else if (type == DinosaurEnumType.ALLOSAUR) {
            this.timeToHatch = ALLOSAUR_TIME_TO_HATCH;
        }
    }

    /**
     * Extends the parent tick() method. Handles the hatching of the egg if it is on the ground (not carried by an Actor)
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        roundsPassed++;
        if (roundsPassed >= timeToHatch) {
            // Egg will try to hatch a baby dinosaur at its location. If it can't, it will try to hatch a baby dinosaur at its adjacent locations
            if (type == DinosaurEnumType.STEGOSAUR) {
                if (currentLocation.canActorEnter(new BabyStegosaur())) {
                    currentLocation.addActor(new BabyStegosaur());
                } else {
                    for (Exit exit : currentLocation.getExits()) {
                        if (exit.getDestination().canActorEnter(new BabyStegosaur())) {
                            exit.getDestination().addActor(new BabyStegosaur());
                            Player.addEcoPoints(STEGOSAUR_HATCH_ECO_POINT_REWARD);
                            break;
                        }
                    }
                }
            } else if (type == DinosaurEnumType.BRANCHIOSAUR) {
                if (currentLocation.canActorEnter(new BabyBrachiosaur())) {
                    currentLocation.addActor(new BabyBrachiosaur());
                } else {
                    for (Exit exit : currentLocation.getExits()) {
                        if (exit.getDestination().canActorEnter(new BabyBrachiosaur())) {
                            exit.getDestination().addActor(new BabyBrachiosaur());
                            Player.addEcoPoints(BRACHIOSAUR_HATCH_ECO_POINT_REWARD);
                            break;
                        }
                    }
                }
            } else if (type == DinosaurEnumType.ALLOSAUR) {
                if (currentLocation.canActorEnter(new BabyAllosaur())) {
                    currentLocation.addActor(new BabyAllosaur());
                } else {
                    for (Exit exit : currentLocation.getExits()) {
                        if (exit.getDestination().canActorEnter(new BabyAllosaur())) {
                            exit.getDestination().addActor(new BabyAllosaur());
                            Player.addEcoPoints(ALLOSAUR_HATCH_ECO_POINT_REWARD);
                            break;
                        }
                    }
                }
            }
            currentLocation.removeItem(this);
        }
    }

    /**
     * Extends the parent tick() method. Handles the hatching of the egg if it is carried by an Actor
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor           The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        roundsPassed++;
        if (roundsPassed >= timeToHatch) {
            // Egg will try to hatch a baby dinosaur an adjacent location
            if (type == DinosaurEnumType.STEGOSAUR) {
                for (Exit exit : currentLocation.getExits()) {
                    if (exit.getDestination().canActorEnter(new BabyStegosaur())) {
                        exit.getDestination().addActor(new BabyStegosaur());
                        Player.addEcoPoints(STEGOSAUR_HATCH_ECO_POINT_REWARD);
                        break;
                    }
                }
            } else if (type == DinosaurEnumType.BRANCHIOSAUR) {
                for (Exit exit : currentLocation.getExits()) {
                    if (exit.getDestination().canActorEnter(new BabyBrachiosaur())) {
                        exit.getDestination().addActor(new BabyBrachiosaur());
                        Player.addEcoPoints(BRACHIOSAUR_HATCH_ECO_POINT_REWARD);
                        break;
                    }
                }
            } else if (type == DinosaurEnumType.ALLOSAUR) {
                for (Exit exit : currentLocation.getExits()) {
                    if (exit.getDestination().canActorEnter(new BabyAllosaur())) {
                        exit.getDestination().addActor(new BabyAllosaur());
                        Player.addEcoPoints(ALLOSAUR_HATCH_ECO_POINT_REWARD);
                        break;
                    }
                }
            }
        }
    }
}
