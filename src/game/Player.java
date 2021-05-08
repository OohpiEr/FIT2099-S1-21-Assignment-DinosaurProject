package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private static int ecoPoints = 0;
	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Returns the player's number of eco points
	 * @return	the player's number of eco points
	 */
	public static int getEcoPoints() {
		return ecoPoints;
	}

	/**
	 * Sets the player's number of eco points
	 * @param ecoPoints	The new number of eco points the player should have
	 */
	public static void setEcoPoints(int ecoPoints) {
		if(ecoPoints>=0){
			Player.ecoPoints = ecoPoints;
		}
	}

	/**
	 * Adds the provided number of eco points to the player's total eco points
	 * @param ecoPoints	The number of eco points to be added to the player's total eco points
	 */
	public static void addEcoPoints(int ecoPoints){
		if(ecoPoints>=0){
			Player.ecoPoints+=ecoPoints;
		}
	}

	/**
	 * Removes the provided number of eco points from the player's total eco points
	 * @param ecoPoints	The number of eco points to be removed from the player's total eco points
	 */
	public static void removeEcoPoints(int ecoPoints){
		if(ecoPoints>=0){
			Player.ecoPoints-=ecoPoints;
		}
	}
}
