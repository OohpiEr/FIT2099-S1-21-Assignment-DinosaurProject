package game;


import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainMenu {

//    public void showMainMenu(Display display) {
//        ArrayList<Character> freeChars = new ArrayList<Character>();
//        HashMap<Character, Action> keyToActionMap = new HashMap<Character, Action>();
//
//        for (char i = 'a'; i <= 'z'; i++)
//            freeChars.add(i);
//
//        // Show with the actions with hotkeys first;
//        for (Action action : actions.sorted(new Menu.SortHotkeysFirst())) {
//            String hotKey = action.hotkey();
//            char c;
//            if (hotKey == null || hotKey == "") {
//                if (freeChars.isEmpty())
//                    break; // we've run out of characters to pick from.
//                c = freeChars.get(0);
//            } else {
//                c = hotKey.charAt(0);
//            }
//            freeChars.remove(Character.valueOf(c));
//            keyToActionMap.put(c, action);
//            display.println(c + ": " + action.menuDescription(actor));
//        }
//
//        char key;
//        do {
//            key = display.readChar();
//        } while (!keyToActionMap.containsKey(key));
//
//        return keyToActionMap.get(key);
//    }

    public static void quitGame(){
        System.exit(0);
    }
}
