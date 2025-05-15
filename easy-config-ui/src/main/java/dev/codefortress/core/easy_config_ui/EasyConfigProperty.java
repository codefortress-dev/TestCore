package dev.codefortress.core.easy_config_ui;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EasyConfigProperty {

    /**
     * Ruta de la propiedad (ej: "security.ddos.threshold").
     */
    String value();

    /**
     * Descripción opcional para mostrar en la UI.
     */
    String description() default "";

    /**
     * Indica si la propiedad puede ser modificada en caliente (true por defecto).
     */
    boolean editable() default true;
}
