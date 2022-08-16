package com.example.demo.threadpool;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ThreadDemo {
    /**
     * Executors 返回线程池对象的弊端如下
     *
     * FixedThreadPool 和 SingleThreadExecutor ： 允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致 OOM。
     * CachedThreadPool 和 ScheduledThreadPool ： 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM。
     *
     */
    static ExecutorService threadPool = Executors.newFixedThreadPool(10);


    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;
    //使用阿里巴巴推荐的创建线程池的方式
    //通过ThreadPoolExecutor构造函数自定义参数创建
    /**
     * 线程池的状态及转换
     *              ---shutdown()---> 【shutdown】---阻塞队列为空workerCount (有效线程数) 为0 --->
     *            /                                                                           \
     * 【running】                                                                               -----> 【tidying】  -----terminated()----> 【terminated】
     *            \                                                                         /
     *              ---shutdownNow()---> 【stop】 ---workerCount (有效线程数) 为0 --->
     */
    static   ThreadPoolExecutor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy());


    /**
     * 底层原理
     * newFixedThreadPool(nThreads) 内部调用了 new ThreadPoolExecutor构造函数自定义参数创建,
     * 其中corePoolSize 和 maximumPoolSize 都被设置为 nThreads
     *
     * 运行机制
     * 1.如果当前运行的线程数小于 corePoolSize， 如果再来新任务的话，就创建新的线程来执行任务；
     * 2.当前运行的线程数等于 corePoolSize 后， 如果再来新任务的话，会将任务加入 LinkedBlockingQueue；
     * 3.线程池中的线程执行完 手头的任务后，会在循环中反复从 LinkedBlockingQueue 中获取任务来执行
     *
     *
     * 存在的问题
     * FixedThreadPool 使用无界队列 LinkedBlockingQueue（队列的容量为 Integer.MAX_VALUE）作为线程池的工作队列会对线程池带来如下影响 ：
     *
     * 1.当线程池中的线程数达到 corePoolSize 后，新任务将在无界队列中等待，因此线程池中的线程数不会超过 corePoolSize；
     * 2.由于使用无界队列时 maximumPoolSize 将是一个无效参数，因为不可能存在任务队列满的情况。所以，通过创建 FixedThreadPool的源码可以看出创建的 FixedThreadPool 的 corePoolSize 和 maximumPoolSize 被设置为同一个值。
     * 3.由于 1 和 2，使用无界队列时 keepAliveTime 将是一个无效参数；
     * 4.运行中的 FixedThreadPool（未执行 shutdown()或 shutdownNow()）不会拒绝任务，在任务比较多的时候会导致 OOM（内存溢出）。
     */
    public void FixedThreadPoolDemo(){
        /**
         * 创建一个可重用固定数量线程的线程池(核心线程和最大线程都是参数10)
         * 任务先建线程，线程超过核心线程放队列LinkedBlockingQueue，
         * 看源码!!!
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
    }

    /**
     *运行机制
     * 1.如果当前运行的线程数少于 corePoolSize，则创建一个新的线程执行任务；
     * 2.当前线程池中有一个运行的线程后，将任务加入 LinkedBlockingQueue
     * 3.线程执行完当前的任务后，会在循环中反复从LinkedBlockingQueue 中获取任务来执行；
     *
     * 存在的问题
     * SingleThreadExecutor 使用无界队列 LinkedBlockingQueue 作为线程池的工作队列（队列的容量为 Intger.MAX_VALUE）。
     * SingleThreadExecutor 使用无界队列作为线程池的工作队列会对线程池带来的影响与 FixedThreadPool 相同。
     * 说简单点就是可能会导致 OOM
     *
     */
    public void SingleThreadExecutorDemo(){
        /**
         * 返回只有一个线程的线程池(核心线程和最大线程都是固定的1)
         * 任务先建线程，线程超过核心线程放队列LinkedBlockingQueue，
         * 看源码!!!
         */
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
    }


    /**
     *运行机制
     * 1.首先执行 SynchronousQueue.offer(Runnable task) 提交任务到任务队列。
     * 如果当前 maximumPool 中有闲线程正在执行 SynchronousQueue.poll(keepAliveTime,TimeUnit.NANOSECONDS)，
     * 那么主线程执行 offer 操作与空闲线程执行的 poll 操作配对成功，
     * 主线程把任务交给空闲线程执行，execute()方法执行完成，否则执行下面的步骤 2；
     * 2.当初始 maximumPool 为空，或者 maximumPool 中没有空闲线程时，
     * 将没有线程执行 SynchronousQueue.poll(keepAliveTime,TimeUnit.NANOSECONDS)。
     * 这种情况下，步骤 1 将失败，此时 CachedThreadPool 会创建新线程执行任务，execute 方法执行完成；
     *
     *存在的问题
     * CachedThreadPool 的corePoolSize 被设置为空（0），maximumPoolSize被设置为 Integer.MAX.VALUE，即它是无界的，这也就意味着如果主线程提交任务的速度高于 maximumPool 中线程处理任务的速度时，CachedThreadPool 会不断创建新的线程。极端情况下，这样会导致耗尽 cpu 和内存资源
     * CachedThreadPool允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM。
     */
    public void CachedThreadPoolDemo(){
        /**
         * 创建一个线程池，根据需要创建新线程，但会在先前构建的线程可用时重用它(核心线程是0 最大线程是 Integer.MAX_VALUE)
         * 任务放队列SynchronousQueue，线程去队列取
         * 看源码!!!
         */
        ExecutorService threadPool = Executors.newCachedThreadPool();
    }

    /**
     *ScheduledThreadPoolExecutor 使用的任务队列 DelayQueue 封装了一个 PriorityQueue，
     * PriorityQueue 会对队列中的任务进行排序，执行所需时间短的放在前面先被执行(ScheduledFutureTask 的 time 变量小的先执行)，
     * 如果执行所需时间相同则先提交的任务将被先执行(ScheduledFutureTask 的 squenceNumber 变量小的先执行)。
     *
     * 运行机制
     * 1.当调用 ScheduledThreadPoolExecutor 的 scheduleAtFixedRate() 方法或者 scheduleWithFixedDelay() 方法时，会向 ScheduledThreadPoolExecutor 的 DelayQueue 添加一个实现了 RunnableScheduledFuture 接口的 ScheduledFutureTask 。
     * 2.线程池中的线程从 DelayQueue 中获取 ScheduledFutureTask，然后执行任务。
     *
     * 具体流程
     *  线程 1 从 DelayQueue 中获取已到期的 ScheduledFutureTask（DelayQueue.take()）。到期任务是指 ScheduledFutureTask的 time 大于等于当前系统的时间；
     *  线程 1 执行这个 ScheduledFutureTask；
     *  线程 1 修改 ScheduledFutureTask 的 time 变量为下次将要被执行的时间；
     *  线程 1 把这个修改 time 之后的 ScheduledFutureTask 放回 DelayQueue 中（DelayQueue.add())
     *
     */
    public void ScheduledThreadPoolExecutorDemo(){
        /**
         * 在给定的延迟后运行任务，或者定期执行任务。(核心线程是参数10 最大线程是 Integer.MAX_VALUE)
         * 任务放队列DelayedWorkQueue，线程去队列取
         * 看源码!!!
         */
        ExecutorService threadPool = Executors.newScheduledThreadPool(10);
    }



    public static void main(String[] args) throws Exception{

        callableFuture();
//        callableDemo();
//        runnableDemo();
    }

    /**
     * Runnable 没有返回值
     * @throws Exception
     */
    public static void runnableDemo() throws Exception{
        for (int i = 0; i < 10; i++) {
            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
            MyRunnable worker = new MyRunnable(+ i);
            //执行Runnable
            executor.execute(worker);
        }
        //终止线程池
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("end");
    }




    /**
     * Callable 可以有返回值
     * @throws Exception
     */
    public static void callableDemo() throws Exception{

        MyCallable callable = new MyCallable();
        Future<String> submit = threadPool.submit(callable);
        // 会等待线程执行完毕 -- 获取返回结果
        String o = submit.get();
        System.out.println(o);
        if(!submit.isDone()){
            System.out.println("手动终止");
            submit.cancel(true);
        }
        System.out.println("end");
        threadPool.shutdown();
    }


    /**
     * 通过Future 获取到所有线程的返回值
     */
    public static void callableFuture(){
        List<Future<String>> futureList = new ArrayList<>();
        Callable<String> callable = new MyCallable();
        for (int i = 0; i < 10; i++) {
            //提交任务到线程池
            Future<String> future = executor.submit(callable);
            //将返回值 future 添加到 list，我们可以通过 future 获得 执行 Callable 得到的返回值
            futureList.add(future);
        }
        for (Future<String> fut : futureList) {
            try {
                System.out.println(new Date() + "::" + fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //关闭线程池
        executor.shutdown();
    }
}
