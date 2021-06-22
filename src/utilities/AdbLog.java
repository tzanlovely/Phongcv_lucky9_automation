package utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class AdbLog {
    public String adbAddress;

    public AdbLog(String adbAddress){
        this.adbAddress=adbAddress;
    }

    private String getAdbLog() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.now().minus(Duration.ofMillis(2000));
        String fromTime = dtf.format(localDateTime);

        Process process= null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"adb","-s",adbAddress,"logcat","-t", fromTime});
        } catch (IOException e) {
            e.printStackTrace();
        }
        String log = "";
        assert process != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line="";

        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            log += line;
            log += "\n";
        }



        log = log.substring(log.lastIndexOf("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"));


        return log;
    }


    public JSONObject getUserInfo() {
        String log = getAdbLog();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(log.split("\n")));
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).contains("QC_user_info_QC")) {
                lines.remove(i);
                i--;
            }
        }
        String userInfo = "";

        for (String s : lines) {
            userInfo += s.split("    ")[3];
        }

        return new JSONObject(userInfo);
    }


    public JSONArray getPositionLog() {
        String log = getAdbLog();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(log.split("\n")));
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).contains("QC_position_data_gui_QC")) {
                lines.remove(i);
                i--;
            }
        }

        String positionLog = "";

        for (String s : lines) {
            String[] parts = s.split("    ");
            positionLog += parts[3];
        }

        JSONArray jsonArray=new JSONArray();
        int start=0;
        int end=0;
        int index=0;
        String object="";
        while (true){
            start=positionLog.indexOf('{',index);
            end=positionLog.indexOf('}',index);
            if(start==-1||end==-1){
                break;
            }
            index=end+1;
            object=positionLog.substring(start,end+1);
            try{
                JSONObject jsonObject=new JSONObject(object);
                jsonArray.put(jsonObject);
            }
            catch (Exception e){
            }
        }
        return jsonArray;

    }


}


