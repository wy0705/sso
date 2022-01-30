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

package com.zfoo.event.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

    public static final String EVENT = "event";

    @Override
    public void init() {
        registerBeanDefinitionParser(EVENT, new EventDefinitionParser());
    }
}
