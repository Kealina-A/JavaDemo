package jdk.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo3 {

    public static void main (String[] args) {
        String str ="My name is kealina";

        System.out.println("-----------------filter/map------------------");
        // 将每个单词的长度调出来
        Stream.of(str.split(" ")).filter(i->i.length()>2).map(i-> i+": "+i.length()).forEach(System.out::println);

        System.out.println("-----------------maptoint------------------");
        Stream.of(str.split(" ")).mapToInt(i->i.length()).forEach(System.out :: println);


        System.out.println("-----------------flatmap------------------");
        // flatMap A元素下有B属性（是个集合），最后得到所有A元素下的所有B属性集合
        // intStream/longStream 不是 Stream 的子类，所以要进行装箱
        Stream.of(str.split(" ")).flatMap(s->s.chars().boxed()).forEach(i-> System.out.print((char)i.intValue()+"-"));
        System.out.println();


        //peek 用于debug,是一个中间操作， forEach 是一个终止操作
        System.out.println("-----------------peek------------------");
        Stream.of(str.split(" ")).peek(System.out :: println).forEach(System.out :: println);

        // limit 使用，主要用于无限流
        System.out.println("-----------------limit------------------");
        new Random().ints().filter(i->i>9 && i<100).limit(2).forEach(System.out :: println);

        System.out.println("-----------------distinct------------------");
        List<Integer> list = Arrays.asList(1, 1, 1, 1, 1, 2,-1,-2,-8);
        List<Integer> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);

        System.out.println("-----------------sorted------------------");
        List<Integer> collect1 = list.stream().sorted().collect(Collectors.toList());
        System.out.println(collect1);

        System.out.println("-----------------skip------------------");
        List<Integer> collect2 = list.stream().skip(5).collect(Collectors.toList());
        System.out.println(collect2);
    }
}
