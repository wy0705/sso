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
 */

package com.zfoo.net.router;

import com.zfoo.protocol.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 同步或异步的调用控制器
 *
 * @author jaysunxiao
 * @version 3.0
 */
public class PacketSignalArray {

    private static final Logger logger = LoggerFactory.getLogger(PacketSignalArray.class);

    // equal with 16383
    private static final int SIGNAL_MASK = 0B00000000_00000000_01111111_11111111;

    private static final AtomicReferenceArray<Integer> signalPacketArray = new AtomicReferenceArray<>(SIGNAL_MASK + 1);

    /**
     * Session控制同步或异步的附加包，key：signalId
     */
    private static final Map<Integer, Integer> signalAttachmentMap = new ConcurrentHashMap<>(1000);

    public static void addSignalAttachment(int signalId) {
        var hash = signalId & SIGNAL_MASK;

        if (signalPacketArray.compareAndSet(hash, null, signalId)) {
            return;
        }
        signalAttachmentMap.put(signalId, signalId);
    }


    public static void removeSignalAttachment(int signalId) {
        var hash = signalId & SIGNAL_MASK;
        var oldSignalId = signalPacketArray.get(hash);

        if (oldSignalId != null && oldSignalId == signalId && signalPacketArray.compareAndSet(hash, oldSignalId, null)) {
            return;
        }
        signalAttachmentMap.remove(signalId);
    }

    public static void status() {
        var count = 0;
        for (int i = 0; i < SIGNAL_MASK + 1; i++) {
            var value = signalPacketArray.get(i);
            if (value != null) {
                logger.info("signalPacketArray has attachment [index:{}][count:{}][value:{}]", i, ++count, JsonUtils.object2String(value));
            }
        }

        signalAttachmentMap.forEach((key, value) -> {
            logger.info("signalAttachmentMap has attachment [key:{}][value:{}]", key, JsonUtils.object2String(value));
        });
    }

}
