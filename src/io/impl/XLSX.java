package io.impl;

import io.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XLSX implements Excel {
    private static final String filePath = System.getProperty("user.dir") + "\\ExcelFile\\";

    public XLSX() {
    }

    @Override
    public Workbook openFile(String fileName) {
        Workbook workbook = null;
        try {
            FileInputStream inputStream = new FileInputStream(filePath+fileName);
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workbook;
    }

    @Override
    public Sheet openSheet(Workbook workbook, String sheetName) {
        Sheet sheet = null;

        try {
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sheet;
    }

    @Override
    public Object[][] read(Sheet sheet, int row_begin, int column_begin, int row_end, int column_end) {
        Object[][] result = new Object[row_end-row_begin+1][column_end-column_begin+1];

        System.out.println("read sheet: "+sheet.getSheetName());

        for (int i=row_begin; i<=row_end; i++) {
            Row row = sheet.getRow(i);
            for (int j=column_begin; j<=column_end; j++) {
                Cell cell = row.getCell(j);
                result[i-row_begin][j-column_begin] = getCellValue(cell);
            }
        }

        return result;
    }

    @Override
    public Object[][] read(Sheet sheet, int rowBegin, int columnBegin, int columnEnd) {
        int rowEnd = sheet.getLastRowNum();

        return read(sheet, rowBegin, columnBegin, rowEnd, columnEnd);
    }

    @Override
    public void write(Sheet sheet, int rowBegin, int columnBegin, int rowEnd, int columnEnd, String[][] content) {
        int iContent=0, jContent=0;
        for(int i=rowBegin; i<=rowEnd; i++) {
            Row row = sheet.getRow(i);
            if (row == null) row = sheet.createRow(i);
            for (int j=columnBegin; j<=columnEnd; j++) {
                Cell cell = row.getCell(j);
                if (cell!=null) row.removeCell(cell);
                cell = row.createCell(j, CellType.STRING);
                cell.setCellValue(content[iContent][jContent]);
                jContent++;
            }
            iContent++;
        }
    }

    @Override
    public void export(Workbook workbook, String fileName) {
        File newFile = new File(filePath+fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) return null;

        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            default:
                break;
        }

        return cellValue;
    }
}
