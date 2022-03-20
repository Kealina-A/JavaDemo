package stream;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * 并行流
 */
public class StreamDemo5 {


    public static void main (String[] args) {

        // 并行执行
        //IntStream.rangeClosed(0,100).parallel().forEach(System.out :: println);

        // 并行流使用的线程池是：ForkJoinPool
        // 默认的线程数中当前机器cpu个数*2
//        // 使用这个属性可个改默认的线程数
//        System.setProperty("java.com.example.webflux2.util.concurrent.ForkJoinPool.common.parallelism","20");
//        IntStream.rangeClosed(0,50).parallel().peek(StreamDemo5::debug).count();


        // 使用自己的线程池，不使用默认的，防止任务被阻塞
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(()->IntStream.range(0,50).parallel().peek(StreamDemo5::debug).count());
        pool.shutdown();

        synchronized (pool) {
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void debug(int i) {
        System.out.println(Thread.currentThread().getName()+":"+i);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
