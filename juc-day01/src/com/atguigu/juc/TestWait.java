package com.atguigu.juc;

public class TestWait {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        WaitThread waitThread=new WaitThread();
        Thread2 thread2=new Thread2();
        Thread t1=new Thread(waitThread,"等待线程");
        Thread t2=new Thread(thread2,"线程2");
        t1.start();
        t2.start();

    }
}
class WaitThread implements Runnable{

    @Override
    public void run() {
        synchronized (this){
            try {
                wait(1000);
                System.out.println(Thread.currentThread().getName()+"正在执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
class Thread2 implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"正在执行");
    }
}
