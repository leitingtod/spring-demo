package com.example.demo.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    private static AtomicInteger ai = new AtomicInteger(0);

    private static volatile int count = 0;

    public static void main() {
        ExecutorService es = Executors.newScheduledThreadPool(10);

        for (int i = 0; i < 10000; i++) {
//            es.execute(() -> {
//                ai.incrementAndGet();
//            });
            es.execute(() -> {
//                synchronized (AtomicIntegerDemo.class) {
                    count++;
//                }
            });
        }
        System.out.println(ai);
        System.out.println(count);
        es.shutdown();
    }
}
