package game;


import edu.monash.fit2099.engine.*;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Pterodactyl;
import game.dinosaurs.Stegosaur;
import game.grounds.*;
import game.items.Teleporter;

import java.util.*;

public class Game {

    /**
     * Eco Points to Win for Game Mode Challenge. Wins if Player's Eco Points is more than ecoPointsToWin.
     */
    private int ecoPointsToWin;
    /**
     * Number of moves left for Challenge Game Mode.
     */
    private int movesLeft;
    private World world;
    private Display display;
    private GameMode gameMode;
    private static final String TITLE =
            "                               ,--.\n" +
                    "                              `.`_.`\\\n" +
                    "                                   \\ \\\n" +
                    "               __                   \\ \\\n" +
                    "              / _)   DESIGNOSAURS    \\ \\\n" +
                    "     _/\\/\\/\\_/ /                      \\ `-''^^^^^''-.\n" +
                    "   _|         /                        \\             `-._\n" +
                    " _|  (  | (  |                          >>   >  <  <__    ^'-----...,,_\n" +
                    "/__.-'|_|--|_|                         //__/`---'\\__\\\\`'\"\"\"\"'\"'\"'\"'''''``\n" +
                    "                                       `\"`\"\"      `\"\"`\"\n" +
                    "========================================================================================\n";
    private final List<String> MAIN_MAP = Arrays.asList(
            "................................................................................",
            "................................................................................",
            ".....#######....................................................................",
            ".....#_____#................~~~.................................................",
            ".....#_$___#...............~~~~~~~..............................................",
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
    private final List<String> NORTHERN_MAP = Arrays.asList(
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

    /**
     * Constructor for a new Game
     *
     * @param display the I/O object to which messages may be written
     */
    public Game(Display display) {
        this.display = display;
    }

    public static String getTITLE() {
        return TITLE;
    }

    /**
     * Setter for game world
     *
     * @param world
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Setter for game mode
     *
     * @param gameMode
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Setter for Eco Points to win for Challenge Game Mode
     *
     * @param ecoPointsToWin
     */
    private void setEcoPointsToWin(int ecoPointsToWin) {
        if (gameMode == GameMode.CHALLENGE) {
            this.ecoPointsToWin = ecoPointsToWin;
        }
    }

    /**
     * Setter for number of moves left for game mode challenge
     *
     * @param movesLeft number of moves left for game mode challenge
     */
    private void setMovesLeft(int movesLeft) {
        if (gameMode == GameMode.CHALLENGE) {
            this.movesLeft = movesLeft;
        }
    }

    /**
     * Getter for Eco Points to Win for Game Mode Challenge.
     *
     * @return Eco Points to Win
     */
    public int getEcoPointsToWin() {
        return ecoPointsToWin;
    }

    /**
     * Getter for number of moves left for game mode challenge
     *
     * @return number of moves left
     */
    public int getMovesLeft() {
        return movesLeft;
    }

    /**
     * Prints out Main Menu on the I/O
     */
    public void showMainMenu() {
        display.println(TITLE);
        display.println("SELECT A GAME MODE:");
        showGameOptions();
    }

    /**
     * Prints out Game Options on the I/O
     */
    public void showGameOptions() {
        HashMap<Character, GameMode> keyToGameModeMap = new HashMap<Character, GameMode>();

        int i = 1;
        for (GameMode gameMode : GameMode.values()) {
            keyToGameModeMap.put(Integer.toString(i).charAt(0), gameMode);
            display.println(i + ": " + gameMode.toString());
            i++;
        }

        char key;
        do {
            key = display.readChar();
        } while (!keyToGameModeMap.containsKey(key));

        if (keyToGameModeMap.get(key) == GameMode.QUIT) {
            quitGame();
        } else {
            setGameMode(keyToGameModeMap.get(key));
            gameMode.showOptions(display, this);
            setUpWorld();
        }
    }

    /**
     * Sets up new world for the game and runs the game.
     */
    private void setUpWorld() {
        setWorld(new World(display));
        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new VendingMachine(), new Bush(), new Lake());
        GameMap gameMap = new GameMap(groundFactory, MAIN_MAP);
        GameMap mapNorth = new GameMap(groundFactory, NORTHERN_MAP);
        world.addGameMap(gameMap);
        world.addGameMap(mapNorth);

        Location mapNorthDestination = mapNorth.at(Math.round(mapNorth.getXRange().max() / 2), mapNorth.getYRange().max());
        for (int x : gameMap.getXRange()) {
            Location currentLocation = gameMap.at(x, 0);
            Teleporter invisibleTeleporter = new Teleporter("Main to Northern Game Map Teleporter", currentLocation,
                    mapNorthDestination, "to Northern Map!");
            currentLocation.addItem(invisibleTeleporter);
        }

        Location mainMapDestination = gameMap.at(Math.round(gameMap.getXRange().max() / 2), 0);
        for (int x : mapNorth.getXRange()) {
            Location currentLocation = mapNorth.at(x, mapNorth.getYRange().max());
            Teleporter invisibleTeleporter = new Teleporter("Northern Game Map to Main Teleporter", currentLocation,
                    mainMapDestination, "to Main Map");
            currentLocation.addItem(invisibleTeleporter);
        }

        Player player = new Player("Player", '@', 100, this);
        Player.resetEcoPoints();
        world.addPlayer(player, gameMap.at(1, 1));

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

    /**
     * Called to exit the game
     */
    public static void quitGame() {
        System.exit(0);
    }

    /**
     * Called every Player's play turn to track game progress
     *
     * @param ecoPoints player's current Eco Points
     */
    public void tick(int ecoPoints) {
        display.println("Game Mode: " + gameMode.toString());
        if (gameMode == GameMode.CHALLENGE) {
            if (movesLeft == 0) {
                showLoseMessage();
            } else if (ecoPoints >= ecoPointsToWin) {
                showWinMessage();
            }
            display.println(String.format("Eco Points To Win: %d, Number of Moves Left: %d", getEcoPointsToWin(), getMovesLeft()));
            movesLeft--;
        }
    }

    /**
     * Prints out message for winning the game
     */
    private void showWinMessage() {
        display.println(TITLE);
        display.println("YOU WIN THE CHALLENGE\n\nPlay Again?");
        showGameOptions();
    }

    /**
     * Prints out message for losing the game
     */
    private void showLoseMessage() {
        display.println(TITLE);
        display.println("GAME OVER\n\nPlay Again?");
        showGameOptions();
    }

    /**
     * Class for Game Mode
     */
    public enum GameMode {
        /**
         * <p>Game Mode Challenge.
         * Player choose a number of moves and a number of eco points.
         * They win the game if they get the specified number of eco points within the specified number
         * of moves, and loses if they do not </p>
         */
        CHALLENGE("Challenge") {
            /**
             * Shows options specific to each Challenge game mode
             * Lets player choose number of moves and eco points.
             *
             * @param display the I/O object to which messages may be written
             * @param game current game
             */
            @Override
            void showOptions(Display display, Game game) {
                Scanner scanner = new Scanner(System.in);
                int maxMoves = 0, ecoPointsToWin = 0;
                boolean flag = false;

                display.println("\nGAME MODE CHALLENGE");
                do  {
                    display.println("Number of moves:");
                    if (scanner.hasNextInt()) {
                        maxMoves = scanner.nextInt();
                    } else {
                        display.println("Number must be an Integer");
                        scanner.next();
                        continue;
                    }

                    display.println("Number of Eco Points:");
                    if (scanner.hasNextInt()) {
                        ecoPointsToWin = scanner.nextInt();
                    } else {
                        display.println("Number must be an Integer");
                        scanner.next();
                        continue;
                    }
                    flag = true;
                }while (!flag);

                game.setMovesLeft(maxMoves);
                game.setEcoPointsToWin(ecoPointsToWin);
            }
        },
        /***
         * Game Mode Sandbox.
         * Game just runs as normal.
         */
        SANDBOX("Sandbox") {
            @Override
            void showOptions(Display display, Game game) {
            }
        },
        /**
         * Quit the game
         */
        QUIT("Quit Game") {
            @Override
            void showOptions(Display display, Game game) {
            }
        };


        /**
         * text description of game modes
         */
        private final String text;

        /**
         * Constructor for GameMode class
         *
         * @param text text description of game modes
         */
        GameMode(final String text) {
            this.text = text;
        }

        /**
         * Shows options specific to each game mode
         *
         * @param display the I/O object to which messages may be written
         * @param game    current game
         */
        abstract void showOptions(Display display, Game game);

        @Override
        public String toString() {
            return this.text;
        }
    }
}
