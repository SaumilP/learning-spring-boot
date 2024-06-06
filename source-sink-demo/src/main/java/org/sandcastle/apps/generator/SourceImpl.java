package org.sandcastle.apps.generator;

import org.sandcastle.apps.model.Record;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class SourceImpl implements Source {

//    private static final long GENERATION_RATE_INTERVAL_IN_MS = 1_000L;
//    private final TimedDataGenerator generator = new TimedDataGenerator(GENERATION_RATE_INTERVAL_IN_MS);

    /**
     * Generates random objects every few seconds
     * @return
     */
    @Override
    public Stream<Record> generate() {
        final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10000);
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.scheduleAtFixedRate(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            queue.offer(random.nextInt(50000));
        }, 0, 1, TimeUnit.MILLISECONDS);

        return Stream.generate(() -> {
            try {
                return queue.take();
            } catch (InterruptedException e) {
            }
            return -1;
        }).map(num -> new Record(UUID.randomUUID().toString(), LocalDateTime.now().toEpochSecond(ZoneOffset.of("+02:00")), num));
    }
}
