package com.example.demo.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 倒计时门栓
 * 可以等在所有计数线程执行完毕后做统一处理
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 6;
        // 创建一个具有固定线程数量的线程池对象（推荐使用构造方法创建）
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadnum = i;
            threadPool.execute(() -> {
                try {
                    //处理文件的业务操作
                    Thread.sleep(1000);
                    System.out.println(threadnum);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //表示一个文件已经被完成
                    // 减少计数，直到计数为0，释放
                    countDownLatch.countDown();
                }

            });
        }
        // 等待直到计数变为0
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("finish");
    }
}
