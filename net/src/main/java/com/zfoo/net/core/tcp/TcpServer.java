/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.net.core.tcp;

import com.zfoo.net.core.AbstractServer;
import com.zfoo.net.handler.ServerRouteHandler;
import com.zfoo.net.handler.codec.tcp.TcpCodecHandler;
import com.zfoo.net.handler.idle.ServerIdleHandler;
import com.zfoo.util.net.HostAndPort;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class TcpServer extends AbstractServer {

    public TcpServer(HostAndPort host) {
        super(host);
    }

    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new ChannelHandlerInitializer();
    }


    private static class ChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel channel) {
            channel.pipeline().addLast(new IdleStateHandler(0, 0, 180));
            channel.pipeline().addLast(new ServerIdleHandler());
            channel.pipeline().addLast(new TcpCodecHandler());
            channel.pipeline().addLast(new ServerRouteHandler());
        }
    }
}
