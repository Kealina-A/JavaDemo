package netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 1. SimpleChannelInboundHandler： 实现了 ChannelInboundHandlerAdapter
 * 2. HttpObject： 客户端和服务器端相互通讯的数据被封装成HttpObject
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {

            System.out.println("msg 类型 ="+msg.getClass());
            System.out.println("客户端地址 ：" +ctx.channel().remoteAddress());

            ByteBuf content = Unpooled.copiedBuffer("Hello，我是服务器",CharsetUtil.UTF_8);

            // 构建一个http的相应返回
            HttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"type/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            ctx.writeAndFlush(httpResponse);


        }
    }
}
