package runtest;

import model.TestResult;
import testsets.ITestSet;

public interface IRunTest {
    public TestResult runTest(ITestSet ITestSet) throws  Exception;
    public void writeOut(TestResult testResult, String fileName) throws  Exception;
}
