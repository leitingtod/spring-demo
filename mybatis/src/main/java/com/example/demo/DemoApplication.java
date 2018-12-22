package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EnableTransactionManagement
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final UserService userService;

    public DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public void test() {
        try {
            Thread.sleep(3000);
            LocalTime now = LocalTime.now();
            int thread_size = 100;
            ExecutorService es = Executors.newFixedThreadPool(thread_size);
            for (int i = 0; i < thread_size; i++) {
                // 乐观锁|非同步（模拟跨机情况）100 并发请求
                // 5 - 602 ms/64%
                // 6 - 700 ms/71%
                // 7 - 793 ms/86%
                // 8 - 892 ms/88%
                // 9 - 986 ms/92%
                // 10 - 1084 ms/93%
                // 20 - 2061 ms/97%
                // 30 - 3043 ms/98%
                // 40 - 4020 ms/99%
                // 50 - 5088 ms/99%
                // 60 - 6062 ms/100%
                // Thread.sleep(50);
                es.execute(() -> {
                    // 同步（模拟单机情况，但跨机无法做到同步）100 并发请求
                    // 0 - 872ms/100%
                    synchronized (userService) {
                        try {
                            userService.updateAccount(1L, 1, 0);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                });
            }
            es.shutdown();
            try {
                es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
                System.out.println("耗时：" + Duration.between(now, LocalTime.now()).toMillis() + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
//        test();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
