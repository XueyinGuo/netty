package com.szu.learn02_byte_buf;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/4 20:32
 */

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;

public class L04_UnpooledDirectByteBuffer {

    public static void main(String[] args) {
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        UnpooledDirectByteBuf directByteBuf = new UnpooledDirectByteBuf(allocator, 16, 1024);
        byte[] bytes = "ByteBuf Test".getBytes();
        directByteBuf.setBytes(0, bytes, 0, bytes.length);
        directByteBuf.writerIndex(16);
        UnpooledHeapByteBuf heapByteBuf = new UnpooledHeapByteBuf(allocator, 10, 1024);
        directByteBuf.readBytes(heapByteBuf);
        /*
        * 内存不够用，动态扩容，还是 新申请 再复制
        * */
        directByteBuf.writeBytes(directByteBuf);
        directByteBuf.readerIndex(10);
        /*
        * 一样的方法
        * */
        directByteBuf.discardReadBytes();
        System.out.println();
    }

}
