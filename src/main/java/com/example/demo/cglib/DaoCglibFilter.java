package com.example.demo.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class DaoCglibFilter implements CallbackFilter {

    public final static int NO_RESTRICTION = 0;
    public final static int RESTRICTION_CREATE = 1;


    @Override
    public int accept(Method method) {
        if(method.getName().startsWith("create")){
            return RESTRICTION_CREATE;
        }
        return NO_RESTRICTION;
    }
}
