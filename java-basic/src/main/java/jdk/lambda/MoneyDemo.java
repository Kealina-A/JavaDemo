package jdk.lambda;

import java.text.DecimalFormat;
import java.util.function.Function;

class MyMoney {

    private int money;

    public MyMoney (int money) {
        this.money = money;
    }

    // 函数式编程， 不关心类名方法名，只关心输入是什么输出是什么
    public void printMoney(Function<Integer,String> i) {
        System.out.println("我的存款:" +i.apply(money));
    }
}
public class MoneyDemo {

    public static void main (String[] args) {
        MyMoney me = new MyMoney(999999999);
        // 输入为i, 输出为new DecimalFormat("#,###").format(i)
        Function<Integer,String> moneyFormat = i->new DecimalFormat("#,###").format(i);
        me.printMoney(moneyFormat.andThen(s->"人民币"+s));

    }

}
