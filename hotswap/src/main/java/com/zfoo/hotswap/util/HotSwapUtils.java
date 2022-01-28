package com.zfoo.hotswap.util;

import com.zfoo.protocol.collection.ArrayUtils;
import com.zfoo.protocol.util.IOUtils;
import com.zfoo.protocol.util.StringUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.ClassFile;
import javassist.util.HotSwapAgent;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public abstract class HotSwapUtils {

    private static final Logger logger = LoggerFactory.getLogger(HotSwapUtils.class);

    /**
     * 热更新java文件
     * JVM的启动参数，jdk11过后默认JVM不允许连接自己，需要加上JVM启动参数: -Djdk.attach.allowAttachSelf=true
     * <p>
     * 优先使用简单的Javassist做热更新，因为Byte Buddy使用了更为复杂的ASM，spring boot web项目中会优先使用Byte Buddy热更新
     *
     * @param bytes .class结尾的字节码文件
     */
    public static synchronized void hotswapClass(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return;
        }

        var clazzName = readClassName(bytes);

        Class<?> clazz = null;
        try {
            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            logger.info("无法在当前项目找到[class:{}]，所以忽略本次热更新", clazzName);
            return;
        }

        hotswapClassByJavassist(clazz, bytes);
    }

    private static String readClassName(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        DataInputStream dataInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            dataInputStream = new DataInputStream(byteArrayInputStream);
            var classFile = new ClassFile(dataInputStream);
            return classFile.getName().replaceAll(StringUtils.SLASH, StringUtils.PERIOD);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeIO(dataInputStream, byteArrayInputStream);
        }
    }

    private static void hotswapClassByJavassist(Class<?> clazz, byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        CtClass ctClass = null;
        try {
            clazz = Class.forName(readClassName(bytes));
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ctClass = ClassPool.getDefault().makeClass(byteArrayInputStream);
            // Javassist热更新
            HotSwapAgent.redefine(clazz, ctClass);
            logger.info("Javassist热更新[{}]成功", clazz);
        } catch (Throwable t) {
            logger.info("无法使用Javassist热更新，开始使用替补方案Byte Buddy做热更新", t);
            hotswapClassByByteBuddy(clazz, bytes);
        } finally {
            IOUtils.closeIO(byteArrayInputStream);
            if (ctClass != null) {
                ctClass.defrost();
            }
        }
    }

    private static void hotswapClassByByteBuddy(Class<?> clazz, byte[] bytes) {
        try {
            // Byte Buddy热更新
            var instrumentation = ByteBuddyAgent.install();
            instrumentation.redefineClasses(new ClassDefinition(clazz, bytes));
            logger.info("Byte Buddy热更新[{}]成功", clazz);
        } catch (Throwable t) {
            logger.error("Byte Buddy热更新未知异常，热更新[{}]失败", clazz);
        }
    }

}
