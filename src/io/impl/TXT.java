package io.impl;

import io.FileReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TXT implements FileReport {

    @Override
    public void write(String fileName, List<String> content) {
        try {
            FileWriter fw = new FileWriter(new File(System.getProperty("user.dir") + "\\FileOutput\\"+fileName));

            for(String str: content) {
                fw.write(str);
                fw.write('\n');
            }

            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
