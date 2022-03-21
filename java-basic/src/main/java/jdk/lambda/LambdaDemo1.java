package jdk.lambda;

@FunctionalInterface //加了些注解，接口里要实现的方法只有一个，并不是只能有一个方法。单一职责
interface Interface1 {
    int doubleNum(int i);
    default int add(int x,int y) {return x+y;} // 默认实现
}

@FunctionalInterface
interface Interface2{
    int doubleNum(int i);
    default int add(int x,int y) {return x+y;} // 默认实现
}

@FunctionalInterface
interface Interface3 extends Interface2,Interface1{

    @Override
    default int add (int x, int y) {
        return Interface2.super.add(x,y);
    }
}

public class LambdaDemo1 {
    public static void main (String[] args) {
        // 返回指定接口的对象实例
        Interface1 i1 =  (i)->i*2;
        System.out.println(i1.doubleNum(2));
        System.out.println(i1.add(1,2));
        // 最常用写法
        Interface1 i2 =  i->i*2;
        Interface1 i3 =  (int i)->i*2;
        Interface1 i4 =  i->{
            return i*2;
        };

    }
}
