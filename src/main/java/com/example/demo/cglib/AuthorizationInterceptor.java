package com.example.demo.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AuthorizationInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("authorizationInterceptor begin");
        proxy.invokeSuper(obj, args);
        System.out.println("authorizationInterceptor end");
        return null;
    }
}
