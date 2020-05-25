package com.atguigu.juc;

/**
 * 每个线程都会被分配一个缓存，从主存(堆)中拿数据
 * volatile关键字：当多个线程操作共享数据时，可以保证内存中的数据可见
 * 相较于synchronized是另外一种较为轻量级的同步策略
 * 1、volatile:不具有“互斥性”
 * 2、volatile:不能保证变量的原子性
 */
public class TestVolatile {
    /*
        内存可见性问题：当多个线程操作共享数据时，彼此不可见
     */
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        //主线程，但flag值是在另一个线程发生变化
        while (true) {
            if (td.isFlag()) {
                System.out.println("-----------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
            flag = true;
            System.out.println("flag=" + isFlag());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFlag() {
        return flag;
    }
}
