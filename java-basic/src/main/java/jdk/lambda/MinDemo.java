package jdk.lambda;

import java.util.stream.IntStream;

public class MinDemo {

    public static void main (String[] args) {

        int[] data = {9,4,5,6,6,67,7,4,4,2,4};

        // jdk1.8
        //        int min = IntStream.of(data).min().getAsInt();
        // 并行处理
        int min = IntStream.of(data).parallel().min().getAsInt();
        System.out.println(min);
    }
}
