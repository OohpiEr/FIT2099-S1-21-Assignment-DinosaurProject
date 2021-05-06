package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Util;
import game.dinosaurs.AdultDino;
import game.dinosaurs.Dinosaur;

public class LayEggAction extends Action {

    private AdultDino dinosaur;
    private GameMap map;

    public LayEggAction(AdultDino dinosaur, GameMap map) {
        this.dinosaur = dinosaur;
        this.map = map;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        dinosaur.layEgg(map);
        return dinosaur + " lays an egg";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
