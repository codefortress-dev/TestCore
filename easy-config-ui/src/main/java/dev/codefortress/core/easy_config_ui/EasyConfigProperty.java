package dev.codefortress.core.easy_config_ui;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EasyConfigProperty {
    String description() default "";
}
