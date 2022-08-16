package com.example.demo.threadpool;

import java.util.concurrent.Callable;

public class MyCallable implements Callable{

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return Thread.currentThread().getName();
    }



}
