package netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf2 {

    public static void main (String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("HelloWorld!", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            System.out.println(new String(array,CharsetUtil.UTF_8));

            System.out.println("byteBuf = "+byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readByte());
            System.out.println(byteBuf.readableBytes()); // 可读容量
            System.out.println(byteBuf.getCharSequence(0,4,CharsetUtil.UTF_8)); // 取某个区间内的数据
        }

    }
}
