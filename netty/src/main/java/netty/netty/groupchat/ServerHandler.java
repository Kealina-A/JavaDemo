package netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class ServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE 是一个全局事件执行器，单例
    private static ChannelGroup channelGroup  =new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch->{
            if (channel!=ch) {
                ch.writeAndFlush("[客户]"+channel.remoteAddress()+" 说 ："+msg);
            } else {
                ctx.channel().writeAndFlush("[自己]"+channel.remoteAddress()+ "：" +msg);
            }
        });

    }



    /**
     * 表示连接建立第一个触发的方法
     */
    @Override
    public void handlerAdded (ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 会自行遍历并发送消息给group里的每个channel
        channelGroup.writeAndFlush("[客户端]"+ctx.channel().remoteAddress()+" 加入了群聊！");
        channelGroup.add(ctx.channel());
    }

    // 表示断开连接
    @Override
    public void handlerRemoved (ChannelHandlerContext ctx) throws Exception {
        String msg = "[客户端]"+ctx.channel().remoteAddress() +"离开啦!";
        channelGroup.writeAndFlush(msg);
        channelGroup.remove(ctx.channel());
    }

    // 表示处于活动状态
    @Override
    public void channelActive (ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() +"上线啦!");
    }

    // 表示处于非活动状态
    @Override
    public void channelInactive (ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() +"下线啦!");
    }


    // 发生异常
    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
