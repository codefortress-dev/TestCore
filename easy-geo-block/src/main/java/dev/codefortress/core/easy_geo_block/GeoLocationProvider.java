package dev.codefortress.core.easy_geo_block;

/**
 * Interfaz para resolver el código de país ISO 3166-1 alpha-2 a partir de una dirección IP.
 */
public interface GeoLocationProvider {

    /**
     * Devuelve el código de país (ej. "CL", "US") a partir de una IP.
     *
     * @param ip dirección IP pública o del cliente
     * @return código de país ISO-2, o null si no se puede resolver
     */
    String resolveCountryCode(String ip);
}
