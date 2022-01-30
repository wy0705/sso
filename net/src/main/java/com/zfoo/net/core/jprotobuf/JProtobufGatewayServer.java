/*
 * Copyright (C) 2020 The zfoo Authors
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.zfoo.net.core.jprotobuf;

import com.zfoo.net.core.AbstractServer;
import com.zfoo.net.handler.GatewayRouteHandler;
import com.zfoo.net.handler.codec.jprotobuf.JProtobufTcpCodecHandler;
import com.zfoo.net.handler.idle.ServerIdleHandler;
import com.zfoo.net.session.model.Session;
import com.zfoo.protocol.IPacket;
import com.zfoo.util.net.HostAndPort;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.lang.Nullable;

import java.util.function.BiFunction;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class JProtobufGatewayServer extends AbstractServer {


    private final BiFunction<Session, IPacket, Boolean> packetFilter;

    public JProtobufGatewayServer(HostAndPort host, @Nullable BiFunction<Session, IPacket, Boolean> packetFilter) {
        super(host);
        this.packetFilter = packetFilter;
    }

    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new ChannelHandlerInitializer(packetFilter);
    }


    private static class ChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {

        private final BiFunction<Session, IPacket, Boolean> packetFilter;

        public ChannelHandlerInitializer(BiFunction<Session, IPacket, Boolean> packetFilter) {
            this.packetFilter = packetFilter;
        }

        @Override
        protected void initChannel(SocketChannel channel) {
            channel.pipeline().addLast(new IdleStateHandler(0, 0, 180));
            channel.pipeline().addLast(new ServerIdleHandler());
            channel.pipeline().addLast(new JProtobufTcpCodecHandler());
            channel.pipeline().addLast(new GatewayRouteHandler(packetFilter));
        }
    }

}
