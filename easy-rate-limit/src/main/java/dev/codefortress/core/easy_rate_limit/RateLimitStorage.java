package dev.codefortress.core.easy_rate_limit;

/**
 * Interfaz para almacenar y verificar contadores de rate limiting.
 */
public interface RateLimitStorage {

    /**
     * Incrementa el contador para el identificador (ej: IP).
     * Si no existe, lo inicializa.
     *
     * @param key identificador (IP, token, etc.)
     * @return cantidad de solicitudes registradas en el intervalo actual
     */
    int increment(String key);

    /**
     * Reinicia manualmente el contador para un identificador.
     *
     * @param key identificador
     */
    void reset(String key);

    /**
     * Verifica si el identificador ha alcanzado el límite.
     *
     * @param key identificador (IP, token, etc.)
     * @param maxRequests máximo permitido
     * @return true si debe ser bloqueado
     */
    boolean isLimitExceeded(String key, int maxRequests);
}
