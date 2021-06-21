package resultwriting;

import io.Excel;
import io.TXT;
import io.Word;
import io.impl.XLSX;
import model.TestCase;
import model.TestResult;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import utilities.Json;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BasicWriting implements IResultWriting {
    private TXT TXT;
    private Word word;
    private Excel excel;

    public BasicWriting(TXT TXT, Word word, Excel excel) {
        this.TXT = TXT;
        this.word = word;
        this.excel = excel;
    }

    @Override
    public void writeOut(TestResult testResult, String fileName, float imgScale) throws Exception {
        Calendar calendar = Calendar.getInstance();
        String txtFileName = String.format("%s_%d.%d_%d.%d.%d.%s",fileName,calendar.get(Calendar.MINUTE),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),".txt");
        String wordFileName = txtFileName.replace("txt","docx");

        List<String> content = new LinkedList<>();
        content.add("Tổng số test case "+testResult.getNCase());
        content.add("Tổng số test fail "+testResult.getNFailCase());
        content.add("Tổng số case đã test trong lần chạy này: "+testResult.getNTestedCase());
        content.add("Tổng số case fail trong lần chạy này: " +testResult.getFailCases().size());
        TXT.write(txtFileName, content);

        for(TestCase testCase: testResult.getFailCases()) {
            word.write(wordFileName, Arrays.asList(new String[]{testCase.getId(), "Step: "+testCase.getFailStep().getStepID()}));
            word.printImage(wordFileName, testCase.getFailImg(), imgScale);
        }

        String excelName = Json.read(System.getProperty("user.dir") + "\\Config\\config.json").getString(fileName);
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
