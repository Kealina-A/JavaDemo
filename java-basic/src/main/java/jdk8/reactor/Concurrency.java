package jdk8.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class Concurrency {

    public static void main (String[] args) {
        //Concurrency
        List<Integer> elements = new ArrayList<>();

        Flux.just(1,2,3,4)
                .log()
                .map(i->i*2)
                .subscribeOn(Schedulers.parallel())
                .subscribe(elements::add);

        System.out.println(elements);


    }
}
