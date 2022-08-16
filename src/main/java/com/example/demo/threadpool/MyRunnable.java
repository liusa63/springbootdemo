package com.example.demo.threadpool;

public class MyRunnable implements Runnable{

    private int param;

    public MyRunnable(int param) {
        this.param = param;
    }

    @Override
    public void run() {
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(param+" run 执行结束...");
        }
    }
}
