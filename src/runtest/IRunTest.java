package runtest;

import model.TestResult;
import testloading.ITestLoading;

public interface IRunTest {
    public TestResult runTest(ITestLoading ITestLoading) throws  Exception;
}
