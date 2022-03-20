package netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel (SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        // 1.加入netty里提供的handler，HttpServerCodec， 针对http请求进行解码
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        // 2.增加一个自定义的handler
        pipeline.addLast("MyHttpHandler",new HttpHandler()); //设置自定义的处理器
    }
}
