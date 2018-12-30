package com.example.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class GreetingCGLibProxy implements MethodInterceptor {
    private static GreetingCGLibProxy instance = new GreetingCGLibProxy();

    private GreetingCGLibProxy() {

    }

    public static GreetingCGLibProxy getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(target, args);
        after();
        return result;
    }

    private void before() {
        System.out.println("Before greeting ...");
    }

    private void after() {
        System.out.println("After greeting ...");
    }
}
