package jdk8.lambda;

import java.util.function.*;

/**
 * 7种函数
 */
public class FunctionDemo {

    public static void main (String[] args) {

        // 断言函数
        Predicate<Integer> p1 = i->i>0;
        System.out.println(p1.test(-9));

        IntPredicate p2 = i->i<0;
        System.out.println(p2.test(-9));

        // 消费者函数
        Consumer<Integer> c1 = i-> System.out.println(i*100);
        c1.accept(123);

        IntConsumer c2  =i-> System.out.println(i*200);
        c2.accept(200);

        // 传入T返回R
        Function<Integer,String> f1 =i-> "num: " + i;
        System.out.println(f1.apply(100));

        // 提供一个数据
        Supplier<Integer> s1 =()->999*377;
        System.out.println(s1.get());

        // 一元函数，输入类型和输出类型相同
        UnaryOperator<String> i = name->"Hello,"+name;
        System.out.println(i.apply("Kealina"));

        // 2个输入函数
        BiFunction<Integer,Integer,Integer> b = (x,y)->x+y*10;
        System.out.println(b.apply(1,2));

        //二元函数。输入类型和输出类型相同
        BinaryOperator<String> bb=(x,y)->x+":"+y;
        System.out.println(bb.apply("注意","安全第一"));

    }
}
