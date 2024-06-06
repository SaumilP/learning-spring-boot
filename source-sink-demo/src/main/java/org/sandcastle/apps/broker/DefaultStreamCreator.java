package org.sandcastle.apps.broker;

import com.google.inject.Singleton;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;

import java.time.Duration;

@Singleton
public class DefaultStreamCreator {

    private static final String DEFAULT_STREAM_Q = "bulk-msg-send-stream";
    public void createStream() {
        try(Environment environment = Environment.builder().uri("rabbitmq-stream://guest:guest@localhost:5552/%2f").build()) {
            System.out.println("About to create stream");
            environment.streamCreator()
                    .stream(DEFAULT_STREAM_Q)
                    .maxAge(Duration.ofSeconds(600))
                    .maxLengthBytes(ByteCapacity.MB(10))
                    .maxSegmentSizeBytes(ByteCapacity.MB(5))
                    .create();
        }catch (Exception ex){
            System.out.println("Failed to create stream, ex=" + ex);
        }
    }

    private boolean isStreamCreated(Environment environment, String stream) {
        try {
            return environment.queryStreamStats(stream).committedChunkId() > 0;
        } catch (Exception ex) {
            return false;
        }
    }
}
