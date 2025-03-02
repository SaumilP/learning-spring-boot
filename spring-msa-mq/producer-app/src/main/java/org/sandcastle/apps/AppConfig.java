package org.sandcastle.apps;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static final String QUEUE_NAME = "svcQ";

    @Bean
    public Queue svcQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}
