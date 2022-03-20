package netty.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel (SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyMessageDecoder",new MyMessageDecoder());
        pipeline.addLast("MyMessageEncoder",new MyMessageEncoder());
        pipeline.addLast("MyServerHandler",new MyServerHandler()); //设置自定义的处理器
    }
}
