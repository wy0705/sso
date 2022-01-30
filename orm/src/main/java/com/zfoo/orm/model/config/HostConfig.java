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

package com.zfoo.orm.model.config;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class HostConfig {

    private String database;
    private String user;
    private String password;
    private Map<String, String> address;

    public static HostConfig valueOf(String database, String user, String password, Map<String, String> addressMap) {
        HostConfig config = new HostConfig();
        config.database = database;
        config.user = user;
        config.password = password;
        config.address = addressMap;
        return config;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }
}
