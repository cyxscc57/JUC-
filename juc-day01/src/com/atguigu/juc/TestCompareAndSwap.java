package com.atguigu.juc;

import java.util.Random;

public class TestCompareAndSwap {
    public static void main(String[] args) {
        CompareAndSwap cas=new CompareAndSwap();
        for (int i = 0; i <10 ; i++) {
            new Thread(() -> {
                int expectedValue = cas.get();
                boolean b = cas.compareAndSet(expectedValue, new Random().nextInt(100));
                System.out.println(b);
            }).start();
        }
    }
}

//模拟CAS算法
class CompareAndSwap{
    private int value;
    //内存值
    public synchronized int get(){
        return value;
    }
    //比较
    public synchronized int compareAndSwap(int expectedValue,int newValue){
        int oldValue=value;
        if(oldValue==expectedValue){
            this.value=newValue;
        }
        return oldValue;
    }
    //设置值
    public synchronized boolean compareAndSet(int expectedValue,int newValue){
        return expectedValue==compareAndSwap(expectedValue,newValue);
    }
}
