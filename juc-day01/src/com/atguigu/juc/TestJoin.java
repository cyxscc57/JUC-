package com.atguigu.juc;

public class TestJoin {
    public static void main(String[] args) {
        System.out.println("主线程正在执行");
        Thread previousThread=new Thread(new PreviousThread());
        previousThread.start();
        try {
            previousThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行结束");
    }
}
class PreviousThread implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"正在执行");
    }
}
