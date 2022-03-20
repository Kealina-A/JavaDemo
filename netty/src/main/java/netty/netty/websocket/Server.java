package netty.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {

    private int port;

    public Server (int port) {
        this.port = port;
    }

    public static void main (String[] args) {
        new Server(7777).run();
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
                            // 基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 是以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // http 数据在传输过程中是分段的，HttpObjectAggregator就是可以将多个段聚合
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1.对应websocket ,它的数据是以帧（frame）开工传递
                             * 2.可以看到webSocketFrame下的6个子类
                             * 3.浏览器请求时 ws://localhost:6666/hello 表示请求的uri
                             * 4.WebSocketServerProtocolHandler 核心功能就是将http协议升级为ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new TextWebSocketFrameHandler()); //设置自定义的处理器,处理业务逻辑
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
