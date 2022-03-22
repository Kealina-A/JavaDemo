package com.example.webflux.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/1")
    private String get1() {

        log.info("get1 start...");
        String res = createStr();
        log.info("get1 end...");

        return res;
    }

    @SneakyThrows
    public String createStr() {
        TimeUnit.SECONDS.sleep(5);
        return "some string";
    }

    @GetMapping("/2")
    private Mono<String> get2() {
        log.info("get2 start...");
        Mono<String> res = Mono.fromSupplier(this :: createStr);
        log.info("get2 end...");
        return res;
    }

    @GetMapping(value = "/3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux() {
        Flux<String> res = Flux.fromStream(IntStream.range(1,5).mapToObj(i->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data :"+i;
        }));
        return res;
    }
}
