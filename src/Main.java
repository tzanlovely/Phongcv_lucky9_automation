import io.impl.DOCX;
import io.impl.TXT;
import io.impl.XLSX;
import model.TestResult;
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
    private static IResultWriting resultWriting = new BasicWriting(new TXT(), new DOCX());
    private static List<ITestLoading> testSets = new ArrayList<>();
    private static String[] names = new String[]{"calcGoldTest"};
    //channelTest
    //calcGoldTest
    public static void main(String[] args) throws Exception {
        for(String name: names) {
            ITestLoading iTestLoading = new BasicTestLoading(new XLSX(), name);
            testSets.add(iTestLoading);
        }

        for (int i=0; i<testSets.size(); i++) {
            TestResult testResult = runTest.runTest(testSets.get(i));
            resultWriting.writeOut(testResult, names[i]);
        }
    }
}
