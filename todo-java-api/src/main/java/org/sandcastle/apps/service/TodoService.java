package org.sandcastle.apps.service;

import org.sandcastle.apps.models.Todo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoService {
    Mono<Todo> findTodoById(String id);

    Mono<Todo> searchTodoByTitle(String title);

    Flux<Todo> findAllTodos();

    Mono<Todo> saveTodo(Todo todo);

    Mono<Void> deleteTodoById(String id);
}