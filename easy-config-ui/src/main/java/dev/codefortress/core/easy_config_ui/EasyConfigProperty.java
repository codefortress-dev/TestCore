package dev.codefortress.core.easy_config_ui;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EasyConfigProperty {
    String description() default "";

    boolean required() default false;

    boolean nonBlank() default false; // solo para Strings

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;
}
