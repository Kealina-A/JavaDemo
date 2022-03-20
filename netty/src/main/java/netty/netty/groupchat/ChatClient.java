package netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {

    private int port;
    private String address;

    public ChatClient ( String address,int port) {
        this.port = port;
        this.address = address;
    }

    public static void main (String[] args) {
        new ChatClient("127.0.0.1", 6666).run();
    }

    public void run (){
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel (SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new ClientHandler()); //设置自定义的处理器
                        }
                    });
            ChannelFuture cf = bootstrap.connect(address,port).sync();
            Channel channel = cf.channel();
            System.out.println("--------------------"+cf.channel().localAddress()+"----------------------");
            Scanner sc= new Scanner(System.in);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                channel.writeAndFlush(s+"\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
