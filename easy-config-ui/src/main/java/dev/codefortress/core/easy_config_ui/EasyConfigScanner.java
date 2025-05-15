package dev.codefortress.core.easy_config_ui;

import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EasyConfigScanner {

    /**
     * Escanea clases en el classpath para encontrar campos anotados con @EasyConfigProperty.
     * Carga esos valores iniciales al EasyConfigStore.
     */
    public static void preload(EasyConfigStore store, String... basePackages) {
        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> types = reflections.getTypesAnnotatedWith(AutoLoadable.class, true);

            for (Class<?> clazz : types) {
                try {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(EasyConfigProperty.class)) {
                            field.setAccessible(true);
                            EasyConfigProperty annotation = field.getAnnotation(EasyConfigProperty.class);
                            String key = annotation.value();
                            String value = String.valueOf(field.get(instance));
                            store.set(key, value);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("No se pudo instanciar clase para escanear: " + clazz.getName());
                }
            }
        }
    }
}
