package com.atguigu.juc;

public class NonAtomicOfVolatile {
    public volatile int inc=0;
    public void increase(){
        inc++;
    }
    /*
        若i=10
        如果线程1先读取到i的值，还没进行增加又刚好被阻塞。
        线程2从主存中去读到i的值就为10，对i进行增加后i的值变为11，放入到主存中

        此时线程1继续工作，对原来的10进行自增变为11，最后写入到主存中。
        所以两次自增操作最终只使得i的 值自增了1


        其实严格的说，对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性。
        在《Java并发编程的艺术》中有这一段描述：“在多处理器下，为了保证各个处理器的缓存是一致的，就会实现缓存一致性协议，
        每个处理器通过嗅探在总线上传播的数据来检查自己缓存的值是不是过期了，当处理器发现自己缓存行对应的内存地址被修改，
        就会将当前处理器的缓存行设置成无效状态，当处理器对这个数据进行修改操作的时候，
        会重新从系统内存中把数据读到处理器缓存里。”我们需要注意的是，这里的修改操作，是指的一个操作。
        对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性

        为什么自增操作会出现那样的结果呢？可以知道自增操作是三个原子操作组合而成的复合操作。
        在一个操作中，读取了inc变量后，是不会再读取的inc的，所以它的值还是之前读的10，它的下一步是自增操作。
     */
    public static void main(String[] args) {
        final NonAtomicOfVolatile nonAtomicOfVolatile=new NonAtomicOfVolatile();
        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <100 ; j++) {
                        System.out.println(nonAtomicOfVolatile.inc);
                        nonAtomicOfVolatile.increase();
                    }
                }
            }).start();
        }
        while(Thread.activeCount()>0){
            Thread.yield();
        }
        System.out.println(nonAtomicOfVolatile.inc);
    }

}
