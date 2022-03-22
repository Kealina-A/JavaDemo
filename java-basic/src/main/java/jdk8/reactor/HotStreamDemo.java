package jdk8.reactor;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HotStreamDemo {

    public static void main (String[] args) {


        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while(true) {
                fluxSink.next(System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })
        .publish();

        publish.subscribe(s-> System.out.println(1+"->"+Thread.currentThread().getName()+"->"+s));
        publish.subscribe(s-> System.out.println(1+"->"+Thread.currentThread().getName()+"->"+s));
        publish.connect();



        // Throttling
        ConnectableFlux<Object> publish1 = Flux.create(fluxSink->{
            while (true){
                fluxSink.next(System.currentTimeMillis());
            }
        }).sample(Duration.ofSeconds(2))
                .publish();
        publish1.subscribe(System.out::println);
        publish1.connect();
        // 复杂策略 windowing and buffering
        //...



    }
}
