package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableTransactionManagement
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public DemoApplication(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public void test() {
        try {
            Thread.sleep(3000);
            ExecutorService es = Executors.newFixedThreadPool(5);
            for (int i = 0; i < 3; i++) {
                es.execute(() -> {
//                    synchronized (userRepository) {
                    try {
                        userRepository.updateAccount(BigDecimal.valueOf(1000), 1, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    }
                });
            }
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        test();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
