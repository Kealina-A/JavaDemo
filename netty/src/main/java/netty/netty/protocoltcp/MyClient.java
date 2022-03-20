package netty.netty.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

    public static void main (String[] args) throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            System.out.println("客户端准备好了。。。");

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6666).sync();
            cf.channel().closeFuture().sync();

        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}
