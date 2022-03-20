package netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf1 {

    public static void main (String[] args) {

        /**
         * 1. 与ByteBuffer 相比 byteBuf 不需要 flip,底层维护了一个writeIndex 和readIndex,分别是读的下标和写的下标
         * 2. 能过readIndex,writeIndex,capacity 分成三段
         * 0-readIndex: 已读区
         * readIndex-writeIndex : 可读区
         * writeIndex- capacity : 可写区
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println(buffer.capacity());
        for (int i = 0; i < 10; i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println("ok");
    }
}
