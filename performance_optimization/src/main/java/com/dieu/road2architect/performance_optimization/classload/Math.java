package com.dieu.road2architect.performance_optimization.classload;

/**
 * Program Name: road2architect
 * <p>
 * Description: 类加载机制
 * <p>
 * Created by wangcan on 2022/4/3
 *
 * @author wangcan
 * @version 1.0
 */
public class Math {

    public static final int INIT_DATA = 666;
    public static User user = new User();

    public int compute() { //一个方法对应一块栈帧内存区域
        int a = 2, b = 4;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        System.out.println(math.compute());
    }
}
