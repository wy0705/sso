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

package com.zfoo.scheduler;

import com.zfoo.util.ThreadUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * cron（译为克龙）代表100万年，是英文单词中最大的时间单位。
 * google（译为古戈尔）代表10的100次方，足够穷尽宇宙万物
 *
 * @author jaysunxiao
 * @version 3.0
 */

@Ignore
public class ApplicationTest {

    @Test
    public void startSchedulerTest() {
        // 加载配置文件，配置文件中必须引入scheduler
        var context = new ClassPathXmlApplicationContext("application.xml");

        ThreadUtils.sleep(Long.MAX_VALUE);
    }

}
