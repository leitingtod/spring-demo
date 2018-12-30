package com.example.demo.proxy;

public class GreetingProxy implements Greeting {
    private GreetingImpl greetingImpl;

    public GreetingProxy(GreetingImpl greetingImpl) {
        this.greetingImpl = greetingImpl;
    }

    @Override
    public void sayHello(String name) {
        before();
        System.out.println("Hello, " + name);
        after();
    }

    private void before() {
        System.out.println("Before greeting ...");
    }

    private void after() {
        System.out.println("After greeting ...");
    }
}
