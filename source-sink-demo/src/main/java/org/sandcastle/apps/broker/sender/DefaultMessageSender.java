package org.sandcastle.apps.broker.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.sandcastle.apps.model.Record;

import java.nio.charset.StandardCharsets;

public class DefaultMessageSender implements MessageSender {

    private static final String QUEUE_NAME = "records.Q";
    @Override
    public void send(Record record) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");

            try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = record.toString();
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                // System.out.println("["+record.getId()+"] Sent");
            }
        } catch (Exception ex){
            System.err.println("Failed to send message - " + record.getId());
        }
    }
}
