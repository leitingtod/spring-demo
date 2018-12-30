package com.example.demo.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    public static void main() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);

            new Thread(() -> {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        new Thread(() -> {
                            try {
                                int len;
                                byte[] data = new byte[1024];
                                InputStream inputStream = socket.getInputStream();
                                while ((len = inputStream.read(data)) != -1 ) {
                                    System.out.println(new String(data, 0, len));
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
