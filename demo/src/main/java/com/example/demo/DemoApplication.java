package com.example.demo;

import com.example.demo.annotation.Apple;
import com.example.demo.annotation.FruitInfoUtil;
import com.example.demo.atomic.AtomicIntegerDemo;
import com.example.demo.bio.BIOClient;
import com.example.demo.bio.BIOServer;
import com.example.demo.future.FutureDemo;
import com.example.demo.map.CHashMapVsSynchronizedMap;
import com.example.demo.map.HashMapDemo;
import com.example.demo.nio.NIOServer;
import com.example.demo.proxy.*;
import com.example.demo.threadlocal.ThreadLocalDemo;
import java.time.LocalTime;
import java.util.concurrent.*;
import com.example.demo.c2.CompactDisc;
import com.example.demo.c2.TrackCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private CompactDisc cd;

    @Autowired
    private TrackCounter trackCounter;

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Nothing to do");
            return;
        }

        switch (args[0]) {
            case "test":
                FutureDemo.main();
                break;
            case "atomic":
                AtomicIntegerDemo.main();
                break;
            case "proxy":
                new GreetingImpl().sayHello("Alice");
                new GreetingProxy(new GreetingImpl()).sayHello("Alice");
                ((Greeting) new GreetingDynamicProxy(new GreetingImpl()).getProxy()).sayHello("Alice");
                ((Greeting) GreetingCGLibProxy.getInstance().getProxy(GreetingImpl.class)).sayHello("Alice");
                break;
            case "last":
                ThreadLocalDemo.main();

                HashMapDemo.main();

                CHashMapVsSynchronizedMap.main();

                // hashcode, equals() Demon
                String a = "ba";
                String b = "bca";

                System.out.println("a.hash: " + a.hashCode());
                System.out.println("b.hash: " + b.hashCode());
                System.out.println("a.equals(b): " + a.equals(b));
                System.out.println("a == b: " + (a == b));

                // ScheduledExecutorService Demo
                ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

                ses.scheduleAtFixedRate(() -> {
                    System.out.println("job1: 5s period task is running at: " + LocalTime.now());
                }, 0, 5, TimeUnit.SECONDS);

                ses.scheduleAtFixedRate(() -> {
                    System.out.println("job1: 8s period task is running at: " + LocalTime.now());
                }, 0, 6, TimeUnit.SECONDS);

                FruitInfoUtil.getFruitInfo(Apple.class);
                break;
            case "bios":
                BIOServer.main();
                break;
            case "nios":
                NIOServer.main();
                break;
            case "bioc":
                BIOClient.main();
                break;
            case "spring":
                SpringApplication.run(DemoApplication.class, args);
                break;
            default:
                System.out.print("Not support " + args[0]);
        }


    }

    public void run(String... args) throws Exception {
        cd.playTrack(0);
        cd.playTrack(0);
        cd.playTrack(1);
        cd.playTrack(2);
        cd.playTrack(2);
        cd.playTrack(2);
        cd.playTrack(2);
        cd.playTrack(3);
        cd.playTrack(3);
        cd.playTrack(3);

        System.out.println(trackCounter.getPlayCount(0));
        System.out.println(trackCounter.getPlayCount(1));
        System.out.println(trackCounter.getPlayCount(2));
        System.out.println(trackCounter.getPlayCount(3));
    }
}
