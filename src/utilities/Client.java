package utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.App;
import org.sikuli.script.Image;
import org.sikuli.script.Location;
import org.sikuli.script.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Client implements IClient {
    protected static Client[] clients = new Client[5];

    protected String title;
    protected float width;
    protected float height;

    protected App app;
    protected Region window;

    protected AdbLog adbLog;

    public static final String imgPath = System.getProperty("user.dir") + "\\Image\\";
    public static JSONObject config = Json.read(System.getProperty("user.dir") + "\\Config\\config.json");

    protected float screenWidth=config.getFloat("screenWidth");
    protected float screenHeight=config.getFloat("screenHeight");

    private static final int topBarHeight =35;

    synchronized public static Client getInstance(int id) {
        assert id>0 && id<=6;
        if (clients[id-1] == null) {
            clients[id-1] = new Client("Client"+id);
            System.out.println("created "+clients[id-1]);
        }
        return clients[id-1];
    }

    /**
     * @param title title of app
     */
    protected Client(String title) {
        this.title = title;
        this.app = new App(title);
        if (app.window(0) == null) return;
        app.focus();
        Region window1 = app.window(0);
        this.window = new Region((window1.x), window1.y+topBarHeight , window1.w-topBarHeight, window1.h - topBarHeight);
        this.width = window.w;
        this.height = window.h;
        this.window.highlight(1);
        String adbAddress = config.getJSONObject(title).getString("adbAddress");
        adbLog = new AdbLog(adbAddress);

    }

    /**
     * Calculate absolute location
     *
     * @param location relative location
     * @return absolute location
     */
    protected Location calLocation(Location location) {
        return new Location(location.x + window.x, location.y + window.y);
    }

    protected Location convertNodeLocation(Node node){
        return calLocation(new Location(node.x * width / screenWidth, (screenHeight - node.y) * height / screenHeight));
    }

    protected void refreshLog() throws Exception {
        window.click(imgPath + "cheatIcon.png");
        Thread.sleep(300);
    }

    public JSONObject getUserInfo() throws Exception {
        JSONObject userInfo = null;
        refreshLog();
        try {
            userInfo = adbLog.getUserInfo();
        } catch (JSONException e) {
            System.out.println("JSONException");
            Thread.sleep(500);
            userInfo = getUserInfo();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("String index out of bounds exception");
            Thread.sleep(500);
            userInfo = getUserInfo();
        }
        return userInfo;
    }

    public JSONArray getPosition() throws Exception {
        JSONArray positionLog = null;
        refreshLog();
        try {
            positionLog = adbLog.getPositionLog();
        } catch (JSONException e) {
            System.out.println("JSONException");
            Thread.sleep(500);
            positionLog = getPosition();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("String index out of bounds exception");
            Thread.sleep(500);
            positionLog = getPosition();
        }
        return positionLog;
    }

    public void sleep(String time) throws Exception {
        Thread.sleep(Integer.parseInt(time));
    }

    @Override
    public JSONObject waitForExisting(List<String> filter) throws Exception {
        JSONObject jsonObject = null;
        int i = 40;
        while(jsonObject==null && i>0) {
            app.focus();
            JSONArray positionLog = getPosition();
            jsonObject = Json.findJsonObjectByFilter(positionLog, filter);
            Thread.sleep(500);
            i--;
        }
        assert jsonObject != null;
        return jsonObject;
    }

    @Override
    public void click(List<String> filter) throws Exception {
        app.focus();

        JSONObject jsonObject = waitForExisting(filter);

        System.out.println("click: "+ filter.toString());
//        JSONArray positionLog= getPosition();
//        JSONObject jsonObject = Json.findJsonObjectByFilter(positionLog, filter);
        assert jsonObject != null;
        Node node = new Node(jsonObject);
        System.out.println("Relative location: " + node.x + " " + node.y);
        this.window.click(convertNodeLocation(node));
    }

    @Override
    public void type(String content) throws Exception {
        window.type(content);
    }

    public Image captureScreen() {
        return window.getImage();
    }

}
