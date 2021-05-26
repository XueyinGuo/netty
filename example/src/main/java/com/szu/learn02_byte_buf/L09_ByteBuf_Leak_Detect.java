package com.szu.learn02_byte_buf;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 *
 * TODO 如何内存泄露检测的
 *
 * @Date 2021/5/26 16:24
 */

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class L09_ByteBuf_Leak_Detect {
    static ReferenceQueue<Object> queue;

    public static void main(String[] args) throws Exception {
        queue = new ReferenceQueue<>();


        Set<LeakDetect> allLeaks =
                Collections.newSetFromMap(new ConcurrentHashMap<LeakDetect, Boolean>());
        byte[] bytes = {};
        LeakDetect<byte[]> byteWeakReference = new LeakDetect(bytes, queue, allLeaks);
        bytes = null;
        System.gc();
        System.runFinalization();
        reportLeak();
    }

    private static void reportLeak() throws Exception {
        LeakDetect<?> poll = (LeakDetect)queue.poll();
        if (poll != null){
            boolean dispose = poll.dispose();
            System.out.println(dispose);
        }
    }

    static class LeakDetect<T>
            extends WeakReference<Object> {

        public Set<LeakDetect> allLeaks;

        public LeakDetect(Object referent, ReferenceQueue queue, Set<LeakDetect> allLeaks) {
            super(referent, queue);

            allLeaks.add(this);
            this.allLeaks = allLeaks;
        }

        boolean dispose() throws Exception {
            clear0();
            /*
             * 上一步切除了 弱引用的 reference
             *
             * 这个 set 的 key 是同一个弱引用，
             * 此时 remove 一个 null 的时候自然会 返回 false
             * */
            return allLeaks.remove(this);
        }

        private void clear0() throws Exception {
            Class<? extends LeakDetect> aClass = this.getClass();
            Field referent = aClass.getDeclaredField("referent");
            referent.setAccessible(true);
            referent.set(aClass, null);
        }
    }


}
