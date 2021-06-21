package client;

import org.sikuli.script.Image;

import java.util.List;

public interface IClient {
    public void click(List<String> filter) throws Exception ;
    public void type(String content) throws Exception;
    public void dragDrop(String begin, String end) throws Exception;
    public Image captureScreen() throws Exception;
    public boolean checkUser(List<String> filter) throws Exception;
    public boolean isExist(List<String> filter) throws Exception;
}
