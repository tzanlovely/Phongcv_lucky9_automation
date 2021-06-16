package client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sikuli.script.Image;

import java.util.List;

public interface IClient {
    public void click(List<String> filter) throws Exception ;
    public void type(String content) throws Exception ;
    public Image captureScreen() throws Exception;
    public boolean checkUser(List<String> filter) throws Exception;
    public boolean isExist(List<String> filter) throws Exception;
}
