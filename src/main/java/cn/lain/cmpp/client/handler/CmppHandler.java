package cn.lain.cmpp.client.handler;

import cn.lain.cmpp.client.NettyClient;
import io.netty.channel.ChannelHandlerAdapter;

public class CmppHandler extends ChannelHandlerAdapter {

    private final NettyClient client;

    public CmppHandler(NettyClient client) {
        this.client = client;
    }

}
