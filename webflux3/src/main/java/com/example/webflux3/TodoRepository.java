package com.example.webflux3;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {

}
