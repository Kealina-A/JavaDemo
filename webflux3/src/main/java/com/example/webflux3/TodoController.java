package com.example.webflux3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping ("/api")
class TodoController {
    private final TodoRepository repository;

    @Autowired
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping ("/todo")
    Flux<Todo> getAll() {
        return repository.findAll();
    }

    @PostMapping ("/todo")
    Mono<Todo> addTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @PutMapping("/todo")
    Mono<Todo> updateTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @DeleteMapping("/todo/{id}")
    Mono<Void> deleteById(@PathVariable("id") Long id) {
        return repository.deleteById(id);
    }
}
