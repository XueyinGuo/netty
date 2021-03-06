package com.szu.learn02_byte_buf;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/4 18:02
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;

public class L01_ReadBytes {

    public static void main(String[] args) {

        String s = "ByteBuf Test";
        byte[] bytes = s.getBytes();
        ByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        UnpooledHeapByteBuf unpooledHeapByteBuf = new UnpooledHeapByteBuf(allocator, 50, 1024);
        /* System.arraycopy(src, srcIndex, array, index, length); 直接copy，很暴力 */
        unpooledHeapByteBuf.setBytes(0, bytes, 0, bytes.length);
        unpooledHeapByteBuf.writerIndex(bytes.length+1);
        byte[] res = new byte[12];
        /* unpooledHeapByteBuf 是源数组 */
        /* 1. 检查 目标数组的长度 是否大于0
         * 2. 检查目前可读的长度（writerIndex）是否大于目标数组长度 */
        ByteBuf byteBuf = unpooledHeapByteBuf.readBytes(res);
        System.out.println();

    }



}
