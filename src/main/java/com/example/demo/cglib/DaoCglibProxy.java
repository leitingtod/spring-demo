package com.example.demo.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class DaoCglibProxy implements MethodInterceptor {

    private String name;
    private Object objT;

    public DaoCglibProxy(String name) {
        this.name = name;
    }
    public DaoCglibProxy(Object obj,String name) {
        this.name = name;
        this.objT = obj;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if(!name.equals("张三")){
            System.out.println("没有权限!");
            return null;
        }
//        return method.invoke(objT, args);    //通过反射进行调用
        //使用Cglib代理调用
        return proxy.invokeSuper(obj, args);
    }
}