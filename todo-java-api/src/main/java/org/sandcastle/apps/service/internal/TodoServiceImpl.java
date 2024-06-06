package org.sandcastle.apps.service.internal;

import org.sandcastle.apps.exception.TodoNotFoundException;
import org.sandcastle.apps.models.Todo;
import org.sandcastle.apps.repository.TodoRepository;
import org.sandcastle.apps.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Mono<Todo> findTodoById(String id) {
        return todoRepository.findById(id).switchIfEmpty(Mono.error(new TodoNotFoundException(String.format("Todo not found. ID: %s", id))));
    }

    @Override
    public Mono<Todo> searchTodoByTitle(String title) {
        return todoRepository.findTodoByTitle(title);
    }

    @Override
    public Flux<Todo> findAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Mono<Todo> saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Mono<Void> deleteTodoById(String id) {
        return todoRepository.deleteById(id);
    }
}