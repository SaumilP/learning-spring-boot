package org.sandcastle.apps;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendMessage(String message) {
        String responseMessage = (String) this.rabbitTemplate.convertSendAndReceive(AppConfig.QUEUE_NAME, message);
        System.out.println("Response message: " + responseMessage);
        return responseMessage;
    }
}
