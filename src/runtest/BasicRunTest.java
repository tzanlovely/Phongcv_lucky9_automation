package runtest;

import client.IClient;
import func.Function;
import model.Step;
import model.TestCase;
import model.TestResult;
import model.TestSet;
import testloading.ITestLoading;
import utilities.ZPCheat;

import java.util.*;

public class BasicRunTest implements IRunTest {
    @Override
    public TestResult runTest(ITestLoading iTestLoading) throws Exception {
        TestSet testSet = iTestLoading.getTestSet();
        List<TestCase> testCases = testSet.getTestingCase();
        TestResult testResult = new TestResult();
        testResult.setNCase(testSet.getIgnoreCase().size()+testSet.getTestingCase().size());
        testResult.setNTestedCase(testCases.size());
        for (TestCase testCase: testSet.getIgnoreCase()) {
            if (testCase.getResult().toLowerCase(Locale.ROOT).equals("fail")) {
                testResult.setNFailCase(testResult.getNFailCase()+1);
            }
        }

        for (TestCase testCase: testCases) {
            ZPCheat zpCheat = new ZPCheat();
            ZPCheat.cheatData(Integer.parseInt(testCase.getCheatID()));
            Step[] steps = testCase.getSteps();
            boolean result = true;
            Set<IClient> iClientSet = new HashSet<>();
            for (Step step: steps) {
                Thread.sleep(1000);
                System.out.println("do step: "+ step.toString());
                try {
                    result = doStep(step, iClientSet);
                } catch (Exception e) {
                    result = false;
                }
                if (result == false) {
                    System.out.println("#########################################################################");
                    System.out.println("fail step "+ step.toString());
                    System.out.println("#########################################################################");
                    testCase.setResult("fail");
                    testCase.setFailStep(step);
                    if(step.getTarget()!=null) {
                        testCase.setFailImg(step.getTarget().captureScreen());
                        for(IClient iClient: iClientSet) {
                            Function.backToLobby(iClient);
                            Function.logOut(iClient);
                        }
                    } else if (step.getParams()!=null && step.getParams().length>0 && step.getParams()[0] instanceof IClient) {
                        testCase.setFailImg(((IClient)step.getParams()[0]).captureScreen());
                        for(IClient iClient: iClientSet) {
                            Function.backToLobby((IClient) step.getParams()[0]);
                            Function.logOut((IClient) step.getParams()[0]);
                            iClientSet.remove((IClient) step.getParams()[0]);
                        }
                    }
                    testResult.getFailCases().add(testCase);
                    break;
                }
            }
            if (result==true) {
                testCase.setResult("pass");
                testResult.setPassCases(testCases);
            }
        }

        testResult.setNFailCase(testResult.getNFailCase()+testResult.getFailCases().size());
        return testResult;
    }

    private boolean doStep(Step step, Set<IClient> iClientSet) throws Exception {
        try {
            System.out.println(this.toString());
            Object preResult = null;
            boolean isArray = false;

            if (step.getTarget() != null) {
                iClientSet.add(step.getTarget());
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
                if (step.getParams()!=null && step.getParams().length>1 && step.getParams()[0] instanceof IClient) iClientSet.add((IClient) step.getParams()[0]);
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
