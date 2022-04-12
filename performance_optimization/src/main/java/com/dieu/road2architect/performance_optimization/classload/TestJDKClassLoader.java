package com.dieu.road2architect.performance_optimization.classload;

import com.sun.crypto.provider.DESKeyFactory;
import sun.misc.Launcher;

import java.net.URL;

/**
 * Program Name: road2architect
 * <p>
 * Description: 测试JDK类加载器
 * <p>
 * Created by wangcan on 2022/4/10
 *
 * @author wangcan
 * @version 1.0
 */
public class TestJDKClassLoader {
    public static void main(String[] args) {
        //获取引导类加载器 因引导类加载器为C++实现，故获取为null
        System.out.println(String.class.getClassLoader());
        //获取拓展类加载器
        System.out.println(DESKeyFactory.class.getClassLoader().getClass().getName());
        //获取应用程序类加载器
        System.out.println(TestJDKClassLoader.class.getClassLoader().getClass().getName());

        System.out.println("--------分割线--------");

        //获取当前类所使用的加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootStrapLoader = extClassLoader.getParent();
        System.out.println("appClassLoader::::" + appClassLoader);
        System.out.println("extClassLoader::::" + extClassLoader);
        System.out.println("bootStrapLoader::::" + bootStrapLoader);

        System.out.println("--------分割线--------");

        System.out.println("bootStrapLoader加载以下文件：");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        System.out.println("--------分割线--------");

        System.out.println("extClassLoader加载以下文件：");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("--------分割线--------");

        System.out.println("appClassLoader加载以下文件：");
        System.out.println(System.getProperty("java.class.path"));
    }
}
