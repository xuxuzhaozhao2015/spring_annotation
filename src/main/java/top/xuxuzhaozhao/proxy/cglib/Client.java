package top.xuxuzhaozhao.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Client {
    public static void main(String[] args) {
        final Producer producer = new Producer();
        /**
         * 基于子类的代理(cglib)
         * 设计的类：Enhancer
         * Enhancer create方法
         * 被代理的对象不能是final类
         *  class:指定被代理对象的字节码
         *  callback:MethodInterceptor方法拦截
         */

        Producer cglibProducer = (Producer) Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             * 执行被代理对象的方法都会经过此方法
             * @param proxy 代理对象的引用
             * @param method 当前执行的方法
             * @param args 当前执行方法所需要的参数
             * @param methodProxy 当前执行方法的代理对象
             * @return
             * @throws Throwable
             */
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Object returnValue = null;
                Double money = (Double) args[0];
                if ("saleProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money * 0.8);
                }
                return returnValue;
            }
        });
        cglibProducer.saleProduct(1000);
    }
}
