package com.example.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;


public class ReactorDemo {

    public static void main (String[] args) {

        // mono : jdk8 stream + jdk9 reactive stream
        // mono : 0-1 个元素
        // flux : 1-n 个元素

        String[] strings = {"1","2","3"};

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;

            @Override
            public void onSubscribe (Subscription subscription) {
                // 保存订阅关系，需要用它来给发布者响应
                this.subscription = subscription;
                // 请求一个数据 ，数字表示要多少条
                this.subscription.request(1);
            }

            @Override
            public void onNext (Integer item) {
                // 接受到一个数据，处理
                System.out.println("接收到数据：" + item);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 处理完调用request再请求一个数据
                this.subscription.request(1);
                // 或者已经达到了目标，调用cancel 告诉发布者不再接受数据了
//                this.subscription.cancel();
            }

            @Override
            public void onError (Throwable throwable) {
                // 妯现了异常（如处理数据的时候产生了异常）
                throwable.printStackTrace();
                //我们可以告诉发布者，后面不接受数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete () {
                // 全部数据处理完了（发布者关闭了，publish.close()时会触发）
                System.out.println("处理完了");
            }
        };

        Flux.fromArray(strings).map(i->Integer.parseInt(i))
                // 最终状态就是订阅
                .subscribe(subscriber);

    }
}
