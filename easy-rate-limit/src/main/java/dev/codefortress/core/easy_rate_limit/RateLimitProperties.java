package dev.codefortress.core.easy_rate_limit;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "easy.rate-limit")
@AutoLoadable
public class RateLimitProperties {

    @EasyConfigProperty(description = "Habilita o deshabilita el rate limit")
    private boolean enabled = true;

    @EasyConfigProperty(description = "Cantidad máxima de peticiones por IP")
    private int maxRequests = 100;

    @EasyConfigProperty(description = "Ventana de tiempo en segundos")
    private long timeWindowSeconds = 60;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getMaxRequests() { return maxRequests; }
    public void setMaxRequests(int maxRequests) { this.maxRequests = maxRequests; }

    public long getTimeWindowSeconds() { return timeWindowSeconds; }
    public void setTimeWindowSeconds(long timeWindowSeconds) { this.timeWindowSeconds = timeWindowSeconds; }
}
