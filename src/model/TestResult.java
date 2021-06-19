package model;


import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private int nCase;
    private int nTestedCase;
    private int nFailCase;
    private List<TestCase> failCases;
    private List<TestCase> passCases;

    public TestResult() {
        nCase = 0;
        nTestedCase = 0;
        nFailCase = 0;
        failCases = new ArrayList<>();
        passCases = new ArrayList<>();
    }

    public int getNFailCase() {
        return nFailCase;
    }

    public void setNFailCase(int nFailCase) {
        this.nFailCase = nFailCase;
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

    public List<TestCase> getPassCases() {
        return passCases;
    }

    public void setPassCases(List<TestCase> passCases) {
        this.passCases = passCases;
    }
}
