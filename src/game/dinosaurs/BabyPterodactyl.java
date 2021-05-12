package game.dinosaurs;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class BabyPterodactyl extends BabyDino{
    @Override
    public void growUp(GameMap map) {

    }

    @Override
    public void eat(Item food, int quantity) {

    }

    @Override
    public void drink(int sips) {

    }

    @Override
    protected Action determineBehaviour(GameMap map) {
        return null;
    }
}
