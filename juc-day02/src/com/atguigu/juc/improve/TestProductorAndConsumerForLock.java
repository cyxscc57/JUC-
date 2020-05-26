package com.atguigu.juc.improve;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者案例
 */
public class TestProductorAndConsumerForLock {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(productor, "生产者A").start();
        new Thread(consumer, "消费者B").start();
        new Thread(productor, "生产者C").start();
        new Thread(consumer, "消费者D").start();
    }
}

class Clerk {
    private int product = 0;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    //访问共享数据，存在多线程安全问题
    //使用等待唤醒机制，防止产品已满或者产品缺货时仍不断调用
    //进货
    public  void get() {//循环次数=2
        lock.lock();
        try{
            while (product >= 1) {
                System.out.println("产品已满");
                try {
                   condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    /*
        存在问题：如果消费者product = 0  ;循环次数：1，会显示缺货并进入等待释放锁资源,并且等待循环结束
        然后生产者会开始生产产品 product=1;循环次数：2 ，生产完释放锁资源,
        而消费者wait方法结束开始执行，不会经过else的代码，且循环结束。
        接下来生产者的product=1，会进入wait等待状态，但是由于消费者线程的循环已经结束，就不会继续唤醒，从而导致程序一直运行。

        解决方式：去掉else 但是会有新的问题，如果有多个生产者和消费者，如果两个消费者同时进入等待状态，当生产者一唤醒，会导致产品同时减少。
        为了避免虚假唤醒问题，应该总是使用循环进行重新判断
     */
    //卖货
    public  void sale() {//product = 0  ;循环次数：1
        lock.lock();
        try{
            while (product <= 0) {
                System.out.println("缺货");
                try {
                    condition.await();//
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }


    }
}

class Productor implements Runnable {
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }


    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
