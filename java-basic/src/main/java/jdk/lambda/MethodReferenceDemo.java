package jdk.lambda;

import java.util.function.*;

class Dog {

    private String name="大旺";
    private int food=10;

    public Dog () {
    }

    public Dog (String name) {
        this.name = name;
    }



    public static void bark(Dog dog) {
        System.out.println(dog+"叫了");
    }


    /**
     * 吃狗粮
     * jdk 默认会把当前实例作为参数传入到非静态方法里 参数名为this,位置为第一个
     * @param num 吃了多少
     * @return 还剩下多少
     */
    public int eat(Dog this,int num) {
        System.out.println("吃了"+num+"斤狗粮");
        this.food-=num;
        return this.food;
    }

    @Override
    public String toString () {
        return name;
    }
}

/**
 * 方法引用的方式
 * 操作重点: 分析输入输出选择对应函数来匹配
 */
public class MethodReferenceDemo {

    public static void main (String[] args) {

        // 方法引用
        Consumer<String> consumer = s-> System.out.println(s);
        consumer.accept("hello");

        // 如果传入参数与函数执行体的参数相同，可使用下面写法
        Consumer<String> consumer1 = System.out::println;
        consumer1.accept("hello1");

        // 静态方法引用
        Dog dog = new Dog();
        Consumer<Dog> dogConsumer = Dog::bark;
        dogConsumer.accept(dog);

        // 非静态方法引用,使用对象实例
//        Function<Integer,Integer> dogConsumer1 = dog::eat;
//        UnaryOperator<Integer> dogConsumer1 = dog::eat;
        IntUnaryOperator dogConsumer1 = dog::eat;
        System.out.println("还剩下"+dogConsumer1.applyAsInt(3)+"斤狗粮");

        // 使用类名方法来引用
        BiFunction<Dog,Integer,Integer> b = Dog::eat;
        System.out.println("还剩下"+b.apply(dog,2)+"斤狗粮");


        // 构造函数引用
        Supplier<Dog> supplier = Dog::new;
        System.out.println("创建了一个新对象:"+supplier.get());

        // 带参的构造函数引用
        Function<String,Dog> f = Dog::new;
        System.out.println("创建了一个新对象:"+f.apply("小旺"));
    }
}
