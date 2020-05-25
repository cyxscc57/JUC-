package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/*
    CountDownLatch:闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
    //应用场景：
    根据类型计算库存，最终计算总和平均值平均销售时间(多线程并行计算，最终再计算总运算)
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(countDownLatch);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时=" + (end - start)+"ms");
    }
}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        //线程安全
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                //5个线程访问每次递减1
                latch.countDown();
            }
        }

    }
}