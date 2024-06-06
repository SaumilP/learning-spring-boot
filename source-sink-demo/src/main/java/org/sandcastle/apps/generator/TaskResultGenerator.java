package org.sandcastle.apps.generator;

import org.sandcastle.apps.utils.TaskListener;
import org.sandcastle.apps.utils.TaskResult;
import org.sandcastle.apps.utils.TaskResultImpl;

public class TaskResultGenerator extends Thread {
    private final TaskListener<TaskResult> taskListener;
    private final int count;

    public TaskResultGenerator(TaskListener<TaskResult> taskListener, int count) {
        this.taskListener = taskListener;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < count; i++) {
                Thread.sleep(200);
                taskListener.onTaskResult(new TaskResultImpl(false, String.valueOf(i)));
            }
            Thread.sleep(200);
            taskListener.onTaskResult(new TaskResultImpl(true, String.valueOf(count)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
