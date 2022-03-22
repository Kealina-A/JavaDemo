package jdk8.stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * jdk8.stream 流的运行机制
 * 1.所有操作是链式调用，一个元素只迭代一次
 * 2.每个操作返回一个新的流，流里的有一个属性sourceStage执行指向同一个地方，(Head)
 * head->nextStage->nextStage->...->null
 * 3. 有状态会把无状态的流截断单独处理
 * 4. 并行环境下，有状态的中间操作不一定能并行操作
 * 5. parallel/sequential 这两个操作也是中间操作,也是返回stream,但不创建流，只是修改header里的parallel并行标志
 */
public class RunStream {

    public static void main (String[] args) {
        Random random = new Random();
        Stream<Integer> stream = Stream.generate(() -> random.nextInt())
                .limit(500)
                .peek(i -> print("peek:" + i)) // 无状态
                .filter(i -> {               // 无状态
                    print("filter:" + i);
                    return i > 1000000;
                })
                .sorted((i1,i2)->{
                    print("sorted:"+i1+","+i2);
                    return i1.compareTo(i2);
                })                  // 有状态
                .peek(i -> print("peek:" + i))
                .parallel();
        stream.count();
    }

    public static void print(String msg) {
        System.out.println(Thread.currentThread().getName()+" ："+msg);
        //System.out.println(msg);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
