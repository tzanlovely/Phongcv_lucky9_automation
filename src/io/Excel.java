package io;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.dev.XSSFSave;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface Excel {
    public Workbook openFile(String fileName);
    public Sheet openSheet(Workbook workbook, String sheetName);
    public Object[][] read(Sheet sheet, int rowBegin, int columnBegin, int rowEnd, int columnEnd);
    public Object[][] read(Sheet sheet, int rowBegin, int columnBegin, int columnEnd);
    public void write(Sheet sheet, int rowBegin, int columnBegin, int rowEnd, int columnEnd, String[][] content);
    public void export(Workbook workbook, String fileName);
}
