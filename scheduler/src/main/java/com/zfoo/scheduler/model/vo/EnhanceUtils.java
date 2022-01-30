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

package com.zfoo.scheduler.model.vo;

import com.zfoo.protocol.util.StringUtils;
import com.zfoo.scheduler.schema.NamespaceHandler;
import com.zfoo.util.security.IdUtils;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public abstract class EnhanceUtils {

    static {
        // 适配Tomcat，因为Tomcat不是用的默认的类加载器，而Javaassist用的是默认的加载器
        var classArray = new Class<?>[]{
                IScheduler.class
        };

        var classPool = ClassPool.getDefault();

        for (var clazz : classArray) {
            if (classPool.find(clazz.getCanonicalName()) == null) {
                ClassClassPath classPath = new ClassClassPath(clazz);
                classPool.insertClassPath(classPath);
            }
        }
    }

    public static IScheduler createScheduler(ReflectScheduler reflectScheduler) throws NotFoundException, CannotCompileException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var classPool = ClassPool.getDefault();

        Object bean = reflectScheduler.getBean();
        Method method = reflectScheduler.getMethod();

        // 定义类名称
        CtClass enhanceClazz = classPool.makeClass(EnhanceUtils.class.getCanonicalName() + StringUtils.capitalize(NamespaceHandler.SCHEDULER) + IdUtils.getLocalIntId());
        enhanceClazz.addInterface(classPool.get(IScheduler.class.getCanonicalName()));

        // 定义类中的一个成员
        CtField field = new CtField(classPool.get(bean.getClass().getCanonicalName()), "bean", enhanceClazz);
        field.setModifiers(Modifier.PRIVATE);
        enhanceClazz.addField(field);

        // 定义类的构造器
        CtConstructor constructor = new CtConstructor(classPool.get(new String[]{bean.getClass().getCanonicalName()}), enhanceClazz);
        constructor.setBody("{this.bean=$1;}");
        constructor.setModifiers(Modifier.PUBLIC);
        enhanceClazz.addConstructor(constructor);

        // 定义类实现的接口方法
        CtMethod invokeMethod = new CtMethod(classPool.get(void.class.getCanonicalName()), "invoke", null, enhanceClazz);
        invokeMethod.setModifiers(Modifier.PUBLIC + Modifier.FINAL);
        String invokeMethodBody = "{this.bean." + method.getName() + "();}";
        invokeMethod.setBody(invokeMethodBody);
        enhanceClazz.addMethod(invokeMethod);

        // 释放缓存
        enhanceClazz.detach();

        Class<?> resultClazz = enhanceClazz.toClass(IScheduler.class);
        Constructor<?> resultConstructor = resultClazz.getConstructor(bean.getClass());
        return (IScheduler) resultConstructor.newInstance(bean);
    }
}
