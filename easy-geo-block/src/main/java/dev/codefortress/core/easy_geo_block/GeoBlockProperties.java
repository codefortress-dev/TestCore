package dev.codefortress.geoblock;

import dev.codefortress.configui.EasyConfigProperty;
import dev.codefortress.configui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "easy.geo-block")
@AutoLoadable
public class GeoBlockProperties {

    @EasyConfigProperty(description = "Habilita o deshabilita el bloqueo geográfico.")
    private boolean enabled = false;

    @EasyConfigProperty(description = "Lista blanca de países permitidos (ISO ALPHA-2).")
    private List<String> allowedCountries = List.of();

    @EasyConfigProperty(description = "Mensaje mostrado cuando se bloquea un país.")
    private String blockMessage = "Acceso restringido desde tu país";

    // Getters y Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAllowedCountries() {
        return allowedCountries;
    }

    public void setAllowedCountries(List<String> allowedCountries) {
        this.allowedCountries = allowedCountries;
    }

    public String getBlockMessage() {
        return blockMessage;
    }

    public void setBlockMessage(String blockMessage) {
        this.blockMessage = blockMessage;
    }
}
