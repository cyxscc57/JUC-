package com.atguigu.juc;

import java.util.Random;
import java.util.concurrent.*;

/*
    一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。
                避免线程频繁的创建和销毁耗费资源，提高了响应速度。
    二、线程池的体系结构：
        java.util.concurrent.Executor:负责线程的使用与调度的根接口
          |--ExecutorService子接口：线程池的主要接口
            |--ThreadPoolExecutor 实现类
                |--ScheduledExecutorService子接口：负责线程的嗲用
                    |--ScheduledThreadPoolExecutor:继承了ThreadPoolExecutor,实现了ScheduledExecutorService
    三、工具类：Executors
 ExecutorService   newFixedThreadPool():创建固定大小的线程池
 ExecutorService   newCachedThreadPool():缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 ExecutorService   newSingleThreadExecutor:创建单个线程池，这个线程池中只有一个线程
ScheduledExecutorService  newScheduledThreadPool():创建固定大小的，可以延迟或者定时地执行任务

 */
public class TestScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Integer> future = pool.schedule(new Callable<Integer>() {
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }
            }, 1, TimeUnit.SECONDS);
            System.out.println(future.get());
        }

        pool.shutdown();
    }
}
