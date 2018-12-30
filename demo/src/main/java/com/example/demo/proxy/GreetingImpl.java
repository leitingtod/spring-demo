package com.example.demo.proxy;

public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(String name) {
        // before();
        System.out.println("Hello, " + name);
        // after();
    }

    // Comment when using proxy
    /*
    private void before() {
        System.out.println("Before greeting ...");
    }

    private void after() {
        System.out.println("After greeting ...");
    }
    */
}
