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

public class Main {
    private static IRunTest runTest = new BasicRunTest();
    private static IResultWriting resultWriting = new BasicWriting(new TXTimpl(), new DOCX(), new XLSX());
    //channelTest
    //calcGoldTest
    private static String[] names = new String[]{"calcGoldTest", "channelTest"};
    private static ITestLoading iTestLoading = new BasicTestLoading(new XLSX());
    public static void main(String[] args) throws Exception {
        LDClient.usingCache=false;
        for(String name: names) {
            TestSet testSet = iTestLoading.getTestSet(name);
            TestResult testResult = runTest.runTest(testSet);
            resultWriting.writeOut(testResult, name, 0.5F);
        }
    }
}
