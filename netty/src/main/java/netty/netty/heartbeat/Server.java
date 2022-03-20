package netty.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class Server {

    private int port;

    public Server (int port) {
        this.port = port;
    }

    public static void main (String[] args) {
        new Server(6666).run();
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) // NioServerSocketChannel 作为通道
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel (SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            /**
                             * IdleStateHandler 是Netty提供的处理空闲状态的处理器
                             * 1.readerIdleTime: 表示多长时间未读
                             * 2.writerIdleTime: 表示多长时间未写
                             * 3.allIdleTime: 表示多长时间未读写
                             *
                             * 达到条件会出发idleStateEvent
                             * 当idleStateEvent 触发后，就会传给管道的下一个handler处理
                             */
                            pipeline.addLast("idle",new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            pipeline.addLast(new ServerHandler()); //设置自定义的处理器
                        }
                    });
            System.out.println("服务器已经准备好了。。。");
            ChannelFuture cf = bootstrap.bind(port).sync();
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
