package org.sandcastle.apps.utils;

public interface TaskListener<T extends TaskResult>  {
    void onTaskResult(T result);
}
