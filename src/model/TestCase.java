package model;

import org.sikuli.script.Image;

public class TestCase {
    private String id;
    private String title;
    private int numberClients;
    private String typeOfClient;
    private String result;
    private String linkLog;
    private Step[] steps;
    private String cheatID;
    private Step failStep;
    private Image failImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Step getFailStep() {
        return failStep;
    }

    public void setFailStep(Step failStep) {
        this.failStep = failStep;
    }

    public Image getFailImg() {
        return failImg;
    }

    public void setFailImg(Image failImg) {
        this.failImg = failImg;
    }

    public String getResult() {
        return result;
    }

    public String getCheatID() {
        return cheatID;
    }

    public void setCheatID(String cheatID) {
        this.cheatID = cheatID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeOfClient() {
        return typeOfClient;
    }

    public void setTypeOfClient(String stt) {
        this.typeOfClient = stt;
    }

    public String isResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLinkLog() {
        return linkLog;
    }

    public void setLinkLog(String linkLog) {
        this.linkLog = linkLog;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public int getNumberClients() {
        return numberClients;
    }

    public void setNumberClients(int numberClients) {
        this.numberClients = numberClients;
    }

    @Override
    public String toString() {
        String a = "TestCase{" +
                "title='" + title + '\'' +
                ", numberClients=" + numberClients +
                ", stt=" + typeOfClient +
                ", result='" + result + '\'' +
                ", linkLog='" + linkLog + '\'' +
                '}'+"\n";

        for (Step step: steps) {
            a += step.toString() + "\n";
        }
        return a;
    }
}
