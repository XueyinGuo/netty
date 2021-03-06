package com.szu.learn02_byte_buf;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/4 19:01
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;

public class L02_WriteBytes {

    public static void main(String[] args) {
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        UnpooledHeapByteBuf src = new UnpooledHeapByteBuf(allocator, 50, 1024);
        byte[] bytes = "WriteBytes Test".getBytes();
        src.setBytes(0, bytes, 0, bytes.length);
        src.writerIndex(bytes.length);
        UnpooledHeapByteBuf dst = new UnpooledHeapByteBuf(allocator, 5, 1024);
        /* 1. if 容量不够进行扩容操作
         * 2. 从src拷贝数组到dst  */

        /*
         * 扩容策略：
         * 1. 数组长度不够64字节，直接变成64字节
         * 2. 不够 4MB，直接翻倍
         * 3. 超过 4MB， 每次增加 4MB
         * */
        /* capacity() 新创建一个数组，进行copy，把array的引用指向新扩容后的数组 */
        ByteBuf byteBuf = dst.writeBytes(src);
        System.out.println();
    }

}
