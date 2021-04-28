package game.items;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;
import game.dinosaurs.BabyAllosaur;
import game.dinosaurs.BabyBrachiosaur;
import game.dinosaurs.BabyStegosaur;

/**
 * A class representing a dinosaur egg. Has an enum attribute 'type' to represent what type of dinosaur egg it is
 * @see game.dinosaurs.Dinosaur
 */
public class Egg extends PortableItem{

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

    private int roundsPassed = 0;
    private int timeToHatch;
    private final int STEGOSAUR_TIME_TO_HATCH = 40;
    private final int BRANCHIOSAUR_TIME_TO_HATCH = 70;
    private final int ALLOSAUR_TIME_TO_HATCH = 50;

    /**
     * Constructor. Sets timeToHatch to the appropriate number of turns based on the dinosaur type
     * @param type
     */
    public Egg(Type type) {
        super(type.getName()+" egg", 'e');
        this.type = type;
        if(type==Type.Stegosaur){
            timeToHatch=STEGOSAUR_TIME_TO_HATCH;
        } else if(type==Type.Branchiosaur) {
            timeToHatch=BRANCHIOSAUR_TIME_TO_HATCH;
        } else if(type==Type.Allosaur) {
            timeToHatch = ALLOSAUR_TIME_TO_HATCH;
        }
    }

    /**
     * Extends the parent tick() method. Handles the hatching of the egg if it is on the ground (not carried by an Actor)
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        roundsPassed++;
        if(roundsPassed>=timeToHatch){
            // Egg will try to hatch a baby dinosaur at its location. If it can't, it will try to hatch a baby dinosaur at its adjacent locations
            if(type==Type.Stegosaur){
                if(currentLocation.canActorEnter(new BabyStegosaur())){
                    currentLocation.addActor(new BabyStegosaur());
                } else {
                    for(Exit exit : currentLocation.getExits()){
                        if(exit.getDestination().canActorEnter(new BabyStegosaur())){
                            exit.getDestination().addActor(new BabyStegosaur());
                            break;
                        }
                    }
                }
            } else if(type==Type.Branchiosaur){
                if(currentLocation.canActorEnter(new BabyBrachiosaur())){
                    currentLocation.addActor(new BabyBrachiosaur());
                } else {
                    for(Exit exit : currentLocation.getExits()){
                        if(exit.getDestination().canActorEnter(new BabyBrachiosaur())){
                            exit.getDestination().addActor(new BabyBrachiosaur());
                            break;
                        }
                    }
                }
            } else if(type==Type.Allosaur){
                if(currentLocation.canActorEnter(new BabyAllosaur())){
                    currentLocation.addActor(new BabyAllosaur());
                } else {
                    for(Exit exit : currentLocation.getExits()){
                        if(exit.getDestination().canActorEnter(new BabyAllosaur())){
                            exit.getDestination().addActor(new BabyAllosaur());
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
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        roundsPassed++;
        if(roundsPassed>=timeToHatch){
            // Egg will try to hatch a baby dinosaur an adjacent location
            if(type==Type.Stegosaur){
                for(Exit exit : currentLocation.getExits()){
                    if(exit.getDestination().canActorEnter(new BabyStegosaur())){
                        exit.getDestination().addActor(new BabyStegosaur());
                        break;
                    }
                }
            } else if(type==Type.Branchiosaur){
                for(Exit exit : currentLocation.getExits()){
                    if(exit.getDestination().canActorEnter(new BabyBrachiosaur())){
                        exit.getDestination().addActor(new BabyBrachiosaur());
                        break;
                    }
                }
            } else if(type==Type.Allosaur){
                for(Exit exit : currentLocation.getExits()){
                    if(exit.getDestination().canActorEnter(new BabyAllosaur())){
                        exit.getDestination().addActor(new BabyAllosaur());
                        break;
                    }
                }
            }
        }
    }
}
