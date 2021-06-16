package runtest;

import func.Function;
import model.Step;
import model.TestCase;
import model.TestResult;
import testsets.ITestSet;
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

        for (TestCase testCase: testCases) {
            ZPCheat zpCheat = new ZPCheat();
            ZPCheat.cheatData(Integer.parseInt(testCase.getCheatID()));
            Step[] steps = testCase.getSteps();
            for (Step step: steps) {
                Thread.sleep(1000);
                System.out.println("do step: "+ step.toString());
                boolean result = true;
                try {
                    result = doStep(step);
                } catch (Exception e) {
                    result = false;
                    Function.backToLobby(step.getTarget());
                    Function.logOut(step.getTarget());
                }
                if (result == false) {
                    System.out.println("#########################################################################");
                    System.out.println("fail step "+ step.toString());
                    System.out.println("#########################################################################");
                    testCase.setResult("fail");
                    testCase.setFailStep(step);
                    testCase.setFailImg(step.getTarget().captureScreen());
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

    private boolean doStep(Step step) throws Exception {
        try {
            System.out.println(this.toString());
            Object preResult = null;
            boolean isArray = false;

            if (step.getTarget() != null) {
                if (step.getParams() != null) {
                    for (Object param : step.getParams()) {
                        if (((String) param).contains(":")) {
                            isArray = true;
                            break;
                        }
                    }
                }
                if (isArray) {
                    preResult = step.getAction().invoke(step.getTarget(), Arrays.asList(step.getParams()));
                } else {
                    preResult = step.getAction().invoke(step.getTarget(), step.getParams());
                }
            } else {
                preResult = step.getAction().invoke(step.getTarget(), step.getParams());
            }

            if (preResult instanceof Boolean) {
                return (boolean) preResult;
            }
        } catch (Exception e) {
            System.out.println("****************************************************************************************");
            System.out.println("ERROR: "+step.toString());
            System.out.println("****************************************************************************************");
            e.printStackTrace();
            throw e;
        }
        return true;
    }
}
