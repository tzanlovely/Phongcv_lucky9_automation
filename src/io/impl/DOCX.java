package io.impl;

import io.Word;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.sikuli.script.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class DOCX implements Word {
    @Override
    public void write(String fileName, List<String> content) {
        try {
            XWPFDocument xwpfDocument;
            File file = new File(System.getProperty("user.dir") + "\\FileOutput\\"+fileName);
            if (!file.exists()) file.createNewFile();
            if (file.length()>0) {
                FileInputStream fis = new FileInputStream(file);
                xwpfDocument = new XWPFDocument(OPCPackage.open(fis));
            } else {
                xwpfDocument = new XWPFDocument();
            }

            for (String writeIn: content) {
                XWPFParagraph paragraph = xwpfDocument.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(writeIn);
            }
            FileOutputStream out = new FileOutputStream(file);
            xwpfDocument.write(out);
            out.close();
            xwpfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printImage(String fileName, Image image) {
        try {
            File file = new File(System.getProperty("user.dir") + "\\FileOutput\\"+fileName);
            image.save("tmp.png", System.getProperty("user.dir") + "\\FileOutput\\");
            File imgFile = new File(System.getProperty("user.dir") + "\\FileOutput\\"+"tmp.png");
            if (!file.exists()) file.createNewFile();
            XWPFDocument xwpfDocument;
            if (file.length()>0) {
                FileInputStream fis = new FileInputStream(file);
                xwpfDocument = new XWPFDocument(OPCPackage.open(fis));
            } else {
                xwpfDocument = new XWPFDocument();
            }

            BufferedImage bufferedImage = ImageIO.read(imgFile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            XWPFParagraph paragraph = xwpfDocument.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile.getName(), Units.toEMU(width)/2, Units.toEMU(height)/2);

            FileOutputStream out = new FileOutputStream(file);
            xwpfDocument.write(out);
            out.close();
            xwpfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
