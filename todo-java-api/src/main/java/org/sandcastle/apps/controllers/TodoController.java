package org.sandcastle.apps.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.sandcastle.apps.models.Todo;
import org.sandcastle.apps.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos/{id}")
    @Operation(summary = "Find Todo by id.")
    Mono<ResponseEntity<Todo>> findTodoById(@PathVariable String id) {
        return todoService.findTodoById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all Todos")
    Flux<Todo> findAllTodos() {
        return todoService.findAllTodos();
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Todo.")
    Mono<ResponseEntity<Todo>> saveTodo(@RequestBody Todo todo) {
        return todoService.saveTodo(todo)
                .map(savedTodo -> ResponseEntity.status(HttpStatus.CREATED).body(savedTodo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Todo by id.")
    Mono<Void> deleteTodoById(@PathVariable String id) {
        return todoService.deleteTodoById(id);
    }

    @GetMapping("/todos/search")
    @Operation(summary = "Search Todo by title.")
    Mono<ResponseEntity<Todo>> searchTodoByTitle(@RequestParam String title) {
        return todoService.searchTodoByTitle(title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}