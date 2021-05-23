package game;


import edu.monash.fit2099.engine.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Game {

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
        "========================================================================================\n"
    ;

    public Game(Display display) {
        this.display = display;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void showMainMenu() {
        display.println(TITLE);
        display.println("SELECT A GAME MODE:");
        showMainMenuOptions();
    }

    private void showMainMenuOptions()
    {
        HashMap<Character, GameMode> keyToGameModeMap = new HashMap<Character, GameMode>();

        int i = 0;
        for (GameMode gameMode : GameMode.values()) {
            keyToGameModeMap.put(Integer.toString(i).charAt(0), gameMode);
            display.println(i + ": " + gameMode.menuDescription());
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
        }
    }



    public static void quitGame() {
        System.exit(0);
    }

    private enum GameMode {
        CHALLENGE("Challenge"),
        SANDBOX("Sandbox"),
        QUIT("Quit Game");

        private final String text;

        GameMode(final String text) {
            this.text = text;
        }

//        private void showChallengeOptions(Display display){
//            display.println();
//        }

        private String menuDescription() {
            return this.text;
        }
    }
}
