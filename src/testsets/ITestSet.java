package testsets;

import model.TestCase;

import java.util.List;

public interface ITestSet {
    public List<TestCase> getTestSet();
    public int getNTest();
}
