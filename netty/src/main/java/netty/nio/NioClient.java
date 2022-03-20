package netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main (String[] args) throws Exception{

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        // 已经连接上了，可以发消息了
        String str ="Hello,你好啊!";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //将buffer的数据写到channel
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
