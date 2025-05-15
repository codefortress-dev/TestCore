package dev.codefortress.core.easy_geo_block;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GeoBlockService {

    private final GeoLocationProvider locationProvider;
    private final GeoBlockProperties properties;

    public GeoBlockService(GeoLocationProvider locationProvider, GeoBlockProperties properties) {
        this.locationProvider = locationProvider;
        this.properties = properties;
    }

    /**
     * Indica si la IP está bloqueada por no estar en un país permitido.
     *
     * @param ip Dirección IP pública del cliente.
     * @return true si la IP debe ser bloqueada, false si está permitida.
     */
    public boolean isBlocked(String ip) {
        if (!properties.isEnabled()) {
            return false; // El bloqueo geográfico está deshabilitado
        }

        String countryCode = locationProvider.resolveCountryCode(ip);
        if (countryCode == null) {
            return true; // No se pudo resolver el país, por seguridad se bloquea
        }

        // Normaliza a mayúsculas
        countryCode = countryCode.trim().toUpperCase(Locale.ROOT);

        return !properties.getAllowedCountries().contains(countryCode);
    }

    /**
     * Devuelve el país correspondiente a una IP (puede ser null).
     *
     * @param ip Dirección IP pública
     * @return Código de país o null si no se pudo determinar
     */
    public String getCountry(String ip) {
        return locationProvider.resolveCountryCode(ip);
    }

    /**
     * Devuelve el mensaje de bloqueo personalizado.
     */
    public String getBlockMessage() {
        return properties.getBlockMessage();
    }
}
