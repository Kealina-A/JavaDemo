package com.example.webflux3;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.NOT_FOUND)
class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(String todoId) {
        super("could not find todo '" + todoId + "'.");
    }
}
