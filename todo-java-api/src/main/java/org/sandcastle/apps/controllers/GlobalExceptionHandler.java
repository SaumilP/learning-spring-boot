package org.sandcastle.apps.controllers;

import org.sandcastle.apps.exception.TodoNotFoundException;
import org.sandcastle.apps.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleTodoNotFoundException(TodoNotFoundException exception) {
        return Mono.just(new ErrorResponse(exception.getMessage()));
    }
}