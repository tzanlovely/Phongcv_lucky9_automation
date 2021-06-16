
import client.IClient;
import client.LDClient;
import com.sun.jna.platform.win32.WinGDI;
import gnu.cajo.invoke.Client;
import io.impl.XLSX;
import model.TestResult;
import runtest.BasicRunTest;
import runtest.IRunTest;
import testsets.BasicTestSet;
import testsets.ITestSet;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static IRunTest runTest = new BasicRunTest();
    private static List<ITestSet> testSets = new ArrayList<>();
    private static String[] names = new String[]{"channelTest"};

    public static void main(String[] args) throws Exception {
        for(String name: names) {
            ITestSet iTestSet = new BasicTestSet(new XLSX(), name);
            testSets.add(iTestSet);
        }

        for (int i=0; i<testSets.size(); i++) {
            TestResult testResult = runTest.runTest(testSets.get(i));
            runTest.writeOut(testResult, names[i]);
        }
    }

}
