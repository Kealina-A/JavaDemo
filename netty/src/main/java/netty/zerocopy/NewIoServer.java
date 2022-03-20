package netty.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 零拷贝测试
 */
public class NewIoServer {

    public static void main (String[] args) throws Exception{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(6666));
        Files.readAllBytes(Paths.get("/path/to/file.txt"));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while(true) {
            SocketChannel accept = serverSocketChannel.accept();
            int readCount=0;

            if (-1 != readCount) {
                readCount = accept.read(byteBuffer);
            }
            byteBuffer.rewind();//倒带 position =1 mark标志作废
        }

    }
}
