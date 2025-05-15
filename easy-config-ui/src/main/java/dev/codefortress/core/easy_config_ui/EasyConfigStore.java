package dev.codefortress.core.easy_config_ui;
import java.util.Map;

public interface EasyConfigStore {

    /**
     * Obtiene el valor actual de una propiedad configurada.
     *
     * @param key Clave (ej: "security.ddos.threshold")
     * @return Valor actual como String, o null si no existe
     */
    String get(String key);

    /**
     * Establece o actualiza el valor de una propiedad.
     *
     * @param key   Clave (ej: "security.ddos.threshold")
     * @param value Valor en formato String
     */
    void set(String key, String value);

    /**
     * Retorna todas las configuraciones activas.
     *
     * @return Mapa clave-valor
     */
    Map<String, String> getAll();
}

