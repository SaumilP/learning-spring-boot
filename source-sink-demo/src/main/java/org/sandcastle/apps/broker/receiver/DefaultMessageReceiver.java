package org.sandcastle.apps.broker.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.sandcastle.apps.model.Record;

import java.nio.charset.StandardCharsets;

@Singleton
public class DefaultMessageReceiver implements MessageReceiver {
    private static final String QUEUE_NAME = "records.Q";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override public void listen() {
        try {
            receive();
        } catch (Exception e){
            // do nothing for now
            e.printStackTrace();
            System.err.println("Failed to start receiving messages");
        }
    }

    void receive() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            // System.out.println(" ["+ message +"] Received");

            Record record = OBJECT_MAPPER.readValue(message, Record.class);
            System.out.println(" [" + record.getId() + "] Received");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
