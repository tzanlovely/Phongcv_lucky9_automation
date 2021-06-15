package model;


import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private int nCase;
    private int nTestedCase;
    private List<TestCase> failCases;

    public TestResult() {
        nCase = 0;
        nTestedCase = 0;
        failCases = new ArrayList<>();
    }

    public int getNCase() {
        return nCase;
    }

    public void setNCase(int nCase) {
        this.nCase = nCase;
    }

    public int getNTestedCase() {
        return nTestedCase;
    }

    public void setNTestedCase(int nTestCase) {
        this.nTestedCase = nTestCase;
    }

    public List<TestCase> getFailCases() {
        return failCases;
    }

    public void setFailCases(List<TestCase> failCases) {
        this.failCases = failCases;
    }
}
