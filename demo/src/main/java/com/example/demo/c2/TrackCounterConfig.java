package com.example.demo.c2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy
public class TrackCounterConfig {
    @Bean
    public TrackCounter trackCounter() {
        return new TrackCounter();
    }

    @Bean
    public CompactDisc blankDisc() {
        BlankDisc cd = new BlankDisc();
        cd.setTitle("Sgt. Pepper's Lonely Hearts Club Band");
        cd.setArtist("The Beatles");

        List<String> tracks = new ArrayList<String>();
        tracks.add("track 0：-------------------------");
        tracks.add("track 1：=========================");
        tracks.add("track 2：8888888888888888888888888");
        tracks.add("track 3：*************************");
        cd.setTracks(tracks);
        return cd;
    }
}
