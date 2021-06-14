package utilities;

import org.json.JSONObject;
import org.sikuli.script.Location;

public class Node {
    public float x;
    public float y;
    public float width;
    public float height;

    public Node(JSONObject jsonObject){
        this.width=jsonObject.getFloat("width");
        this.height=jsonObject.getFloat("height");
        this.x=jsonObject.getFloat("x")+(0.5f-jsonObject.getFloat("anchorX"))*this.width;
        this.y=jsonObject.getFloat("y")+(0.5f-jsonObject.getFloat("anchorY"))*this.height;
    }

    public Location getLocation(){
        return new Location(x,y);
    }
}
