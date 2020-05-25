package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一、创建执行线程的方式三：实现Callable接口。相较于Runnable接口，方法多了一个返回值，并且可以抛出异常
 * 二、执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果，FutureTask是Future的实现类
 */
public class TestCallable {
    public static void main(String[] args) {
        ThreadDemo td=new ThreadDemo();
        FutureTask<Integer> futureTask=new FutureTask<Integer>(td);

        new Thread(futureTask).start();

        try {
            Integer integer = futureTask.get();//FutureTask类似于闭锁的作用，等计算完成后才会执行get
            System.out.println(integer);
            System.out.println("----------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum=0;
        for (int i = 0; i <=100 ; i++) {
            sum+=i;
        }

        return sum;
    }
}
