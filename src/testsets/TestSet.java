package testsets;

import model.TestCase;

import java.util.List;

public interface TestSet {
    public void loadTestSet();
    public List<TestCase> getTestSet();
}
