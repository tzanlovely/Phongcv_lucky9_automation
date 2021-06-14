package utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sikuli.script.Image;

import java.util.List;

public interface IClient {
    public void click(List<String> filter) throws Exception ;
    public void type(String content) throws Exception ;
    public Image captureScreen() throws Exception;
    public JSONObject waitForExisting (List<String> filter) throws Exception;
    public JSONObject getUserInfo() throws Exception;
    public JSONArray getPosition() throws Exception;
    public void sleep(String time) throws Exception;
}
