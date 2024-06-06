package org.sandcastle.apps.broker.sender;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.Producer;
import com.rabbitmq.stream.compression.Compression;
import org.sandcastle.apps.model.Record;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamsMessageSender implements MessageSender {

    private static final String STREAM_QUEUE_NAME = "bulk-msg-send-stream";
    @Override
    public void sendAll(List<Record> records) {
        try {
            long startTime = System.currentTimeMillis();
            AtomicInteger msgCntr = new AtomicInteger(records.size());

            try(Environment environment = Environment.builder().uri("rabbitmq-stream://guest:guest@localhost:5552/%2f").build()) {
                CountDownLatch publishConfirmLatch = new CountDownLatch(msgCntr.get());

                try (Producer producer = environment.producerBuilder()
                        .name("default-msg-producer")
                        .confirmTimeout(Duration.ZERO)
                        .stream(STREAM_QUEUE_NAME)
                        .build()
                ) {

                    records.forEach(rec -> producer.send(
                            producer.messageBuilder()
                                    .properties()
                                    .correlationId(UUID.randomUUID())
                                    .absoluteExpiryTime(LocalDateTime.now().plusSeconds(30).toEpochSecond(ZoneOffset.UTC))
                                    .contentType("application/json")
                                    .messageBuilder()
                                    .addData(rec.toString().getBytes())
                                    .build(),
                            confirmationStatus -> {
                                if(confirmationStatus.isConfirmed()){
                                    // remove the message
                                }
                                publishConfirmLatch.countDown();
                            }
                    ));

                    // breathing time before next batch gets submitted
                    publishConfirmLatch.await(500, TimeUnit.MILLISECONDS);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.printf("Published %s messages in %d ms%n", msgCntr.get(), elapsedTime);

        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println("Failed to send messages");
        }
    }
}
