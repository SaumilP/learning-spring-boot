package org.sandcastle.apps.utils;

public class TaskResultImpl implements TaskResult {
    private boolean lastResult;
    private String  name;

    public TaskResultImpl(boolean lastResult, String name) {
        this.lastResult = lastResult;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaskResultImpl{" +
                "lastResult=" + lastResult +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isLastResult() {
        return lastResult;
    }
}
