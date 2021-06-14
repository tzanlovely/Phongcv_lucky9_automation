package testsets;

import io.Excel;
import model.Status;
import model.Step;
import model.TestCase;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import utilities.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChannelTestSet implements TestSet {
    private Excel xlsx;
    private List<TestCase> testSet;

    public ChannelTestSet(Excel xlsx) {
        this.xlsx = xlsx;
    }

    private TestCase loadTestCase(Sheet sheet) {
        TestCase testCase = new TestCase();

        Object[][] information = xlsx.read(sheet, 0, 1, 5, 1);
//        for(int i=0; i<6; i++) {
//            System.out.println(information[i][0]);
//        }

        testCase.setTitle((String) information[0][0]);
        testCase.setNumberClients(Integer.parseInt((String) information[1][0]));
        testCase.setStt(Integer.parseInt((String) information[2][0]));
        testCase.setResult((String) information[3][0]);
        testCase.setLinkLog((String) information[4][0]);
        testCase.setCheatID((String)information[5][0]);

        Object[][] stepExact = xlsx.read(sheet, 11, 0, 3);
        Step[] steps = new Step[stepExact.length];

        for (int i=0; i<stepExact.length; i++) {
            steps[i] = (extractStep(stepExact[i]));
        }

        testCase.setSteps(steps);

        return testCase;
    }

    private Step extractStep(Object[] target) {
        Step step = new Step();

//        for(int i=0; i<4; i++) {
//            System.out.println(target[i]);
//        }
        Class[] listParamsClass = null;

        if (target[3] != null) {
            List<Object> params = Arrays.asList(((String) target[3]).split(","));
            step.setParams(params.toArray());
        }

        step.setStepID((double) target[0]);
        step.setTarget(AdvanceClient.getInstance(Integer.parseInt((String)target[1])));
        try {
            Method action = null;
            action = MethodExtractor.getPublicMethodByName(AdvanceClient.class, (String) target[2]);
            step.setAction(action);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("khong the lay method: "+(String)target[2]);
        }
        return step;
    }

    private ArrayList<Status> getStatus(Sheet sheet) {
        Object[][] result = xlsx.read(sheet, 1, 0, 1);
        ArrayList<Status> statuses = new ArrayList<>();

        for (int i=0; i<result.length; i++) {
            Status status = new Status();
            status.setTestCase((String)result[i][0]);
            status.setStatus((String)result[i][1]);

//            System.out.println(status.getTestCase()+":"+status.getStatus());

            statuses.add(status);
        }

        return statuses;
    }

    @Override
    public void loadTestSet() {
        JSONObject config = Json.read(System.getProperty("user.dir") + "\\Config\\config.json");
        String fileName = config.getString("channelTest");
        Workbook workbook = xlsx.openFile(fileName);
        ArrayList<TestCase> testCases = new ArrayList<>();

        ArrayList<Status> statuses = getStatus(xlsx.openSheet(workbook, "Status"));

        for (Status status: statuses) {
            if (status.getStatus().equals("test")) {
                testCases.add(loadTestCase(xlsx.openSheet(workbook, status.getTestCase())));
            }
        }
        this.testSet = testCases;
    }

    @Override
    public List<TestCase> getTestSet() {
        return testSet;
    }
}
