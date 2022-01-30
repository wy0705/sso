/*
 * Copyright (C) 2020 The zfoo Authors
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.protocol.serializer;

import com.zfoo.protocol.generate.GenerateProtocolFile;
import com.zfoo.protocol.registration.EnhanceUtils;
import com.zfoo.protocol.registration.field.BaseField;
import com.zfoo.protocol.registration.field.IFieldRegistration;
import com.zfoo.protocol.registration.field.ListField;
import com.zfoo.protocol.registration.field.ObjectProtocolField;
import com.zfoo.protocol.serializer.enhance.EnhanceObjectProtocolSerializer;
import com.zfoo.protocol.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import static com.zfoo.protocol.util.FileUtils.LS;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class CutDownListSerializer implements ICutDownSerializer {

    private static final CutDownListSerializer INSTANCE = new CutDownListSerializer();

    public static CutDownListSerializer getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean writeObject(StringBuilder builder, String objectStr, Field field, IFieldRegistration fieldRegistration, CodeLanguage language) {
        var listField = (ListField) fieldRegistration;
        var flag = true;

        var listName = getListClassName(listField);
        switch (listName) {
            case "Boolean":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeBooleanList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeBooleanArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeBooleanArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeBooleanArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteBooleanList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Byte":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeByteList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeByteArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeByteArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeByteArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteByteList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Short":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeShortList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeShortArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeShortArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeShortArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteShortList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Integer":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeIntList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeIntArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeIntArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeIntArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteIntList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Long": {
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeLongList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeLongArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeLongArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeLongArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteLongList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            }
            case "Float": {
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeFloatList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeFloatArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeFloatArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeFloatArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteFloatList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            }
            case "Double": {
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeDoubleList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeDoubleArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeDoubleArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeDoubleArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteDoubleList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            }
            case "String":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("{}.writeStringList($1, (List){});", EnhanceUtils.byteBufUtils, objectStr));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("buffer.writeStringArray({});", objectStr)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("buffer.writeStringArray({})", objectStr)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("buffer:writeStringArray({})", objectStr)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("buffer.WriteStringList({});", objectStr)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            default:
                // List<IPacket>
                if (listField.getListElementRegistration() instanceof ObjectProtocolField) {
                    var objectProtocolField = (ObjectProtocolField) listField.getListElementRegistration();
                    switch (language) {
                        case Enhance:
                            builder.append(StringUtils.format("{}.writePacketList($1, (List){}, {});", EnhanceUtils.byteBufUtils, objectStr, EnhanceUtils.getProtocolRegistrationFieldNameByProtocolId(objectProtocolField.getProtocolId())));
                            break;
                        case JavaScript:
                            builder.append(StringUtils.format("buffer.writePacketArray({}, {});", objectStr, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        case GdScript:
                            builder.append(StringUtils.format("buffer.writePacketArray({}, {})", objectStr, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        case Lua:
                            builder.append(StringUtils.format("buffer:writePacketArray({}, {})", objectStr, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        case CSharp:
                            builder.append(StringUtils.format("buffer.WritePacketList({}, {});", objectStr, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        default:
                            flag = false;
                    }
                } else {
                    flag = false;
                }
        }

        return flag;
    }

    @Override
    public String readObject(StringBuilder builder, Field field, IFieldRegistration fieldRegistration, CodeLanguage language) {
        var listField = (ListField) fieldRegistration;
        var list = "list" + GenerateProtocolFile.index.getAndIncrement();
        var flag = true;

        var listName = getListClassName(listField);
        switch (listName) {
            case "Boolean":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readBooleanList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readBooleanArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readBooleanArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readBooleanArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadBooleanList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Byte":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readByteList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readByteArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readByteArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readByteArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadByteList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Short":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readShortList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readShortArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readShortArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readShortArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadShortList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Integer":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readIntList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readIntArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readIntArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readIntArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadIntList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Long":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readLongList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readLongArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readLongArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readLongArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadLongList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Float":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readFloatList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readFloatArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readFloatArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readFloatArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadFloatList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "Double":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readDoubleList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readDoubleArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readDoubleArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readDoubleArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadDoubleList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            case "String":
                switch (language) {
                    case Enhance:
                        builder.append(StringUtils.format("List {} = {}.readStringList($1);", list, EnhanceUtils.byteBufUtils));
                        break;
                    case JavaScript:
                        builder.append(StringUtils.format("const {} = buffer.readStringArray();", list)).append(LS);
                        break;
                    case GdScript:
                        builder.append(StringUtils.format("var {} = buffer.readStringArray()", list)).append(LS);
                        break;
                    case Lua:
                        builder.append(StringUtils.format("local {} = buffer:readStringArray()", list)).append(LS);
                        break;
                    case CSharp:
                        builder.append(StringUtils.format("var {} = buffer.ReadStringList();", list)).append(LS);
                        break;
                    default:
                        flag = false;
                }
                break;
            default:
                if (listField.getListElementRegistration() instanceof ObjectProtocolField) {
                    var objectProtocolField = (ObjectProtocolField) listField.getListElementRegistration();
                    switch (language) {
                        case Enhance:
                            builder.append(StringUtils.format("List {} = {}.readPacketList($1, {});", list, EnhanceUtils.byteBufUtils, EnhanceUtils.getProtocolRegistrationFieldNameByProtocolId(objectProtocolField.getProtocolId())));
                            break;
                        case JavaScript:
                            builder.append(StringUtils.format("const {} = buffer.readPacketArray({});", list, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        case GdScript:
                            builder.append(StringUtils.format("var {} = buffer.readPacketArray({})", list, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        case Lua:
                            builder.append(StringUtils.format("local {} = buffer:readPacketArray({})", list, objectProtocolField.getProtocolId())).append(LS);
                            break;
                        case CSharp:
                            builder.append(StringUtils.format("var {} = buffer.ReadPacketList<{}>({});", list, EnhanceObjectProtocolSerializer.getProtocolClassSimpleName(objectProtocolField.getProtocolId()), objectProtocolField.getProtocolId())).append(LS);
                            break;
                        default:
                            flag = false;
                    }
                } else {
                    flag = false;
                }
        }


        if (flag) {
            return list;
        } else {
            GenerateProtocolFile.index.getAndDecrement();
            return null;
        }
    }

    public String getListClassName(ListField listField) {
        if (listField.getListElementRegistration() instanceof BaseField) {
            return ((Class<?>) ((ParameterizedType) listField.getType()).getActualTypeArguments()[0]).getSimpleName();
        } else {
            return listField.getType().getTypeName();
        }
    }
}
