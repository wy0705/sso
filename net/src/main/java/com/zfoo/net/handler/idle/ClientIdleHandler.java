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

package com.zfoo.net.handler.idle;

import com.zfoo.net.packet.common.Heartbeat;
import com.zfoo.net.packet.model.EncodedPacketInfo;
import com.zfoo.net.util.SessionUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jaysunxiao
 * @version 3.0
 */
@ChannelHandler.Sharable
public class ClientIdleHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientIdleHandler.class);

    private static final EncodedPacketInfo heartbeatPacket = EncodedPacketInfo.valueOf(new Heartbeat(), null);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                logger.warn("client sends heartbeat packet to {}", SessionUtils.sessionInfo(ctx));
                ctx.channel().writeAndFlush(heartbeatPacket);
            }
        }

    }
}
