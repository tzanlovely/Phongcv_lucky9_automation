package client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.App;
import org.sikuli.script.Image;
import org.sikuli.script.Location;
import org.sikuli.script.Region;
import utilities.AdbLog;
import utilities.Json;
import utilities.Node;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class LDClient implements IClient {
    public static boolean usingCache = false;
    private static Set<JSONObject> buttonCache = new HashSet<>();

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

    protected static LDClient[] LDClients = new LDClient[10];
    synchronized public static LDClient getInstance(int id) {
        if (LDClients[id-1] == null) {
            LDClients[id-1] = new LDClient("Client"+id);
            System.out.println("created "+ LDClients[id-1]);
        }
        return LDClients[id-1];
    }

    /**
     * @param title title of app
     */
    protected LDClient(String title) {
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

    public void refreshLog() throws Exception {
        app.focus();
        try {
            window.click(imgPath + "cheatIcon.png");
        } catch (Exception e) {
            try {
                window.click(imgPath + "icongame.png");
            } catch (Exception ex) {
                throw ex;
            }
            throw e;
        }
        Thread.sleep(100);
    }

    synchronized private static void addToCache(JSONObject btn) {
        if (!buttonCache.contains(btn)) buttonCache.add(btn);
    }

    private static JSONObject findInCache(List<String> filter) {
        Iterator<JSONObject> btnIterator = buttonCache.iterator();
        JSONObject result = null;
        while(btnIterator.hasNext()) {
            JSONObject btn = btnIterator.next();
            if (Json.isInJSONObject(btn, filter)) {
                result = btn;
                break;
            }
        }
        return result;
    }

    public JSONObject getUserInfo() throws Exception {
        app.focus();
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
        app.focus();
        JSONArray positionLog = null;
        refreshLog();
        try {
            positionLog = adbLog.getPositionLog();
        } catch (JSONException e) {
            System.out.println("JSONException");
            Thread.sleep(100);
            positionLog = getPosition();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("String index out of bounds exception");
            Thread.sleep(100);
            positionLog = getPosition();
        }
        return positionLog;
    }

    public void sleep(String time) throws Exception {
        app.focus();
        Thread.sleep(Integer.parseInt(time));
    }

    public JSONObject waitForExisting(List<String> filter) throws Exception {
        JSONObject jsonObject = null;
        int i = 25;
        while(jsonObject==null && i>0) {
            app.focus();
            JSONArray positionLog = getPosition();
            jsonObject = Json.findJsonObjectByFilter(positionLog, filter);
            Thread.sleep(150);
            i--;
        }
        if (jsonObject!=null && usingCache) addToCache(jsonObject);
        return jsonObject;
    }

    @Override
    public void click(List<String> filter) throws Exception {
        app.focus();
        JSONObject jsonObject = null;
        if (LDClient.usingCache) {
            jsonObject = findInCache(filter);
        }
        if (jsonObject == null) jsonObject = waitForExisting(filter);
        System.out.println("click: "+ filter.toString());
        assert jsonObject != null;
        Node node = new Node(jsonObject);
        this.window.click(convertNodeLocation(node));
    }

    @Override
    public void type(String content) throws Exception {
        window.type(content);
    }

    @Override
    public void dragDrop(String begin, String end) throws Exception {
        app.focus();
        window.dragDrop(imgPath+begin, imgPath+end);
    }

    public Image captureScreen() {
        return window.getImage();
    }

    @Override
    public boolean checkUser(List<String> filter) throws Exception {
        app.focus();
        return Json.isInJSONObject(getUserInfo(), filter);
    }

    @Override
    public boolean isExist(List<String> filter) throws Exception {
        app.focus();
        return (Json.findJsonObjectByFilter(getPosition(), filter))!=null;
    }

}
