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
        backButtons.add(Arrays.asList("name:label_BtnOkTitle"));
        backButtons.add(Arrays.asList("name:btn_Accept"));
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
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean logOut(IClient client) throws Exception {
        System.out.println("Client"+ client +" log out");
        try {
            if (!client.isExist(Arrays.asList("name:btn_Settings"))) return false;
            client.click(Arrays.asList("name:btn_Settings"));
            client.click(Arrays.asList("name:btn_Logout"));
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void closeGame(IClient client) throws Exception {

    }

    public static void backToLobby(IClient client) throws Exception {
        System.out.println("back to lobby: "+client);
        for(List<String> btn: backButtons) {
            if (client.isExist(btn)) {
                client.click(btn);
                Thread.sleep(500);
                backToLobby(client);
                return;
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
