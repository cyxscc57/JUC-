package com.atguigu.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList:"写入并复制"
 * 注意：在进行添加操作时，效率低，每次添加都会进行复制，开销非常大。并发迭代操作非常多时，可以选择使用。
 */
public class TestCopyAndWriteArrayList {
    public static void main(String[] args) {
        HelloThread ht=new HelloThread();
        for (int i = 0; i <10 ; i++) {
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable{
    //private static List<String> list= Collections.synchronizedList(new ArrayList<>());
    private static CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<>();
    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }
    @Override
    public void run() {
        Iterator iterator=list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
            list.add("AA");
        }
    }
}
