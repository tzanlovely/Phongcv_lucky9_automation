package runtest;

import model.Step;
import model.TestCase;
import model.TestResult;
import testsets.ITestSet;
import utilities.AdvanceClient;
import utilities.ZPCheat;

import java.util.Arrays;
import java.util.List;

public class BasicRunTest implements IRunTest {
    @Override
    public TestResult runTest(ITestSet iTestSet) throws Exception {
        List<TestCase> testCases = iTestSet.getTestSet();
        TestResult testResult = new TestResult();
        testResult.setNCase(iTestSet.getNTest());
        testResult.setNTestedCase(testCases.size());

//        ZPCheat zpCheat = new ZPCheat();
        for (TestCase testCase: testCases) {
//            zpCheat.cheatData(Integer.parseInt(testCase.getCheatID()));
            Step[] steps = testCase.getSteps();
            for (Step step: steps) {
                Thread.sleep(1000);
                System.out.println(step.toString());
                boolean result = doStep(step);
                if (result == false) {
                    testCase.setResult("fail");
                    testCase.setFailStep(step);
                    testCase.setFailImg(step.getTarget().captureScreen());
                    ((AdvanceClient)step.getTarget()).backToLobby();
                    ((AdvanceClient)step.getTarget()).logOut();
                    testResult.getFailCases().add(testCase);
                    break;
                }
            }
        }
        return testResult;
    }

    @Override
    public void writeOut(TestResult testResult, String fileName) throws Exception {

    }

    private boolean doStep(Step step) {
        try {
            System.out.println(this.toString());
            Object preResult = null;
            boolean isArray = false;

            if (step.getParams() != null)
                for(Object param: step.getParams()) {
                    if (((String)param).contains(":")) {
                        isArray = true;
                        break;
                    }
                }

            if (isArray) {
                preResult = step.getAction().invoke(step.getTarget(), Arrays.asList(step.getParams()));
            } else {
                preResult = step.getAction().invoke(step.getTarget(), step.getParams());
            }
            if (preResult instanceof Boolean) {
                return (boolean) preResult;
            }
        } catch (Exception e) {
            System.out.println("****************************************************************************************");
            System.out.println("ERROR: "+this.toString());
            System.out.println("****************************************************************************************");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
