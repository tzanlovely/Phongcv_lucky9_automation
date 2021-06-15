package client;

import org.sikuli.script.Key;

import java.util.Arrays;
import java.util.List;

public class AdvanceLDClient extends LDClient {
    public AdvanceLDClient(String title) {
        super(title);
    }

    synchronized public static LDClient getInstance(int id) {
        assert id>0 && id<=6;
        if (LDClients[id-1] == null) {
            LDClients[id-1] = new AdvanceLDClient("Client"+id);
            System.out.println("created "+ LDClients[id-1]);
        } else if (!(LDClients[id-1] instanceof AdvanceLDClient)) {
            LDClients[id-1] = new AdvanceLDClient("Client"+id);
            System.out.println("created "+ LDClients[id-1]);
        }
        return LDClients[id-1];
    }

    public void logIn(String id) {
        System.out.println("client "+this + " log in");
        try {
            this.click(Arrays.asList("name:btn_ZID"));
            clearText("name:sprite_Name");
            this.type(id);
            this.click(Arrays.asList("name:btn_Ok"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        System.out.println("Client"+this+" log out");
        try {
            this.click(Arrays.asList("name:btn_Settings"));
            this.click(Arrays.asList("name:btn_Logout"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExist(List<String> filter) {


        return true;
    }

    public boolean checkUser(List<String> filter) {
        return true;
    }

    public void backToLobby() {

    }

    private void clearText(String id) {
        try {
            this.click(Arrays.asList(id));
            for (int i=0; i<10; i++) {
                this.window.type(Key.BACKSPACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
