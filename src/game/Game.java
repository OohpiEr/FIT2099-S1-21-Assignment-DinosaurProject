package game;


import edu.monash.fit2099.engine.*;

import java.util.*;

public class Game {

    private int ecoPointsToWin, maxMoves;
    private Display display;
    private GameMode gameMode;
    private final String TITLE =
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

    public Game(Display display) {
        this.display = display;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    private void setEcoPointsToWin(int ecoPointsToWin) {
        if (gameMode == GameMode.CHALLENGE) {
            this.ecoPointsToWin = ecoPointsToWin;
        }
    }

    private void setMaxMoves(int maxMoves) {
        if (gameMode == GameMode.CHALLENGE) {
            this.maxMoves = maxMoves;
        }
    }

    public int getEcoPointsToWin() {
        return ecoPointsToWin;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    public void showMainMenu() {
        display.println(TITLE);
        display.println("SELECT A GAME MODE:");
        showGameOptions();
    }

    public void showGameOptions() {
        HashMap<Character, GameMode> keyToGameModeMap = new HashMap<Character, GameMode>();

        int i = 0;
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
        }
    }


    public static void quitGame() {
        System.exit(0);
    }

    public void tick(int numberOfMoves, int ecoPoints) {
        display.println("Game Mode: " + gameMode.toString());
        if (gameMode == GameMode.CHALLENGE) {
            if (numberOfMoves >= maxMoves){
                showLoseMessage();
            } else if (ecoPoints >= ecoPointsToWin){
                showWinMessage();
            }
            display.println(String.format("Eco Points: %d, Number of Moves: %d",  getEcoPointsToWin(), getMaxMoves()));
        }
    }

    private void showWinMessage() {
        display.println("YOU WIN THE CHALLENGE\n\nPlay Again?");
        showGameOptions();
    }

    private void showLoseMessage() {
        display.println("GAME OVER\n\nPlay Again?");
        showGameOptions();
    }

    public enum GameMode {
        CHALLENGE("Challenge") {
            @Override
            void showOptions(Display display, Game game) {
                Scanner scanner = new Scanner(System.in);
                int maxMoves = 0, ecoPointsToWin = 0;

                display.println("GAME MODE CHALLENGE");
                do {
                    display.println("Number of moves:");
                    maxMoves = scanner.nextInt();
                    display.println("Number of Eco Points:");
                    ecoPointsToWin = scanner.nextInt();
                } while (maxMoves <= 0 && ecoPointsToWin <= 0);

                game.setMaxMoves(maxMoves);
                game.setEcoPointsToWin(ecoPointsToWin);
            }
        },
        SANDBOX("Sandbox") {
            @Override
            void showOptions(Display display, Game game) {
            }
        },
        QUIT("Quit Game") {
            @Override
            void showOptions(Display display, Game game) {
            }
        };


        private final String text;

        GameMode(final String text) {
            this.text = text;
        }


        abstract void showOptions(Display display, Game game);

        @Override
        public String toString() {
            return this.text;
        }
    }
}
