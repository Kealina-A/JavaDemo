package com.example.webflux2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Webflux2Application {

    public static void main (String[] args) {
        SpringApplication.run(Webflux2Application.class, args);
    }

}
