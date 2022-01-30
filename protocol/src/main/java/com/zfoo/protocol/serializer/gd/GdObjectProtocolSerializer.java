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

package com.zfoo.protocol.serializer.gd;

import com.zfoo.protocol.generate.GenerateProtocolFile;
import com.zfoo.protocol.registration.field.IFieldRegistration;
import com.zfoo.protocol.registration.field.ObjectProtocolField;
import com.zfoo.protocol.util.StringUtils;

import java.lang.reflect.Field;

import static com.zfoo.protocol.util.FileUtils.LS;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class GdObjectProtocolSerializer implements IGdSerializer {

    @Override
    public void writeObject(StringBuilder builder, String objectStr, int deep, Field field, IFieldRegistration fieldRegistration) {
        ObjectProtocolField objectProtocolField = (ObjectProtocolField) fieldRegistration;
        GenerateProtocolFile.addTab(builder, deep);
        builder.append(StringUtils.format("buffer.writePacket({}, {})", objectStr, objectProtocolField.getProtocolId())).append(LS);
    }

    @Override
    public String readObject(StringBuilder builder, int deep, Field field, IFieldRegistration fieldRegistration) {
        ObjectProtocolField objectProtocolField = (ObjectProtocolField) fieldRegistration;
        var result = "result" + GenerateProtocolFile.index.getAndIncrement();
        GenerateProtocolFile.addTab(builder, deep);
        builder.append(StringUtils.format("var {} = buffer.readPacket({})", result, objectProtocolField.getProtocolId())).append(LS);
        return result;
    }
}
