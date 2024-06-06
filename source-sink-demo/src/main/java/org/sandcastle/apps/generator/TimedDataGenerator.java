package org.sandcastle.apps.generator;

import org.sandcastle.apps.model.Record;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

// Another value generator class
@Deprecated
public class TimedDataGenerator {
    private final AtomicInteger value = new AtomicInteger();
    private final Timer valueUpdater;

    public TimedDataGenerator(long updateRateInMs) {
        valueUpdater = new Timer();
        valueUpdater.schedule(createTimerTask(), 0, updateRateInMs);
    }

    private TimerTask createTimerTask() {
        return new TimerTask() {
            public void run() {
                value.set(generateNewValue());
            }
        };
    }

    private static int generateNewValue() {
        Random r = new Random();
        int low = 0;
        int high = 30;
        return r.nextInt(high-low) + low;
    }

    public Record getValue() {
        return new Record(UUID.randomUUID().toString(), LocalDateTime.now().toEpochSecond(ZoneOffset.of("+02:00")), value.intValue());
    }
}
