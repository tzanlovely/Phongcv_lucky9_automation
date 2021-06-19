package resultwriting;

import io.Excel;
import io.FileReport;
import io.Word;
import io.impl.DOCX;
import io.impl.TXT;
import io.impl.XLSX;
import model.TestCase;
import model.TestResult;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import utilities.Json;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BasicWriting implements IResultWriting {
    private FileReport fileReport;
    private Word word;

    public BasicWriting(FileReport fileReport, Word word) {
        this.fileReport = fileReport;
        this.word = word;
    }

    @Override
    public void writeOut(TestResult testResult, String fileName) throws Exception {
        Calendar calendar = Calendar.getInstance();
        String txtFileName = String.format("%s_%d.%d_%d.%d.%d.%s",fileName,calendar.get(Calendar.MINUTE),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),".txt");
        String wordFileName = txtFileName.replace("txt","docx");

        List<String> content = new LinkedList<>();
        content.add("Tổng số test case "+testResult.getNCase());
        content.add("Tổng số test fail "+testResult.getNFailCase());
        content.add("Tổng số case đã test trong lần chạy này: "+testResult.getNTestedCase());
        content.add("Tổng số case fail trong lần chạy này: " +testResult.getFailCases().size());
        fileReport.write(txtFileName, content);

        for(TestCase testCase: testResult.getFailCases()) {
            word.write(wordFileName, Arrays.asList(new String[]{testCase.getId(), "Step: "+testCase.getFailStep().getStepID()}));
            word.printImage(wordFileName, testCase.getFailImg());
        }

        String excelName = Json.read(System.getProperty("user.dir") + "\\Config\\config.json").getString(fileName);
        Excel excel = new XLSX();
        Workbook workbook = excel.openFile(excelName);
        for(TestCase testCase: testResult.getFailCases()) {
            Sheet sheet = excel.openSheet(workbook, testCase.getId());
            excel.write(sheet, 3, 1, 3, 1, new String[][]{{testCase.getResult()}});
        }
        for (TestCase testCase: testResult.getPassCases()) {
            Sheet sheet = excel.openSheet(workbook, testCase.getId());
            excel.write(sheet, 3, 1, 3, 1, new String[][]{{testCase.getResult()}});
        }
        excel.export(workbook, "tmp.xlsx");
    }
}
