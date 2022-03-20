package netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;
    // 当通道就绪就会触发
    @Override
    public void channelActive (ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 5; i++) {
            String msg = "今天是个好日子,好日子呀好日子，好日子呀好日子！";
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            int length = bytes.length;

            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }


    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到的信息如下：");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content,CharsetUtil.UTF_8));

        System.out.println("客户端接收到的数据包数="+(++count));
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息："+cause.getMessage());
        ctx.close();
    }
}
