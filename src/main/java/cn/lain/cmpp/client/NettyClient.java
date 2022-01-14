package cn.lain.cmpp.client;

import cn.lain.cmpp.client.handler.CmppDecoder;
import cn.lain.cmpp.client.handler.CmppEncoder;
import cn.lain.cmpp.client.handler.CmppHandler;
import cn.lain.cmpp.model.CmppHead;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int RECONNECT_SLEEP_TIME = 10000;
    private static final int CONNECT_TIMEOUT = 20;

    private Channel channel;
    private String host;
    private int port;
    private String username;
    private String password;

    public NettyClient(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void doConnect(final String host, final int port) throws InterruptedException {
        NettyClient client = this;
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(host, port)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                ChannelPipeline pipeline = channel.pipeline();
                                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 0, true))
                                        .addLast(new IdleStateHandler(0, 0, CONNECT_TIMEOUT, TimeUnit.SECONDS))
                                        .addLast(new CmppDecoder())
                                        .addLast(new CmppEncoder())
                                        .addLast(new CmppHandler(client));
                            }
                });
        ChannelFuture channelFuture = bootstrap.connect().sync();
        channel = channelFuture.channel();
        channel.writeAndFlush("");
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    public void start() {
        try {
            doConnect(host, port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        channel.close();
    }

    public boolean submit(CmppHead submit) {
        if (isActive()) {
            channel.writeAndFlush(submit);
            return true;
        }
        return false;
    }

    public boolean isActive() {
        if (channel == null) {
            return false;
        }
        return channel.isOpen() && channel.isActive() && channel.isWritable();
    }

    public void reConnect(int reConnect) {
        int times = 0;
        while (times < reConnect) {
            try {
                if (!isActive()) {
                    start();
                } else {
                    Thread.sleep(RECONNECT_SLEEP_TIME);
                    times++;
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(RECONNECT_SLEEP_TIME);
                    times++;
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
