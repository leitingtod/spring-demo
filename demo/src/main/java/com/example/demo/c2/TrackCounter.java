package com.example.demo.c2;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class TrackCounter {
    private Map<Integer, Integer> trackCounts = new HashMap<>();

    @Pointcut("execution(* com.example.demo.c2.CompactDisc.playTrack(int)) && args(trackNumber)")
    public void trackPlayed(int trackNumber) {}

    @Before("trackPlayed(trackNumber)")
    public void countTrack(int trackNumber) {
        int curr = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, curr+1);
    }

    public int getPlayCount(int trackNumber) {
        return trackCounts.getOrDefault(trackNumber, 0);
    }
}
