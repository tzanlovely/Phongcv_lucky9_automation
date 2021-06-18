package runtest;

import client.IClient;
import func.Function;
import io.FileReport;
import io.Word;
import io.impl.DOCX;
import io.impl.TXT;
import model.Step;
import model.TestCase;
import model.TestResult;
import testsets.ITestSet;
import utilities.ZPCheat;

import java.util.*;

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
                }
                if (result == false) {
                    System.out.println("#########################################################################");
                    System.out.println("fail step "+ step.toString());
                    System.out.println("#########################################################################");
                    testCase.setResult("fail");
                    testCase.setFailStep(step);
                    if(step.getTarget()!=null) {
                        testCase.setFailImg(step.getTarget().captureScreen());
                        Function.backToLobby(step.getTarget());
                        Function.logOut(step.getTarget());
                    } else if (step.getParams()!=null && step.getParams().length>0 && step.getParams()[0] instanceof IClient) {
                        testCase.setFailImg(((IClient)step.getParams()[0]).captureScreen());
                        Function.backToLobby((IClient) step.getParams()[0]);
                        Function.logOut((IClient) step.getParams()[0]);
                    }
                    testResult.getFailCases().add(testCase);
                    break;
                }
            }
        }
        return testResult;
    }

    @Override
    public void writeOut(TestResult testResult, String fileName) throws Exception {
        Calendar calendar = Calendar.getInstance();
        String txtFileName = String.format("%s_%d.%d_%d.%d.%d.%s",fileName,calendar.get(Calendar.MINUTE),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),".txt");
        String wordFileName = txtFileName.replace("txt","docx");

        FileReport fileReport = new TXT();
        List<String> content = new LinkedList<>();
        content.add("Tổng số test case "+testResult.getNCase());
        content.add("Tổng số test fail "+testResult.getFailCases().size());
        fileReport.write(txtFileName, content);

        Word word = new DOCX();
        for(TestCase testCase: testResult.getFailCases()) {
            word.write(wordFileName, Arrays.asList(testCase.getId()));
            word.printImage(wordFileName, testCase.getFailImg());
        }
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
