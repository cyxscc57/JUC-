package com.atguigu.juc;

import java.util.ArrayList;
import java.util.List;
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
public class TestThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1、创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        ThreadPoolDemo tpd=new ThreadPoolDemo();
        List<Future<Integer>> list=new ArrayList<>();
        //Callable方式
        for (int i = 0; i <10 ; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int j = 0; j <= 100; j++) {
                        sum += j;
                    }
                    return sum;
                }
            });
            list.add(future);
        }
        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }
        //2、为线程池中的线程分配任务
//        for (int i = 0; i <10 ; i++) {
//            pool.submit(tpd);
//        }
        //3、关闭线程池
        pool.shutdown();
//        new Thread(tpd).start();
//        new Thread(tpd).start();
    }
}

class ThreadPoolDemo implements  Runnable{
    private int i=0;

    @Override
    public void run() {
        while (i<=100){
            System.out.println(Thread.currentThread().getName()+" : "+i++);
        }
    }
}