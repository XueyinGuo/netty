package com.szu.learn02_byte_buf;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/7 20:00
 */

import io.netty.util.Recycler;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.concurrent.locks.LockSupport;

public class L07_PooledDirectBuffer_Recycler {
    static Thread t1, t2;
    private  static final Recycler<CyclerA> CA = new Recycler<CyclerA>() {
        @Override
        protected CyclerA newObject(Handle<CyclerA> handle) {
            return new CyclerA("testA", handle);
        }
    };
    private  static final Recycler<CyclerB> CB = new Recycler<CyclerB>() {
        @Override
        protected CyclerB newObject(Handle<CyclerB> handle) {
            return new CyclerB("testB", handle);
        }
    };
    public static void main(String[] args) {
        ArrayDeque<Object> container = new ArrayDeque<>();
        t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                CyclerA cyclerA = CA.get();
                container.add(cyclerA);
            }
            LockSupport.unpark(t2);
            LockSupport.park();
            for (int i = 0; i < 5; i++) {
                /* 如果其他线程帮自己会收了对象，自己又用到的时候，直接取出来都不用调用构造函数了呢！！！ */
                /* 但是为什么只会收了一个呢？，队列中加进去9个，但是只取到一个？ */
                /* 【答案是 第一次加进去之后，每回收【间隔数】数量的对象才会继续往队列中加入元素，默认间隔为 8，】
                *  【为了控制队列的缓慢增长】  */
                /* TODO 但是那些没有回收的东西，一直存在在队列中会导致内存泄漏吗？
                *   其他线程创建的队列放到 WeakHashMap 中，其中 stack 是虚引用，队列是强引用 WeakHashMap<Stack<?>, WeakOrderQueue>
                *   也就是说，当 持有 stack强引用的那个线程死了，这个stack就被回收了，key没了，value还没看见显示的清除，队列中的东西岂不是访问不到了吗？
                * 往自己队列中搬东西的时候，条件满足则执行cursor.reclaimAllSpaceAndUnlink()，释放本线程中存在的已经死亡的线程的队列 */
                CyclerA cyclerA = CA.get();
                container.add(cyclerA);

            }
        }, "t1");

        t2 = new Thread(() -> {
            LockSupport.park();
            CyclerB cyclerB = CB.get();
            for (int i = 0; i < 27; i++) {
                /* 创建队列，每【回收间隙数】往队列中加一个，默认 8 */
                /*  【为了控制队列的缓慢增长】  */
                CyclerA poll = (CyclerA)container.poll();
                poll.recycle();
            }
            LockSupport.unpark(t1);
            LockSupport.park();


        }, "t2");

        t1.start();
        t2.start();
    }



}
class CyclerA{
    private String value;
    private Recycler.Handle<CyclerA> handle;
    public void setValue(String value) {
        this.value = value;
    }

    public CyclerA(String value, Recycler.Handle<CyclerA> handle) {
        this.value = value;
        this.handle = handle;
    }

    public void recycle(){
        handle.recycle(this);
    }
}
class CyclerB{

    private String value;
    private Recycler.Handle<CyclerB> handle;
    public void setValue(String value) {
        this.value = value;
    }

    public CyclerB(String value, Recycler.Handle<CyclerB> handle) {
        this.value = value;
        this.handle = handle;
    }

    public void recycle(){
        handle.recycle(this);
    }
}