package utilities;

import org.sikuli.script.Key;

import java.util.Arrays;
import java.util.List;

public class AdvanceClient extends Client {
    public AdvanceClient(String title) {
        super(title);
    }

    synchronized public static Client getInstance(int id) {
        assert id>0 && id<=6;
        if (clients[id-1] == null) {
            clients[id-1] = new AdvanceClient("Client"+id);
            System.out.println("created "+clients[id-1]);
        } else if (!(clients[id-1] instanceof AdvanceClient)) {
            clients[id-1] = new AdvanceClient("Client"+id);
            System.out.println("created "+clients[id-1]);
        }
        return clients[id-1];
    }

    public void logIn(String id) {
        System.out.println("client "+this + " log in");
        try {
            this.click(Arrays.asList("name:btn_ZID"));
            this.click(Arrays.asList("name:sprite_Name"));
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
            this.refreshLog();
            this.click(Arrays.asList(id));
            for (int i=0; i<20; i++) {
                this.window.type(Key.BACKSPACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
