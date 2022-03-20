package com.example.webflux2.routers;


import com.example.webflux2.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler) {
        return RouterFunctions.nest(
                path("/user"),
                route(GET("/"),handler::getAllUser)
                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)),handler::createUser)
                .andRoute(DELETE("/{id}"),handler:: deleteUserById)
                .andRoute(PUT("/{id}").and(accept(MediaType.APPLICATION_JSON)),handler:: updateUserById)
        );
    }



}
