package jdk.lambda;

import java.util.function.Function;

/**
 * 级联表达式和柯里化
 *
 * 柯里化：把多个参数的函数转化为只有一个参数的函数
 * 柯里化的目的：函数标准化
 */
public class CurryDemo {

    public static void main (String[] args) {
        // 级联表达式
        Function<Integer,Function<Integer,Integer>> function =x->y->x+y;
        System.out.println(function.apply(2).apply(5));

        Function<Integer,Function<Integer,Function<Integer,Integer>>> function2 =x->y->z->x+y+z;

        // 可批量操作
        int[] nums = {2,3,4};
        Function f = function2;
        for (int i = 0; i < nums.length; i++) {
            if (f instanceof Function) {
                Object obj = f.apply(nums[i]);
                if (obj instanceof Function) {
                    f = (Function) obj;
                } else {
                    System.out.println(obj);
                }
            }
        }
    }
}
