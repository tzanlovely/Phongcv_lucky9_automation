package func;

import client.IClient;
import org.sikuli.script.Key;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function {
    private static List<List<String>> backButtons;

    static {
        backButtons = new ArrayList<>();
        backButtons.add(Arrays.asList("name:btn_Menu"));
        backButtons.add(Arrays.asList(new String[]{"name:btn_Close", "width:184"}));
        backButtons.add(Arrays.asList("name:btn_Close"));
        backButtons.add(Arrays.asList("name:btn_Cancel"));
    }

    public static boolean logIn(IClient client, String id) throws Exception {
        System.out.println("client "+client + " log in");
        try {
            client.click(Arrays.asList("name:btn_ZID"));
            clearText(client, "name:sprite_Name");
            client.type(id);
            client.click(Arrays.asList("name:btn_Ok"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean logOut(IClient client) throws Exception {
        System.out.println("Client"+ client +" log out");
        try {
            client.click(Arrays.asList("name:btn_Settings"));
            client.click(Arrays.asList("name:btn_Logout"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void closeGame(IClient client) throws Exception {

    }

    public static void backToLobby(IClient client) throws Exception {
        if (client.isExist(Arrays.asList("name:settings"))) return;
        for(List<String> btn: backButtons) {
            if (client.isExist(btn)) {
                client.click(btn);
                backToLobby(client);
            }
        }
    }

    public static void clearText(IClient client, String id) throws Exception {
        try {
            client.click(Arrays.asList(id));
            for (int i=0; i<10; i++) {
                client.type(Key.BACKSPACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
