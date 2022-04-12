package com.dieu.road2architect.performance_optimization.classload;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * Program Name: road2architect
 * <p>
 * Description: 自定义类加载器：（1）加载自定义路径的class文件；（2）打破双亲委派机制。
 * <p>
 * Created by wangcan on 2022/4/10
 *
 * @author wangcan
 * @version 1.0
 */
public class MyClassLoaderTest {
    private static final String NAME = "com.dieu.road2architect.performance_optimization.classload.User1";

    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        /**
         * 从路径中读取文件
         * @param name 包名
         * @return 字节数组
         * @throws Exception 异常
         */
        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转化为Class对象，这个字节数组是class文件读取后最终的字节数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                if (c == null) {
                    long t0 = System.nanoTime();
                    //打破双亲委派机制
//                    try {
//                        if (parent != null) {
//                            c = parent.loadClass(name, false);
//                        } else {
//                            c = findBootstrapClassOrNull(name);
//                        }
//                    } catch (ClassNotFoundException e) {
//                        // ClassNotFoundException thrown if class not found
//                        // from the non-null parent class loader
//                    }

                    if (c == null) {
                        // If still not found, then invoke findClass in order
                        // to find the class.
                        long t1 = System.nanoTime();
                        if (name.startsWith("com.dieu")) {
                            c = findClass(name);
                        } else {
//                            ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
//                            ClassLoader extClassLoader = appClassLoader.getParent();
//                            c = extClassLoader.loadClass(name);
                            c = this.getParent().loadClass(name);
                        }

                        // this is the defining class loader; record the stats
                        sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //初始化自定义加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
        MyClassLoader myClassLoader = new MyClassLoader("D:/test");
        /*
         * 沙箱安全机制：禁止加载java.lang包下的类，否则触发java.lang.SecurityException: Prohibited package name: java.lang
         */
        //D盘创建 test/com/dieu/road2architect/performance_optimization/classload 目录，将User类的复制类User1.class扔到该目录下。
        Class<?> clazz = myClassLoader.loadClass(NAME);
        Object o = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(o, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
