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

	private int ecoPoints;
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
		setEcoPoints(0);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}

	public int getEcoPoints() {
		return ecoPoints;
	}

	public void setEcoPoints(int ecoPoints) {
		if(ecoPoints>=0){
			this.ecoPoints = ecoPoints;
		}
	}

	public void addEcoPoints(int ecoPoints){
		if(ecoPoints>=0){
			this.ecoPoints+=ecoPoints;
		}
	}

	public void removeEcoPoints(int ecoPoints){
		if(ecoPoints>=0){
			this.ecoPoints-=ecoPoints;
		}
	}
}
