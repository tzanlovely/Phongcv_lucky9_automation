package utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class AdbLog {

    public String adbAddress;
    private String[] commands;

    public AdbLog(String adbAddress){
        this.adbAddress=adbAddress;
        commands = new String[]{"adb","-s",adbAddress,"logcat","-t","50000"};
    }

    private String getAdbLog() {
        Process process= null;
        try {
            process = Runtime.getRuntime().exec(commands);
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

        log = log.substring(log.lastIndexOf("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"), log.lastIndexOf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));


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
        System.out.println(userInfo);

        return new JSONObject(userInfo);
    }


    synchronized public JSONArray getPositionLog() {
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

        System.out.println("position log: \n" + positionLog);

        return new JSONArray(positionLog);
    }


}


