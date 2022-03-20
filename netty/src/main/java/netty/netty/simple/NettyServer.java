package netty.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main (String[] args) throws InterruptedException {


        /**
         * 1. 创建两个线程组 bossGroup 和 workGroup
         * 2. bossGroup 只处理连接请求 ，真正的和客户端业务处理 会交给 workGroup
         * 3. 两个线程组都是无限循环的
         * 4. 两个线程组包含的子线程（NioEventLoop） 默认是以cpu的核数*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //用链式编程来设置
            bootstrap.group(bossGroup,workGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) // NioServerSocketChannel 作为通道
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持活动的连接状态
//                    .handler(null) //handler 是针对bossGroup ,childHandler 是针对workGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel (SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler()); //设置自定义的处理器
                            // 给workgroup 的eventLoop 对应的通道设置处理器
                        }
                    });

            System.out.println("服务器已经准备好了。。。");
            //绑定一个端口，并返回一个ChannelFuture对象
            //启动服务器
            ChannelFuture cf = bootstrap.bind(6666).sync();

            //对关闭通过进行监听，有关闭事件的时候才会到这
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
