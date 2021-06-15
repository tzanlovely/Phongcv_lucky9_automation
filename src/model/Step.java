package model;
import utilities.Client;
import utilities.IClient;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Step {
    private double stepID;
    private IClient target;
    private Method action;
    private Object[] params;

    public double getStepID() {
        return stepID;
    }

    public void setStepID(double stepID) {
        this.stepID = stepID;
    }

    public IClient getTarget() {
        return target;
    }

    public void setTarget(IClient target) {
        this.target = target;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Step{" +
                "stepID=" + stepID +
                ", target=" + target +
                ", action=" + action +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
