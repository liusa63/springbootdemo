package com.example.demo.threadpool;

import java.util.concurrent.*;

/**
 * 倒计时门栓
 * 可以等在所有计数线程执行完毕后做统一处理
 */
public class CountDownLatchCyclicBarrierDemo {

    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            5,
            10,
            1,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * CountDownLatch 是计数器，线程完成一个记录一个，只不过计数不是递增而是递减，
     * 而 CyclicBarrier 更像是一个阀门，需要所有线程都到达，阀门才能打开，然后继续执行。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//        countDownLatchDemo();
        cyclicBarrierDemo();
    }


    /**
     * cyclicBarrier
     * 分组处理，若数量不够初始化时指定的数量，则会一直等待
     *
     * new CyclicBarrier(5) 5个线程一组，全部执行完毕后放行
     *
     * 内部通过一个 count 变量作为计数器，count 的初始值为 parties 属性的初始化值，
     * 每当一个线程到了栅栏这里了，那么就将计数器减一。如果 count 值为 0 了，
     * 表示这是这一代最后一个线程到达栅栏，就尝试执行我们构造方法中输入的任务。
     *
     *
     * @throws InterruptedException
     */
    public static void cyclicBarrierDemo() throws InterruptedException {
        int threadCount = 6;
        // 需要同步的线程数量
//        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
            System.out.println("线程执行完之后先干这个..");
        });
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
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("完成了："+threadnum);

            });
        }
        threadPool.shutdown();
        System.out.println("finish");
    }


    /**
     * CountDownLatch 的使用
     * 初始化时指定需要等到完成的线程数量，且只能指定一次
     * 在线程内部进行调用countDown()方法将数量减1，countDownLatch.await()直至为0
     * 若数量不匹配则会一直等待
     *
     * @throws InterruptedException
     */
    public static void countDownLatchDemo() throws InterruptedException {
        int threadCount = 6;
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
