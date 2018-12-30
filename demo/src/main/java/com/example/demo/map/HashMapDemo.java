package com.example.demo.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapDemo {
    public static void main() {
        Map<String, Integer> m = new HashMap<String, Integer>();

        m.put("alice", 10);
        m.put("bob", 12);

        System.out.println("m: " + m);
        System.out.println("m.isEmpty: " + m.isEmpty());
        System.out.println("m.keySet: " + m.keySet());
        System.out.println("m.hasKey alice: " + m.containsKey("alice"));
        System.out.println("m.hasKey alen: " + m.containsKey("alen"));
        System.out.println("m.get bob: " + m.getOrDefault("bob", 10));
        System.out.println("m.get bok: " + m.getOrDefault("bok", 10));
        System.out.println("m.hashcode('bob'): " + m.get("bob").hashCode());
        System.out.println("m.hashcode('alice'): " + m.get("alice").hashCode());
        System.out.println("'alice.hashcode': " + "alice".hashCode());
        System.out.println("----------------------");

        Iterator it = m.keySet().iterator();
        while(it.hasNext()) {
            String key = (String)it.next();
            System.out.print("key: " + key);
            System.out.println("\tvalue:" + m.get(key));
        }
        System.out.println("----------------------");

        Set<Map.Entry<String, Integer>> sets = m.entrySet();
        for(Map.Entry<String, Integer> entry : sets) {
            System.out.print("key:" + entry.getKey() + ", ");
            System.out.println("\tvalue: "+entry.getValue());
        }
        System.out.println("----------------------");

        m.forEach((k, v) -> {
            System.out.print("key:" + k + ", ");
            System.out.println("\tvalue: "+v);
        });

    }
}
