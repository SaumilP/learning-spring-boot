package org.sandcastle.apps;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.sandcastle.apps.broker.sender.MessageSender;
import org.sandcastle.apps.generator.AsyncRecordGenerator;
import org.sandcastle.apps.generator.Source;
import org.sandcastle.apps.model.Record;
import org.sandcastle.apps.utils.AsyncTaskResultIterator;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;

@Singleton
public class DefaultProcessor {
    private static final int DEFAULT_CHUNK_SIZE = 5_000;
    private static final int DEFAULT_SLEEP_IN_MS = 1_000;
    private static final int MAX_CPU_CORES = 3;

    private final AsyncTaskResultIterator<Record> listener;
    private final AsyncRecordGenerator recordsGenerator;

    private MessageSender streamSender;
    private MessageSender amqpSender;
    private boolean useAMQP;

    @Inject
    public DefaultProcessor(Source sourceService,
                            @Named("StreamsMessageSender") MessageSender streamSender,
                            @Named("AmqpMessageSender") MessageSender amqpSender) {
        this.amqpSender = amqpSender;
        this.streamSender = streamSender;
        this.listener = new AsyncTaskResultIterator<>();
        this.recordsGenerator = new AsyncRecordGenerator(listener, sourceService, DEFAULT_CHUNK_SIZE, DEFAULT_SLEEP_IN_MS);
    }

    public DefaultProcessor configure(boolean useAMQP){
        this.useAMQP = useAMQP;
        return this;
    }

    private MessageSender getMessageSender(){
        return useAMQP ? amqpSender : streamSender;
    }

    public void start() {
        recordsGenerator.start();

        if (useAMQP) {
            // Parallel Stream backed by Max 3 CPU Cores; As records arrive, they gets sent to downstream MessageBroker
            listener.unbufferedParallelStream(MAX_CPU_CORES).forEach(getMessageSender()::send);

            // Start printing as records arrive from Stream (Fastest one out of both)
            // listener.unbufferedStream().forEach(System.out::println);

            // Alternate version, Wait for all records are available (RAM heavy)
            // listener.bufferedStream().forEach(System.out::println);
        } else {

            // Parallel stream backed by Max 3 CPU Cores; As records arrive, they gets sent as stream to downstream MessageBroker
            AtomicInteger counter = new AtomicInteger();
            listener.unbufferedParallelStream(MAX_CPU_CORES).collect(groupingBy(x->counter.getAndIncrement()/DEFAULT_CHUNK_SIZE))
                    .values()
                    .forEach(getMessageSender()::sendAll);
        }
    }
}
