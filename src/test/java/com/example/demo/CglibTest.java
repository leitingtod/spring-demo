package com.example.demo;

import com.example.demo.cglib.*;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.Test;


public class CglibTest {
    @Test
    public void testCglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AuthorizationService.class);
        enhancer.setCallback(new AuthorizationInterceptor());

        AuthorizationService authorizationService = (AuthorizationService) enhancer.create();
        authorizationService.f();
        authorizationService.g();
    }

    @Test
    public void testCallbackFilter() {
        Enhancer en = new Enhancer();
        en.setSuperclass(Dao.class);
        // 指定两个callback，通过callBackFilter返回的下标值控制调用使用那一个callback处理
        en.setCallbacks(new Callback[]{NoOp.INSTANCE, new DaoCglibProxy("张三1")});
        //方法过滤器，根据过滤器返回不同的值回调对应下标的Callback方法
        en.setCallbackFilter(new DaoCglibFilter());

        Dao dao = (Dao) en.create();
        dao.create();
        dao.query();
        dao.update();
        dao.delete();
    }
}
