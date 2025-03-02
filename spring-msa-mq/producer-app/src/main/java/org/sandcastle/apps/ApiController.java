package org.sandcastle.apps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final MessageProducer messageProducer;

    public ApiController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping(value = "/send/{message}")
    public String sendMessage(@PathVariable("message") String message) {
        String respMessage = messageProducer.sendMessage(message);
        System.out.println(String.format("Sent[%s] Received [%s]", message, respMessage));
        return respMessage;
    }
}
