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

package com.zfoo.storage.interpreter;

import com.zfoo.protocol.exception.RunException;
import com.zfoo.protocol.util.ReflectionUtils;
import com.zfoo.protocol.util.StringUtils;
import com.zfoo.storage.model.anno.Id;
import com.zfoo.storage.strategy.*;
import com.zfoo.storage.util.CellUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.TypeDescriptor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class ExcelResourceReader implements IResourceReader {

    private static final TypeDescriptor TYPE_DESCRIPTOR = TypeDescriptor.valueOf(String.class);

    private static final ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();

    static {
        var converters = new HashSet<>();
        converters.add(new JsonToArrayConverter());
        converters.add(new JsonToMapConverter());
        converters.add(new JsonToObjectConverter());
        converters.add(new StringToClassConverter());
        converters.add(new StringToDateConverter());
        converters.add(new StringToMapConverter());
        conversionServiceFactoryBean.setConverters(converters);
        conversionServiceFactoryBean.afterPropertiesSet();
    }

    @Override
    public <T> List<T> read(InputStream inputStream, Class<T> clazz) {
        var wb = createWorkbook(inputStream, clazz);
        var result = new ArrayList<T>();

        // ?????????????????????sheet???
        var sheet = wb.getSheetAt(0);
        var fieldInfos = getFieldInfos(sheet, clazz);

        var iterator = sheet.iterator();
        // ?????????????????????????????????????????????????????????????????????
        iterator.next();
        iterator.next();
        iterator.next();

        // ???ROW_SERVER????????????????????????
        while (iterator.hasNext()) {
            var row = iterator.next();
            var instance = ReflectionUtils.newInstance(clazz);

            var idCell = row.getCell(0);
            if (StringUtils.isBlank(CellUtils.getCellStringValue(idCell))) {
                continue;
            }

            for (var fieldInfo : fieldInfos) {
                var cell = row.getCell(fieldInfo.index);
                var content = CellUtils.getCellStringValue(cell);
                if (StringUtils.isNotEmpty(content) || fieldInfo.field.getType() == String.class) {
                    inject(instance, fieldInfo.field, content);
                }
            }
            result.add(instance);
        }
        return result;
    }

    private void inject(Object instance, Field field, String content) {
        try {
            var targetType = new TypeDescriptor(field);
            var value = conversionServiceFactoryBean.getObject().convert(content, TYPE_DESCRIPTOR, targetType);
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, instance, value);
        } catch (Exception e) {
            throw new RunException(e, "?????????Excel??????[class:{}]??????[content:{}]???????????????[field:{}]", instance.getClass().getSimpleName(), content, field.getName());
        }
    }


    // ??????????????????????????????
    private Collection<FieldInfo> getFieldInfos(Sheet sheet, Class<?> clazz) {
        var fieldRow = getFieldRow(sheet);
        if (fieldRow == null) {
            throw new RunException("??????????????????[class:{}]???Excel????????????????????????", clazz.getSimpleName());
        }

        var cellFieldMap = new HashMap<String, Integer>();
        for (var i = 0; i < fieldRow.getLastCellNum(); i++) {
            var cell = fieldRow.getCell(i);
            if (Objects.isNull(cell)) {
                continue;
            }

            var name = CellUtils.getCellStringValue(cell);
            if (StringUtils.isEmpty(name)) {
                continue;
            }
            var previousValue = cellFieldMap.put(name, i);
            if (Objects.nonNull(previousValue)) {
                throw new RunException("??????[class:{}]???Excel????????????????????????????????????[field:{}]", clazz.getSimpleName(), name);
            }
        }

        var fieldList = Arrays.stream(clazz.getDeclaredFields())
                .filter(it -> !Modifier.isTransient(it.getModifiers()))
                .filter(it -> !Modifier.isStatic(it.getModifiers()))
                .collect(Collectors.toList());

        for (var field : fieldList) {
            if (!cellFieldMap.containsKey(field.getName())) {
                throw new RunException("?????????[class:{}]???????????????[filed:{}]??????????????????????????????????????????", clazz, field.getName());
            }

            if (field.isAnnotationPresent(Id.class)) {
                var cellIndex = cellFieldMap.get(field.getName());
                if (cellIndex != 0) {
                    throw new RunException("?????????[class:{}]?????????[Id:{}]????????????Excel???????????????????????????????????????????????????", clazz, field.getName());
                }
            }

            if (Modifier.isPublic(field.getModifiers())) {
                throw new RunException("????????????????????????????????????????????????????????????[class:{}]?????????[filed:{}]?????????public??????????????????private??????", clazz, field.getName());
            }

            var setMethodName = StringUtils.EMPTY;
            try {
                setMethodName = ReflectionUtils.fieldToSetMethod(clazz, field);
            } catch (Exception e) {
                // ??????setMethod????????????
            }
            if (!StringUtils.isBlank(setMethodName)) {
                throw new RunException("????????????????????????????????????????????????????????????[class:{}]?????????[filed:{}]????????????set??????[{}]", clazz, field.getName(), setMethodName);
            }
        }

        return fieldList.stream().map(it -> new FieldInfo(cellFieldMap.get(it.getName()), it)).collect(Collectors.toList());

    }

    // ?????????????????????????????????????????????????????????????????????
    private Row getFieldRow(Sheet sheet) {
        var iterator = sheet.iterator();
        var row = iterator.next();
        return row;
    }


    private Workbook createWorkbook(InputStream input, Class<?> clazz) {
        try {
            return WorkbookFactory.create(input);
        } catch (IOException e) {
            throw new RunException("????????????[{}]???????????????????????????", clazz.getSimpleName());
        }
    }


    private static class FieldInfo {
        public final int index;
        public final Field field;

        public FieldInfo(int index, Field field) {
            this.index = index;
            this.field = field;
        }
    }
}
