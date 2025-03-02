package org.sandcastle.apps;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Configuration
public class AppConfig {
    public static final String QUEUE_NAME = "svcQ";

    @Autowired
    private RpcService rpcService;

    @Bean
    public Queue exampleQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void listen(
            @Header(value = AmqpHeaders.REPLY_TO, required = false) String senderId,
            @Header(value = AmqpHeaders.CORRELATION_ID, required = false) String correlationId,
            String message) {
        System.out.println("Received message: " + message);
        if (senderId != null && correlationId != null) {
            String responseMessge = "Hey Bob, Message '" + message + "' received!";
            rpcService.sendResponse(senderId, correlationId, responseMessge);
        }
    }
}
