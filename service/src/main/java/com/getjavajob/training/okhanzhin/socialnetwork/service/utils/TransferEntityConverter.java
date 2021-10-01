package com.getjavajob.training.okhanzhin.socialnetwork.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransferEntityConverter<S, T> {
    private static final String BUILD_METHOD_NAME = "build";
    private static final Logger logger = LoggerFactory.getLogger(TransferEntityConverter.class);

    public TransferEntityConverter() {
    }

    public T buildEntity(S transfer, Class<T> targetEntity) {
        return processTransformation(transfer, targetEntity);
    }

    public T buildTransfer(S entity, Class<T> targetTransfer) {
        return processTransformation(entity, targetTransfer);
    }

    private T processTransformation(S source, Class<T> target) {
        try {
            Field[] declaredFields = source.getClass().getDeclaredFields();
            Field[] sourceFields = new Field[declaredFields.length - 1];
            System.arraycopy(declaredFields, 1, sourceFields, 0, declaredFields.length - 1);

            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
            }

            Class<?>[] declaredClasses = target.getDeclaredClasses();
            Class<?> builderClass = declaredClasses[0];

            Object builderInstance = builderClass.newInstance();
            Method build = builderInstance.getClass().getDeclaredMethod(BUILD_METHOD_NAME);

            for (int i = 0; i <= sourceFields.length - 1; i++) {
                String transferFieldName = sourceFields[i].getName();
                Class<?> sourceFieldType = sourceFields[i].getType();
                Method builderMethod = ReflectionUtils.findMethod(builderClass, transferFieldName, sourceFieldType);
                if (builderMethod != null) {
                    builderInstance = builderMethod.invoke(builderInstance, sourceFields[i].get(source));
                }
            }

            return (T) build.invoke(builderInstance, (Object[]) null);
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException e) {
            logger.error("Error during " + source.getClass().getSimpleName() + " to " + target.getSimpleName() +
                         " transformation:" + e.getMessage(), e);
        }

        return null;
    }
}
