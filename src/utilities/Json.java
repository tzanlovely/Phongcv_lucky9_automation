package utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Json {
    public static JSONObject read(String filePath){
        String data="";
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new JSONObject(data);
    }

    public static JSONObject findJsonObjectByFilter(JSONArray log, List<String> filter) {
        for (int j = 0; j < log.length(); j++) {
            boolean found=true;
            for (int i = 0; i < filter.size(); i++) {
                String field=filter.get(i).split(":")[0];
                String value=filter.get(i).split(":")[1];
                if (!log.getJSONObject(j).get(field).toString().equals(value))
                    found=false;
            }
            if(found) {
                return log.getJSONObject(j);
            }
        }
        return null;
    }

    public static boolean isInJSONObject(JSONObject jsonObject, List<String> filter) {
        for(int i=0; i<filter.size(); i++) {
            String field = filter.get(i).split(":")[0].trim();
            String value = filter.get(i).split(":")[1].trim();
            if(!jsonObject.get(field).toString().equals(value)) {
                return false;
            }
        }
        return true;
    }
}
