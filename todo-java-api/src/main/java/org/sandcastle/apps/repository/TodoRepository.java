package org.sandcastle.apps.repository;

import org.sandcastle.apps.models.Todo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TodoRepository {
    private final Map<String, Todo> todos = new ConcurrentHashMap<>();
    private final UnicastProcessor<Todo> processor = UnicastProcessor.create();
    private final FluxSink<Todo> fluxSink = processor.sink(FluxSink.OverflowStrategy.LATEST);
    private final Flux<Todo> hotFlux = processor.publish().autoConnect();

    public Mono<Todo> save(Todo todo) {
        if(todo.getId() == null || todo.getId().isEmpty()){
            todo.setId(UUID.randomUUID().toString());
        }
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());

        todos.put(todo.getId(), todo);
        fluxSink.next(todo);
        return Mono.just(todo);
    }

    public Mono<Todo> findById(String id){
        return Flux.fromIterable(todos.values())
                .filter(obj -> obj.getId().equals(id))
                .next();
    }

    public Mono<Todo> findTodoByTitle(String title){
        return Flux.fromIterable(todos.values())
                .filter(obj -> obj.getTitle().equals(title))
                .next();
    }

    public Mono<Void> deleteById(String id){
        return Flux.fromIterable(todos.values())
                .filter(obj -> obj.getId().equals(id))
                .then();
    }

    public Flux<Todo> findAll() {
        //without fromIterable I cannot push books that where saved before someone subscribed
        return Flux.fromIterable(todos.values())
                /*.concatWith(hotFlux)
                //Unfortunately this solution produces duplicates so we need to filter them
                .distinct()*/
                ;
    }
}