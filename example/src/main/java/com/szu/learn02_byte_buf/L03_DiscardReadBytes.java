package com.szu.learn02_byte_buf;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/4 19:34
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;

public class L03_DiscardReadBytes {

    public static void main(String[] args) {
        /* preferDirect 设置为 true 好像也没什么两样啊 */
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        UnpooledHeapByteBuf byteBuf = new UnpooledHeapByteBuf(allocator, 50, 1024);
        byte[] bytes = "ByteBuf Test".getBytes();

        ByteBuf buf = byteBuf.setBytes(0, bytes, 0, bytes.length);
        buf.writerIndex(16);
        buf.readerIndex(5);
        buf.discardReadBytes();
    }

}
