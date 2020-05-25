package com.atguigu.juc;


import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {
    public static void main(String[] args) {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 500000000L);

        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时为"+ Duration.between(start,end).toMillis());//147
    }
    @Test
    public void test1(){
        Instant start = Instant.now();
        long sum=0;
        for (int i = 0; i <=500000000L ; i++) {
            sum+=i;
        }
        Instant end = Instant.now();
        System.out.println("耗时为"+ Duration.between(start,end).toMillis());//208
    }
    //java8 新特性
    @Test
    public void test2(){
        Instant start = Instant.now();
        Long sum = LongStream.rangeClosed(0L, 500000000L).
                parallel().
                reduce(0L,Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗时为"+ Duration.between(start,end).toMillis());
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID = 5595531583244691826L;
    private long start;
    private long end;
    private static final long THRESHOLD = 10000l;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        //计算的长度
        long length = end - start;
        //如果小于临界值用for循环计算，大于的话进行拆分
        if (length <= THRESHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //拆分
            long middle = (end +start) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();//拆分，并加入线程队列
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
