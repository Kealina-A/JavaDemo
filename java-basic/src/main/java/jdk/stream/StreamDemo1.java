package jdk.stream;

import java.util.stream.IntStream;

public class StreamDemo1 {

    public static void main (String[] args) {

        int[] nums = {1,2,3};
        // 外部迭代
        int s = 0;
        for (int i = 0;i<nums.length;i++) {
            s+=nums[i];
        }
        System.out.println("结果为："+s);

        // 使用stream内部迭代 (与外部迭代的区别就是不用关心内部实现)
//        int sum = IntStream.of(nums).sum();
        // map 就是中间操作 （返回stream可继续操作）
        // sum 就是终止操作
        int sum = IntStream.of(nums).map(i->i*2).sum();
        System.out.println("结果为："+sum);


        // 惰性求值 就是终止操作没有调用情况下，中间操作不会执行。
        int sum1 = IntStream.of(nums).map(StreamDemo1::doubleNum).sum();
//        System.out.println("结果为："+sum1);

        System.out.println("惰性求值 就是终止没有调用情况下，中间操作不会执行。");
        IntStream.of(nums).map(StreamDemo1::doubleNum);

    }

    public static int doubleNum (int i) {
        System.out.println("执行了乘2");
        return i*2;
    }
}
