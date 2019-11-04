package top.xuxuzhaozhao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        final IProducer producer = new Producer();

        /**
         * 动态代理：
         *  特点：字节码随用随创建，随用随加载
         *  作用：不修改源码的前提下，对方法的增强
         *  分类：
         *      1、基于接口的动态代理；
         *      2、基于子类的动态代理；
         *   基于接口的动态代理：
         *   创建代理对象的要求：【最少实现一个接口】;
         *   newProxyInstance:
         *      classloader：用于加载代理对象字节码，和被代理对象使用相同的类加载器，固定写法
         *          已实例化的对象.getClass().getClassLoader()
         *      class[]：字节码数组，它是用于让代理对象和被代理对象有相同的方法，固定写法
         *          已实例化的对象.getClass().getInterface()
         *      InvocationHandler：用于提供增强的代码。写如何代理，通常为匿名类
         *          此接口实现类谁用谁写
         */

        IProducer producerProxy = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 执行被代理对象的任何接口方法都会执行该方法
                     * @param proxy 代理对象的引用
                     * @param method 当前执行的方法
                     * @param args 当前执行方法所需要的参数
                     * @return 和被代理对象有相同的返回值
                     * @throws Throwable
                     */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object returnValue = null;
                        //提供增强的代码
                        // 1、获取方法执行的参数
                        Double money = (Double) args[0];
                        // 2、判断当前方法是不是销售
                        if ("saleProduct".equals(method.getName())) {
                            returnValue = method.invoke(producer, money * 0.8);
                        }
                        return returnValue;
                    }
                });

        producerProxy.saleProduct(1000);

        /**
         * 基于子类的动态代理（需要第三方jar包：cglib）
         */
    }
}
