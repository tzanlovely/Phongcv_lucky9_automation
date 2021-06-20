package resultwriting;

import model.TestResult;

public interface IResultWriting {
    public void writeOut(TestResult testResult, String fileName, float imgScale) throws Exception;
}
