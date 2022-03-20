package netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count=0;

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        // 接收到数据并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到的信息如下：");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(content,CharsetUtil.UTF_8));

        System.out.println("服务器接收到的数据包数="+(++count));

        //创建协议包对象
        byte[] responseContent = String.valueOf(UUID.randomUUID()).getBytes(StandardCharsets.UTF_8);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseContent.length);
        messageProtocol.setContent(responseContent);
        ctx.writeAndFlush(messageProtocol);
    }
}
