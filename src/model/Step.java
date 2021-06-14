package model;

import org.sikuli.script.Image;
import utilities.Client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Step {
    private double stepID;
    private Client target;
    private Method action;
    private Object[] params;
    private Image beforeDoAction;
    private Image afterDoAction;
    private boolean result;

    public boolean doStep() {
        try {
            System.out.println(this.toString());
            Object preResult = null;
//*********************************************
            boolean isArray = false;

            if (params != null)
            for(Object param: params) {
                if (((String)param).contains(":")) {
                    isArray = true;
                    break;
                }
            }

            if (isArray) {
                preResult = action.invoke(target, Arrays.asList(params));
            } else {
                preResult = action.invoke(target, params);
            }
            if (preResult instanceof Boolean) {
                return (boolean) preResult;
            }
        } catch (Exception e) {
            System.out.println("*************************************************************");
            System.out.println("ERROR: "+this.toString());
            boolean isArray = false;
            for(Object param: params) {
                if (((String)param).contains(":")) {
                    isArray = true;
                    break;
                }
            }
            System.out.println(isArray+" "+e);
            System.out.println("*************************************************************");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public double getStepID() {
        return stepID;
    }

    public void setStepID(double stepID) {
        this.stepID = stepID;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Client target) {
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

    public Image getBeforeDoAction() {
        return beforeDoAction;
    }

    public void setBeforeDoAction(Image beforeDoAction) {
        this.beforeDoAction = beforeDoAction;
    }

    public Image getAfterDoAction() {
        return afterDoAction;
    }

    public void setAfterDoAction(Image afterDoAction) {
        this.afterDoAction = afterDoAction;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
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
