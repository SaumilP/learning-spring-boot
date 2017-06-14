package org.sandcastle.apps.controllers;

import org.sandcastle.apps.model.Greet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class GreetingsController {
    @GetMapping("/hello")
    private ResponseEntity<Greet> greet(@RequestParam String name) {
        Objects.requireNonNull(name);

        Greet greetings = new Greet(name);
        return new ResponseEntity<Greet>(greetings, HttpStatus.OK);
    }
}
