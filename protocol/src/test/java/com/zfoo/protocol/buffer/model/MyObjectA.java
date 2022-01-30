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

package com.zfoo.protocol.buffer.model;

import com.zfoo.protocol.IPacket;

import java.util.Map;
import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class MyObjectA implements IPacket {

    public static final transient short PROTOCOL_ID = 2;

    public int a;

    public Map<Integer, String> b;

    public MyObjectB c;

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyObjectA myObjectA = (MyObjectA) o;
        return a == myObjectA.a && Objects.equals(b, myObjectA.b) && Objects.equals(c, myObjectA.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
