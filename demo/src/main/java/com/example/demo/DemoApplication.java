package com.example.demo;

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

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
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
