package jdk.stream;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo4 {

    public static void main (String[] args) {

        String str ="My name is kealina";

        // 使用并行流
        str.chars().parallel().forEach(s-> System.out.print((char)s));
        System.out.println();
        // 使用forEachOrdered保证顺序
        str.chars().parallel().forEachOrdered(s-> System.out.print((char)s));
        System.out.println();

        // 使用collect 收集到list
        List<String> collect = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(collect);

        // 使用reduce 拼接字符串
        Optional<String> reduce = Stream.of(str.split(" ")).reduce((s1, s2) -> s1 + "|" + s2);
        String s = reduce.orElse("");
        System.out.println(s);
        // 带初始化值的reduce
        String reduce1 = Stream.of(str.split(" ")).reduce("", (s1, s2) -> s1 + "|" + s2);
        System.out.println(reduce1);

        // max 的使用
        Optional<String> max = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        System.out.println(max.get());

        // findFirst
        OptionalInt first1 = new Random().ints().findFirst();
        System.out.println(first1.getAsInt());


    }
}
