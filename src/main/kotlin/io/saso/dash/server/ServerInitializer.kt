package io.saso.dash.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

public abstract class ServerInitializer : ChannelInitializer<SocketChannel>()
{ /* empty */ }