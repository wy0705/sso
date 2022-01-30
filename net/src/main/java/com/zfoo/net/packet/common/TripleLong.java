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

package com.zfoo.net.packet.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zfoo.protocol.IPacket;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class TripleLong implements IPacket {

    public static final transient short PROTOCOL_ID = 114;

    @JsonSerialize(using = ToStringSerializer.class)
    private long left;
    @JsonSerialize(using = ToStringSerializer.class)
    private long middle;
    @JsonSerialize(using = ToStringSerializer.class)
    private long right;

    public static TripleLong valueOf(long left, long middle, long right) {
        var triple = new TripleLong();
        triple.left = left;
        triple.middle = middle;
        triple.right = right;
        return triple;
    }

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }

    public long getLeft() {
        return left;
    }

    public void setLeft(long left) {
        this.left = left;
    }

    public long getMiddle() {
        return middle;
    }

    public void setMiddle(long middle) {
        this.middle = middle;
    }

    public long getRight() {
        return right;
    }

    public void setRight(long right) {
        this.right = right;
    }
}
