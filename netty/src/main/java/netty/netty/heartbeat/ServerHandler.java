package netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;


public class ServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void userEventTriggered (ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = "";
            switch (idleStateEvent.state()) {
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress()+"超时类型：" +eventType);
        }
    }
}
