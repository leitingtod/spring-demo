package com.example.demo.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

public class CHashMapVsSynchronizedMap {
    public final static int THREAD_POOL_SIZE = 5;

    public static void main() {
        Map<String, Integer> hashtable = new Hashtable<>();
        Map<String, Integer> hashmap = Collections.synchronizedMap(new HashMap<>());
        Map<String, Integer> chashmap = new ConcurrentHashMap<>();
        try {
            ExecutorService es = Executors.newFixedThreadPool(3);
            es.execute(() -> {
                try {
                    perf(hashtable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.execute(() -> {
                try {
                    perf(hashmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.execute(() -> {
                try {
                    perf(chashmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            es.shutdown();
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void perf(final Map<String, Integer> map) throws InterruptedException {
        System.out.println("Start to test " + map.getClass());

        long averageTime = 0;

        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();

            ExecutorService es = Executors.newFixedThreadPool(15);

            for (int k = 0; k < THREAD_POOL_SIZE; k++) {
                es.execute(() -> {
                    for (int j = 0; j < 1000000; j++) {
                        Integer random = (int) Math.ceil(Math.random());
                        map.get(String.valueOf(random));
                        map.put(String.valueOf(random), random);
                    }
                });
            }
            es.shutdown();
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long endTime = System.nanoTime();
            long totalTime = (endTime - startTime) / 1000000L;
            averageTime += totalTime;
//            System.out.println("1M entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + map.getClass() + " the average time: " + averageTime / 5 + " ms\n");
    }
}
