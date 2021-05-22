package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.*;
import game.grounds.*;
import game.items.Teleporter;

/**
 * The main class for the Jurassic World game.
 */
public class Application {

    public static void main(String[] args) {
        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new VendingMachine(), new Bush(), new Lake());

        List<String> map = Arrays.asList(
                "................................................................................",
                "................................................................................",
                ".....#######....................................................................",
                ".....#_____#................~~~.................................................",
                ".....#_$___#...............~~~~~~~...............................................",
                ".....###.###................~~~~~~..............................................",
                ".............................~..................................................",
                "......................................+++.......................................",
                ".......................................++++.....................................",
                "...................~~~.............+++++........................................",
                "....................~~~~.............++++++.....................................",
                "......................~~..............+++.......................................",
                ".....................................+++........................................",
                "................................................................................",
                "............+++.................................................................",
                ".............+++++..............................................................",
                "...............++........................................+++++..................",
                ".............+++....................................++++++++....................",
                "............+++.......................................+++.......................",
                "..................................................................~.............",
                "...................~~~~~~~.......................................~~~~~...++.....",
                ".................~~~~~~~~.........................................~~~~..++.++...",
                ".................~~~~~~........................................~~~~~~~...++++...",
                "................................................................~~~.......++....",
                "................................................................................");
        GameMap gameMap = new GameMap(groundFactory, map);
        world.addGameMap(gameMap);

        List<String> gameMapNorth = Arrays.asList(
                "................................................................................",
                "................................................................................",
                "................................................................................",
                "................................................................................",
                "................................................................................",
                "................................................................................",
                "................................................................................",
                "......................................+++.......................................",
                ".......................................++++.....................................",
                "...................................+++++........................................",
                ".....................................++++++.....................................",
                "......................................+++.......................................",
                ".....................................+++........................................",
                "................................................................................",
                "............+++.................................................................",
                ".............+++++..............................................................",
                "...............++........................................+++++..................",
                ".............+++....................................++++++++....................",
                "............+++.......................................+++.......................",
                "................................................................................",
                ".........................................................................++.....",
                "........................................................................++.++...",
                ".........................................................................++++...",
                "..........................................................................++....",
                "................................................................................");
        GameMap mapNorth = new GameMap(groundFactory, gameMapNorth);
        world.addGameMap(mapNorth);

        Location mapNorthDestination = mapNorth.at(Math.round(mapNorth.getXRange().max() / 2), mapNorth.getYRange().max());
        for (int x : gameMap.getXRange()) {
            Location currentLocation = gameMap.at(x, 0);
            Teleporter invisibleTeleporter = new Teleporter("Main to Northern Game Map Teleporter", currentLocation,
                    mapNorthDestination, "further North!");
            currentLocation.addItem(invisibleTeleporter);
        }

        Location mainMapDestination = gameMap.at(Math.round(gameMap.getXRange().max() / 2), 0);
        for (int x : mapNorth.getXRange()) {
            Location currentLocation = mapNorth.at(x, mapNorth.getYRange().max());
            Teleporter invisibleTeleporter = new Teleporter("Northern Game Map to Main Teleporter", currentLocation,
                    mainMapDestination, "further South!");
            currentLocation.addItem(invisibleTeleporter);
        }

        Actor player = new Player("Player", '@', 100);
        world.addPlayer(player, gameMap.at(1, 1));

        // Place a pair of stegosaurs in the middle of the map
        gameMap.at(10, 10).addActor(new Stegosaur("Stegosaur", true));
        gameMap.at(20, 19).addActor(new Stegosaur("Stegosaur", false));

        gameMap.at(21, 19).addActor(new Brachiosaur("Brachiosaur", false));
        gameMap.at(11, 11).addActor(new Brachiosaur("Brachiosaur", false));
        gameMap.at(15, 14).addActor(new Brachiosaur("Brachiosaur", true));
        gameMap.at(18, 18).addActor(new Brachiosaur("Brachiosaur", true));

        gameMap.at(70, 23).addActor(new Pterodactyl("Pterodactyl", true));
        gameMap.at(70, 24).addActor(new Pterodactyl("Pterodactyl", false));

        world.run();
    }
}
