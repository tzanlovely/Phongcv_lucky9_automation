package model;

import java.util.List;

public class TestSet {
    private List<TestCase> testingCase;
    private List<TestCase> ignoreCase;

    public List<TestCase> getTestingCase() {
        return testingCase;
    }

    public void setTestingCase(List<TestCase> testingCase) {
        this.testingCase = testingCase;
    }

    public List<TestCase> getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(List<TestCase> ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
