package com.szu.learn02_byte_buf;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/5/26 16:24
 */

import io.netty.util.ResourceLeakDetector;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class L08_ByteBuf_Leak_Detect {

    public static void main(String[] args) {
        String str = new String ("泄露了吗");
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        /*
        * 虚引用指向 str，并传参一个 引用队列
        * */
        WeakReference<String> weakReference = new WeakReference<>(str, queue);

        /*
        * 此时 GC 的话，会导致 str 被回收， 但是因为有一个弱引用指向 str， 所以 这个弱引用会被放入引用队列
        * */
        str = null;
        System.out.println(weakReference.get());
        System.gc();
        System.runFinalization();

        /*
        * 此时队列中的不为空了！！！
        * 并且输出 为 true
        * */
        System.out.println(queue.poll() == weakReference) ;

    }


}
