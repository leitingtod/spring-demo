package com.example.demo.threadlocal;

import java.util.Random;

public class ThreadLocalDemo implements Runnable {
    private final static ThreadLocal<Student> studentLocal = new ThreadLocal<Student>();

    public void run() {
        accessStudent();
    }

    public static void main() {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        Thread t1 = new Thread(demo, "a");
        Thread t2 = new Thread(demo, "b");
        t1.start();
        t2.start();
    }

    private void accessStudent() {
        String threadName = Thread.currentThread().getName();
        System.out.printf("Thread %s is running!\n", threadName);

        Random random = new Random();
        int age = random.nextInt(100);

        System.out.printf("Thread %s set age to: %d\n", threadName, age);
        Student s = getStudent();
        s.setAge(age);

        System.out.printf("Thread %s first read age is: %d\n", threadName, age);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread %s second read age is: %d\n", threadName, age);
    }

    private Student getStudent() {
        Student s = (Student) studentLocal.get();
        if(s == null) {
            s = new Student();
            studentLocal.set(s);
        }
        return s;
    }
}