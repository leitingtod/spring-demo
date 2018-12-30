package com.example.demo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureDemo {
    public static void main() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            int sum = 0;
            System.out.println("sub thread is computing ...");
            try {
                Thread.sleep(3000);
                for (int i = 0; i < 3; i++) {
                    sum += i;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sum;
        });
        es.submit(futureTask);
        es.shutdown();

        System.out.println("main thread is running ...");

        try {
            System.out.println(futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
