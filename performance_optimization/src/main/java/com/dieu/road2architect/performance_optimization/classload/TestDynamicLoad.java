package com.dieu.road2architect.performance_optimization.classload;

/**
 * Program Name: road2architect
 * <p>
 * Description: 验证动态加载
 * <p>
 * Created by wangcan on 2022/4/4
 *
 * @author wangcan
 * @version 1.0
 */
public class TestDynamicLoad {
    static {
        System.out.println("load TestDynamicLoad");
    }
    public static void main(String[] args) {
        new A();
        B b = null;
    }
}

class A {
    static {
        System.out.println("load A");
    }
    public A() {
        System.out.println("init A");
    }
}
class B {
    static {
        System.out.println("load B");
    }
    public B() {
        System.out.println("init B");
    }
}
