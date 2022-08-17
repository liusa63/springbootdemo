package com.example.demo.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {



    public static void main(String[] args) {
        AtomicInteger cout = new AtomicInteger(1);
        System.out.println(cout.incrementAndGet());
        System.out.println(cout);
        System.out.println(cout.getAndIncrement());
        System.out.println(cout);
    }
}
