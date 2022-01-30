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

package com.zfoo.net.consumer;

import com.zfoo.net.router.answer.AsyncAnswer;
import com.zfoo.net.router.answer.SyncAnswer;
import com.zfoo.protocol.IPacket;
import org.springframework.lang.Nullable;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public interface IConsumer {

    /**
     * 直接发送，不需要任何返回值
     *
     * @param packet   需要发送的包
     * @param argument 计算负载均衡的参数，比如用户的id
     */
    void send(IPacket packet, @Nullable Object argument);

    <T extends IPacket> SyncAnswer<T> syncAsk(IPacket packet, Class<T> answerClass, @Nullable Object argument) throws Exception;

    <T extends IPacket> AsyncAnswer<T> asyncAsk(IPacket packet, Class<T> answerClass, @Nullable Object argument);

}
