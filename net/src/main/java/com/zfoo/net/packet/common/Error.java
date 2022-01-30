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

import com.zfoo.protocol.IPacket;
import com.zfoo.protocol.ProtocolManager;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class Error implements IPacket {

    public static final transient short PROTOCOL_ID = 101;

    private int module;

    private int errorCode;

    private String errorMessage;

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }

    public static short errorProtocolId() {
        return PROTOCOL_ID;
    }

    @Override
    public String toString() {
        FormattingTuple message = MessageFormatter.arrayFormat(
                "module:[{}], errorCode:[{}], errorMessage:[{}]", new Object[]{module, errorCode, errorMessage});
        return message.getMessage();
    }

    public static Error valueOf(int module, int errorCode, String errorMessage) {
        Error response = new Error();
        response.module = module;
        response.errorCode = errorCode;
        response.errorMessage = errorMessage;
        return response;
    }

    public static Error valueOf(IPacket packet, int errorCode, String errorMessage) {
        Error response = new Error();
        response.module = ProtocolManager.getProtocol(packet.protocolId()).module();
        response.errorCode = errorCode;
        response.errorMessage = errorMessage;
        return response;
    }

    public static Error valueOf(IPacket packet, int errorCode) {
        return valueOf(packet, errorCode, null);
    }

    public static Error valueOf(IPacket packet, String errorMessage) {
        return valueOf(packet, 0, errorMessage);
    }

    public static Error valueOf(String errorMessage) {
        return valueOf(0, 0, errorMessage);
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
