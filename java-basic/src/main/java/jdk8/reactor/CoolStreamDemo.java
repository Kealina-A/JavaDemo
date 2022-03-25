package jdk8.reactor;

import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicLong;

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

        Flux.range(5, 3).subscribe(); // 没有可视的输出，但是是生效的
        Flux.range(5, 3).subscribe(System.out :: println);

        Flux<Integer> ints = Flux.range(1, 6)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i), //正常结果和异常结果处理，异常后的不执行
                error -> System.err.println("Error: " + error));

        Flux.range(1,4)
                .subscribe(i -> System.out.println(i),
                        error -> System.err.println("Error: " + error),
                        ()-> System.out.println("done"));

        Flux.range(1,20)
                .subscribe(i -> System.out.println(i),
                        error -> System.err.println("Error: " + error),
                        ()-> System.out.println("done"),
                        sub -> sub.request(10));


        System.out.println("----------------------------");
        // 不用lambda 实现的subscriber ->baseSubscriber
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> f1 = Flux.range(1, 4);
        f1.subscribe(ss);

        System.out.println("----------------------------");
        // limitRate limitRequest
        Flux.range(1,5).limitRequest(1)
                .subscribe(i -> System.out.println(i));

        System.out.println("----------------------------");
        //generate
        Flux.generate(()->0,(state,sink)-> {
            sink.next("3 X "+state+" = " +3*state);
            if (state==10) sink.complete();
            return state+1;
        }).subscribe(System.out::println);

        System.out.println("----------------------------");
        //generate  可变状态变量
        Flux.generate(AtomicLong ::new,(state, sink)-> {

            long i= state.getAndIncrement();
            sink.next("3 X "+i+" = " +3*i);
            if (i==10) sink.complete();
            return state;
        }).subscribe(System.out::println);

        System.out.println("----------------------------");


    }
}


