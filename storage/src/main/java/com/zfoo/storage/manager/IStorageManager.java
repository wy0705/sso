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

package com.zfoo.storage.manager;

import com.zfoo.storage.model.vo.Storage;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public interface IStorageManager {

    /**
     * 配置表初始化之前，先读取所有的excel
     */
    void initBefore();

    /**
     * 注入
     */
    void inject();

    /**
     * 程序加载过后，移除没有用到的配置表
     */
    void initAfter();

    @Nullable
    Storage<?, ?> getStorage(Class<?> clazz);

    Map<Class<?>, Boolean> allStorageUsableMap();

    Map<Class<?>, Storage<?, ?>> storageMap();

    void updateStorage(Class<?> clazz, Storage<?, ?> storage);

}
