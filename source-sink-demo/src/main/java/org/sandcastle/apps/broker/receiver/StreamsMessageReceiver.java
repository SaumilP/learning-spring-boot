package org.sandcastle.apps.broker.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class StreamsMessageReceiver implements MessageReceiver {
    private static final String STREAM_QUEUE_NAME = "bulk-msg-send-stream";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override public void listen() {
        try {
            receive();
        } catch (Exception e){
            // do nothing for now
            e.printStackTrace();
            System.err.println("Failed to start receiving streamed messages");
        }
    }

    void receive() throws Exception {
        int messageCount = 100000;
        AtomicLong counter = new AtomicLong(0);
        System.out.println("Starting stream consuming...");

        try(Environment environment = Environment.builder().uri("rabbitmq-stream://guest:guest@localhost:5552/%2f").build()) {

            //CountDownLatch consumeLatch = new CountDownLatch(messageCount);
            try(Consumer consumer = environment.consumerBuilder()
                    .stream(STREAM_QUEUE_NAME)
                    .name("default-msg-reader")
                    .autoTrackingStrategy()
                    .builder()
                    .offset(OffsetSpecification.first())
                    .messageHandler((offset, message) -> {
                        String msg = new String(message.getBodyAsBinary());
                        System.out.println(msg + " received");
                        //consumeLatch.countDown();
                        counter.getAndIncrement();
                    })
                    .build()) {
                //consumeLatch.await(500, TimeUnit.MILLISECONDS);
                System.out.println(counter.get() + " messages received");
            }
        }
    }
}
