package netty.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 当通道就绪就会触发
    @Override
    public void channelActive (ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 ："+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务器！",CharsetUtil.UTF_8));
    }


    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器发送的内容："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址："+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
