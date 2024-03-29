package io.saso.dash.server.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.saso.dash.server.Server;
import me.mazeika.uconfig.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DashServer implements Server
{
    private static final Logger logger = LogManager.getLogger();

    private final Config config;
    private final ChannelHandler[] handlers;

    @Inject
    public DashServer(Config config,
                      @Named("server http handlers") ChannelHandler[] handlers)
    {
        this.config = config;
        this.handlers = handlers;
    }

    @Override
    public void start()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new Initializer());

            ChannelFuture f = b.bind(
                    config.getOrDefault("server.bind.host", "127.0.0.1"),
                    config.getOrDefault("server.bind.port", 80));
            Channel ch = f.sync().channel();

            logger.info("Server started at {}", ch.localAddress());
            ch.closeFuture().sync();
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private class Initializer extends ChannelInitializer<SocketChannel>
    {
        @Override
        protected void initChannel(SocketChannel ch)
        {
            ChannelPipeline p = ch.pipeline();

            p.addLast(new HttpServerCodec());
            p.addLast(new HttpObjectAggregator(65536));
            p.addLast(handlers);
        }
    }
}
