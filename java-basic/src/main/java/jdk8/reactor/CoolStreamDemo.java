package jdk8.reactor;

import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import static org.hamcrest.MatcherAssert.assertThat;

public class CoolStreamDemo {

    public static void main (String[] args) {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elements::add);

        Flux.just(1,2,3,4)
                .log()
                .subscribe(new Subscriber<>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe (Subscription subscription) {
                        //subscription.request(Long.MAX_VALUE);
                        this.s = subscription;
                        s.request(2); // 消费者决定每次只接收两个数据
                    }

                    @Override
                    public void onNext (Integer integer) {
                        elements.add(integer);
                        onNextAmount++;
                        if (onNextAmount % 2 == 0) {
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError (Throwable throwable) {

                    }

                    @Override
                    public void onComplete () {

                    }
                });

        // map 操作
        Flux.just(1,2,3,4)
                .log()
                .map(i->i*2)
                .subscribe(elements::add);

        List<String> eles = new ArrayList<>();

        // 合并两个流
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(eles::add);


        System.out.println(elements);
        System.out.println(eles);
    }
}
