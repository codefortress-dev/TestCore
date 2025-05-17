package dev.codefortress.core.easy_geo_block;

import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import dev.codefortress.core.easy_config_ui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "easy.geo-block")
@AutoLoadable
public class GeoBlockProperties {

    @EasyConfigProperty(description = "Habilita o deshabilita el bloqueo geográfico")
    private boolean enabled = true;

    @EasyConfigProperty(description = "Lista de países permitidos (ISO alpha-2)")
    private List<String> allowedCountries = List.of("CL", "AR", "PE");

    @EasyConfigProperty(description = "Mensaje personalizado cuando se bloquea por geolocalización")
    private String blockMessage = "Acceso denegado desde esta ubicación.";

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public List<String> getAllowedCountries() { return allowedCountries; }
    public void setAllowedCountries(List<String> allowedCountries) { this.allowedCountries = allowedCountries; }

    public String getBlockMessage() { return blockMessage; }
    public void setBlockMessage(String blockMessage) { this.blockMessage = blockMessage; }
}
