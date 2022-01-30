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

package com.zfoo.protocol.generate;

import com.zfoo.protocol.serializer.CodeLanguage;

import java.util.EnumSet;
import java.util.Set;

/**
 * 创建协议文件的操作类
 *
 * @author jaysunxiao
 * @version 3.0
 */
public class GenerateOperation {

    /**
     * 不创建任何协议文件
     */
    public static final GenerateOperation NO_OPERATION = new GenerateOperation();

    /**
     * 折叠协议，生成协议文件会和Java源文件保持相同的目录结构，默认不折叠
     */
    private boolean foldProtocol;

    /**
     * 生成协议的路径，默认为当前运行项目的路径
     */
    private String protocolPath;

    /**
     * 保留参数
     */
    private String protocolParam;

    /**
     * 需要生成的协议文件
     */
    private final Set<CodeLanguage> generateLanguages = EnumSet.noneOf(CodeLanguage.class);

    public String getProtocolPath() {
        return protocolPath;
    }

    public void setProtocolPath(String protocolPath) {
        this.protocolPath = protocolPath;
    }

    public boolean isFoldProtocol() {
        return foldProtocol;
    }

    public void setFoldProtocol(boolean foldProtocol) {
        this.foldProtocol = foldProtocol;
    }

    public String getProtocolParam() {
        return protocolParam;
    }

    public void setProtocolParam(String protocolParam) {
        this.protocolParam = protocolParam;
    }

    public Set<CodeLanguage> getGenerateLanguages() {
        return generateLanguages;
    }

}
