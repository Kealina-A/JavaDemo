package com.example.webflux3;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import java.util.stream.Stream;

@SpringBootApplication
public class Webflux3Application {

    public static void main (String[] args) {
        SpringApplication.run(Webflux3Application.class, args);
    }

  /*  @Bean
    ApplicationRunner init(TodoRepository repository, DatabaseClient client) {

        return args -> {
            client.sql("create table IF NOT EXISTS TODO" +
                    "(id SERIAL PRIMARY KEY, " +
                    "text varchar (255) not null, " +
                    "completed boolean default false);").fetch().first().subscribe();
            client.sql("DELETE FROM TODO;").fetch().first().subscribe();

            Stream<Todo> stream = Stream.of(new Todo(null, "Hi this is my first todo!", false),
                    new Todo(null, "This one I have acomplished!", true),
                    new Todo(null, "And this is secret", false));

            // initialize the database
            repository.saveAll(Flux.fromStream(stream))
                    .then()
                    .subscribe(); // execute

        };
    }*/
}
