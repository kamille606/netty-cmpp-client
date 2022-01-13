package cn.lain.cmpp.client.handler;

import cn.lain.cmpp.client.model.CmppHead;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CmppEncoder extends MessageToByteEncoder<CmppHead> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CmppHead msg, ByteBuf out) throws Exception {
        msg.doEncode();
        out.writeBytes(msg.getMsgBytes());
    }

}
