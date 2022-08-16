package com.example.demo.threadpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        CompletableFuture<Void> task1 =
                CompletableFuture.supplyAsync(()->{
                    //自定义业务操作
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("task1");
                    return null;
                });

        CompletableFuture<Void> task6 =
                CompletableFuture.supplyAsync(()->{
                    //自定义业务操作
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("task6");
                    return null;
                });

        CompletableFuture<Void> headerFuture=CompletableFuture.allOf(task1,task6);

        try {
            headerFuture.join();
        } catch (Exception ex) {
            //......
        }
        System.out.println("all done. ");
    }
    
    public void listBatchDemo(){
        //文件夹位置
//        List<String> filePaths = Arrays.asList("1","2","3");
//        // 异步处理所有文件
//        List<CompletableFuture<String>> fileFutures = filePaths.stream()
//                .map(filePath -> doSomeThing(filePath))
//                .collect(Collectors.toList());
//        // 将他们合并起来
//        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
//                fileFutures.toArray(new CompletableFuture[fileFutures.size()])
//        );
    }

    private List<String> doSomeThing(String filePath) {
        return new ArrayList<>();
    }
}
