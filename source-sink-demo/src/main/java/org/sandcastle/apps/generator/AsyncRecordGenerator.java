package org.sandcastle.apps.generator;

import org.sandcastle.apps.model.Record;
import org.sandcastle.apps.utils.TaskListener;

public class AsyncRecordGenerator extends Thread {
    private final TaskListener<Record> taskListener;
    private final Source dataSource;
    private final int recordChunk;
    private final int sleepInMs;

    public AsyncRecordGenerator(TaskListener<Record> taskListener, Source dataSource, int recordChunk, int sleepInMs) {
        this.taskListener = taskListener;
        this.dataSource = dataSource;
        this.recordChunk = recordChunk;
        this.sleepInMs = sleepInMs;
    }

    @Override
    public void run() {
        try {
            int cnt = 0, recs = 0;
            do {
                dataSource.generate()
                        .limit(recordChunk)
                        .forEach(taskListener::onTaskResult);
                recs+= recordChunk;
                Thread.sleep(sleepInMs);
                cnt++;
            } while (cnt <= 10);

            // last event to stop loop
            dataSource.generate().limit(1)
                    .peek(record -> record.setLastRecord(true))
                    .forEach(taskListener::onTaskResult);
            recs++;
            System.out.println("Total Generated Records = " + recs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
