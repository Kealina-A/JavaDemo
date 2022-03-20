package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class NioServer {

    public static void main (String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 注册accept 事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 监听连接，最多阻塞1s
            if (selector.select(1000)==0) {
                System.out.println("等待1秒，无连接");
                continue;
            }
            // 有客户端连接进来
            // 获取有事件发生的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    System.out.println("有请求进来 "+key.hashCode());
                    //根据key对应的事件做相应的处理
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成一个socketChannel "+socketChannel.hashCode());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    // 通过key 获取到 channel
                    SocketChannel channel =(SocketChannel) key.channel();
                    // 获取该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    channel.read(byteBuffer);
                    System.out.println("from 客户端 "+new String(byteBuffer.array()));
                }
                //手动移除当前key，防止重复操作
                iterator.remove();
            }
        }




    }
}
