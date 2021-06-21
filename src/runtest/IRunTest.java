package runtest;

import model.TestResult;
import model.TestSet;

public interface IRunTest {
    public TestResult runTest(TestSet testSet) throws  Exception;
}
