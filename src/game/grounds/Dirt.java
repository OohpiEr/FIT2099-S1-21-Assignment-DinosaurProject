package game.grounds;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {
		super('.');
	}
	private final double BASE_CHANCE_TO_GROW_BUSH = 0.01;
	private final double CHANCE_TO_GROW_BUSH_IF_HAVE_ADJACENT_BUSH = 0.10;

	/**
	 * Extends the parent tick() method. Adds a chance for the Dirt to turn into a Bush if there is no adjacent Tree. Chance is increased if there is an adjacent Bush
	 * @see Bush
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		boolean canGrowBush = true;
		boolean hasAdjacentBush = false;
		for(Exit exit : location.getExits()){
			if(exit.getDestination().getGround().getClass()==Tree.class){
				canGrowBush = false;
				break;
			}
			if(exit.getDestination().getGround().getClass()==Bush.class){
				hasAdjacentBush = true;
				break;
			}
		}
		if(canGrowBush){
			double chanceToGrowBush = hasAdjacentBush ? CHANCE_TO_GROW_BUSH_IF_HAVE_ADJACENT_BUSH : BASE_CHANCE_TO_GROW_BUSH;
			if(Math.random()<=chanceToGrowBush){
				location.setGround(new Bush());
			}
		}
	}
}
