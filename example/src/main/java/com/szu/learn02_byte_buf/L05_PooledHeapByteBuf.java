package com.szu.learn02_byte_buf;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/4 21:06
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class L05_PooledHeapByteBuf {

    public static void main(String[] args) {

        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);
        ByteBuf buffer1 = allocator.buffer(50, 1024);
        buffer1.release();
        ByteBuf buffer2 = allocator.buffer(50, 1024);


    }

}
