package io.saso.dash.server.handlers

import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.WebSocketFrame

public abstract class ServerWSHandler :
        SimpleChannelInboundHandler<WebSocketFrame>()
{ /* empty */ }
