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

package com.zfoo.net.consumer.event;

import com.zfoo.event.model.event.IEvent;
import com.zfoo.net.consumer.registry.RegisterVO;
import com.zfoo.net.session.model.Session;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class ConsumerStartEvent implements IEvent {

    private RegisterVO registerVO;
    private Session session;

    public static ConsumerStartEvent valueOf(RegisterVO registerVO, Session session) {
        var event = new ConsumerStartEvent();
        event.registerVO = registerVO;
        event.session = session;
        return event;
    }

    public RegisterVO getRegisterVO() {
        return registerVO;
    }

    public void setRegisterVO(RegisterVO registerVO) {
        this.registerVO = registerVO;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
