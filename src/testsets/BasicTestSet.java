package testsets;

import client.IClient;
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

public class BasicTestSet implements ITestSet {
    private Excel xlsx;
    private List<TestCase> testSet;
    private List<Status> statuses;

    public BasicTestSet(Excel xlsx, String name) {
        this.xlsx = xlsx;
        loadTestSet(name);
    }

    private TestCase loadTestCase(Sheet sheet) {
        TestCase testCase = new TestCase();
        Object[][] information = xlsx.read(sheet, 0, 1, 5, 1);

        if (information[0][0] != null) testCase.setTitle(((String) information[0][0]).trim());
        if (information[1][0] != null) testCase.setNumberClients(Integer.parseInt(((String) information[1][0]).trim()));
        testCase.setTypeOfClient(((String) information[2][0]).trim());
        if (information[3][0] != null) testCase.setResult(((String) information[3][0]).trim());
        if (information[4][0] != null) testCase.setLinkLog(((String) information[4][0]).trim());
        if (information[5][0] != null) testCase.setCheatID(((String)information[5][0]).trim());

        Object[][] stepExact = xlsx.read(sheet, 11, 0, 3);
        Step[] steps = new Step[stepExact.length];

        for (int i=0; i<stepExact.length; i++) {
            steps[i] = (extractStep(stepExact[i], testCase.getTypeOfClient()));
        }

        testCase.setSteps(steps);

        return testCase;
    }

    private Step extractStep(Object[] target, String typeOfClient) {
        for (int i=0; i<target.length; i++) {
            if(target[i] != null && target[i] instanceof String) target[i] = ((String)target[i]).trim();
        }

        Step step = new Step();

        step.setStepID((double) target[0]);

        try {
            int nClient = Integer.parseInt((String)target[1]);
            Class cls = Class.forName("client."+typeOfClient);
            Method action = MethodExtractor.getPublicMethodByName(cls, (String) target[2]);
            Method getInstance = MethodExtractor.getPublicMethodByName(cls, "getInstance");
            step.setAction(action);
            step.setTarget((IClient) getInstance.invoke(null, nClient));
        } catch (Exception e) {
            try {
                Class cls = Class.forName("func."+target[1]);
                Method action = MethodExtractor.getPublicMethodByName(cls, (String) target[2]);
                step.setAction(action);
                step.setTarget(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (target[3] != null) {
            List<String> params = Arrays.asList(((String) target[3]).split("\\|"));

            if (step.getTarget()!=null) {
                List<String> parameters = new ArrayList<>();
                for(String param: params) {
                    parameters.add(param.trim());
                }
                step.setParams(parameters.toArray());
            } else {
                try {
                    Class cls = Class.forName("client."+typeOfClient);
                    Method method = MethodExtractor.getPublicMethodByName(cls, "getInstance");
                    List<Object> parameters = new ArrayList<>();
                    parameters.add(method.invoke(null, Integer.parseInt(params.get(0))));
                    for (int i=1; i<params.size(); i++) {
                        parameters.add(params.get(i).trim());
                    }
                    step.setParams(parameters.toArray());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return step;
    }

    private ArrayList<Status> getStatus(Sheet sheet) {
        Object[][] result = xlsx.read(sheet, 1, 0, 1);
        ArrayList<Status> statuses = new ArrayList<>();

        for (int i=0; i<result.length; i++) {
            Status status = new Status();
            status.setTestCase(((String)result[i][0]).trim());
            status.setStatus(((String)result[i][1]).trim());

            statuses.add(status);
        }

        return statuses;
    }

    private void loadTestSet(String name) {
        JSONObject config = Json.read(System.getProperty("user.dir") + "\\Config\\config.json");
        String fileName = config.getString(name);
        Workbook workbook = xlsx.openFile(fileName);
        List<TestCase> testCases = new ArrayList<>();

        statuses = getStatus(xlsx.openSheet(workbook, "Status"));

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

    @Override
    public int getNTest() {
        return statuses.size();
    }
}
