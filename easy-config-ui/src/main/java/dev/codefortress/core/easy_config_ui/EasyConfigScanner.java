package dev.codefortress.core.easy_config_ui;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EasyConfigScanner {

    private static final Map<String, ConfigMetadata> properties = new ConcurrentHashMap<>();

    public static void preload(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(AutoLoadable.class)) return;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(EasyConfigProperty.class)) {
                field.setAccessible(true);
                EasyConfigProperty annotation = field.getAnnotation(EasyConfigProperty.class);
                properties.put(field.getName(), new ConfigMetadata(field.getName(), annotation.description(), clazz));
            }
        }
    }

    public static Collection<ConfigMetadata> getAll() {
        return properties.values();
    }

    public static ConfigMetadata get(String key) {
        return properties.get(key);
    }

    public record ConfigMetadata(String key, String description, Class<?> source) {}
}
