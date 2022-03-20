package netty.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义handler
 *
 * 1. 继承ChannelInboundHandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    // ctx: 通道的上下文，可以拿到pipeline, channel,地址等信息
    // msg: 内容
    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {


        //异步处理 方式1
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                Thread.sleep(10*1000);
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端！11",CharsetUtil.UTF_8));
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        });

        //异步处理 方式2
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(10*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端！22",CharsetUtil.UTF_8));
            } catch (Exception e){
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);
        System.out.println("go on....");
//
//        System.out.println("server ctx ：" +ctx);
//        // byteBuf 是 netty 提供的，相比 Nio 的 ByteBuffer 性能更高
//        ByteBuf byteBuf = (ByteBuf) msg;
//
//        System.out.println("客户端发送的内容："+byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端的地址："+ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete (ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端！",CharsetUtil.UTF_8));
    }

    // 发生异常
    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
