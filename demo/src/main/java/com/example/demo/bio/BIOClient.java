package com.example.demo.bio;

import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BIOClient {
    public static void main() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(8);

            executorService.execute(() -> {
                try {
                    while (true) {
                        try {
                            Socket socket = new Socket("127.0.0.1", 8080);
                            String s =  new Date() + ": hello world.";
                            System.out.println(s);
                            socket.getOutputStream().write(s.getBytes());
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
