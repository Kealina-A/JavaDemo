package jdk9flow;


import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * jdk9
 */
public class FlowDemo {

    public static void main (String[] args) {
        // 1. 定义发布者，发布的数据类型是 Integer
        // 直接使用jdk自带的Submission publisher. 它实现了Publisher接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // 2. 定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe (Flow.Subscription subscription) {
                // 保存订阅关系，需要用它来给发布者响应
                this.subscription = subscription;
                // 请求一个数据 ，数字表示要多少条
                this.subscription.request(1);
            }

            @Override
            public void onNext (Integer item) {
                // 接受到一个数据，处理
                System.out.println("接收到数据：" + item);
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

        // 3. 发布者和订阅者 建立订阅关系
        publisher.subscribe(subscriber);

        // 4. 生产数据，并发布
        // 这里忽略数据生产过程
        int data = 111;
        // submit 是个block操作，如果缓冲池满了的话就会阻塞
        publisher.submit(data);
        publisher.submit(222);
        publisher.submit(33333);

        // 5. 结束后，关闭发布者
        publisher.close();


        try {
            Thread.currentThread().join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
