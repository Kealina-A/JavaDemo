package lambda;

interface IMath {

    int add(int x,int y);
}
interface IMath2 {

    int sub(int x,int y);
}

/**
 * 类型推断
 */
public class TypeDemo {

    public static void main (String[] args) {

        // 变量类型定义
        IMath iMath = (x,y)->x+y*2;
        // 数组里
        IMath[] iMath2 ={(x,y)->x+y*2} ;
        // 类型强转
        Object iMath3 = (IMath)(x,y)->x+y*2;
        // 通过返回类型
        IMath lambda = createLambda();

        TypeDemo typeDemo = new TypeDemo();
        // 当有二义性时，使用强转对应的接口来解决
        typeDemo.test((IMath2) (x,y)->x+y*2);
    }

    public void test(IMath iMath) {

    }
    public void test(IMath2 iMath) {

    }
    public static IMath createLambda() {
        return (x,y)->x+y*2;
    }
}
