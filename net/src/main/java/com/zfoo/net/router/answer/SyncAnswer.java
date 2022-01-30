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

package com.zfoo.net.router.answer;

import com.zfoo.net.router.attachment.SignalAttachment;
import com.zfoo.protocol.IPacket;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class SyncAnswer<T extends IPacket> implements ISyncAnswer<T> {


    private final T packet;
    private final SignalAttachment attachment;

    public SyncAnswer(T packet, SignalAttachment attachment) {
        this.packet = packet;
        this.attachment = attachment;
    }

    @Override
    public T packet() {
        return packet;
    }

    @Override
    public SignalAttachment attachment() {
        return attachment;
    }


}
