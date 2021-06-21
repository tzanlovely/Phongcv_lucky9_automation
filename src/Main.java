import client.LDClient;
import io.impl.DOCX;
import io.impl.TXTimpl;
import io.impl.XLSX;
import model.TestResult;
import model.TestSet;
import resultwriting.BasicWriting;
import resultwriting.IResultWriting;
import runtest.BasicRunTest;
import runtest.IRunTest;
import testloading.BasicTestLoading;
import testloading.ITestLoading;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static IRunTest runTest = new BasicRunTest();
    private static IResultWriting resultWriting = new BasicWriting(new TXTimpl(), new DOCX(), new XLSX());
    private static List<TestSet> testSets = new ArrayList<>();
    private static String[] names = new String[]{"calcGoldTest"};
    private static ITestLoading iTestLoading = new BasicTestLoading(new XLSX());
    //channelTest
    //calcGoldTest
    public static void main(String[] args) throws Exception {
        LDClient.usingCache=true;
        for(String name: names) {
            TestSet testSet = iTestLoading.getTestSet(name);
            testSets.add(testSet);
        }

        for (int i=0; i<testSets.size(); i++) {
            TestResult testResult = runTest.runTest(testSets.get(i));
            resultWriting.writeOut(testResult, names[i], 0.5F);
        }
    }
}
