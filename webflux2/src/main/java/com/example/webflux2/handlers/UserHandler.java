package com.example.webflux2.handlers;

import com.example.webflux.util.CheckUtil;
import com.example.webflux2.domain.User;
import com.example.webflux2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {

    private final UserRepository repository;

    public UserHandler (UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.repository.findAll(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {

        Mono<User> user = request.bodyToMono(User.class);
        return user.flatMap(u->{
            CheckUtil.checkName(u.getName());

            return ok().contentType(MediaType.APPLICATION_JSON)
                    .body(this.repository.save(u),User.class);
        });
    }

    public Mono<ServerResponse> deleteUserById (ServerRequest request) {

        String id = request.pathVariable("id");

        return this.repository.findById(id)
                .flatMap(u-> this.repository.delete(u)
                        .then(ok().build())
                    .switchIfEmpty(notFound().build()));
    }

    public Mono<ServerResponse> updateUserById (ServerRequest request) {

        String id = request.pathVariable("id");
        Mono<User> user = request.bodyToMono(User.class);

        return this.repository.findById(id)
                .flatMap(u->
                        user.flatMap(uu->{
                            u.setAge(uu.getAge());
                            u.setName(uu.getName());
                            return this.repository.save(u);
                        }).then(ok().build())
                            .switchIfEmpty(notFound().build())
                );
    }
}
