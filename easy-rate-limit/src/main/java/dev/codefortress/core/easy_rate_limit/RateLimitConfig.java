package dev.codefortress.core.easy_rate_limit;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import jakarta.annotation.PostConstruct;

@AutoLoadable
public class RateLimitConfig {

    @EasyConfigProperty(description = "Cantidad máxima de solicitudes permitidas por IP dentro del intervalo")
    private int maxRequests = 100;

    @EasyConfigProperty(description = "Intervalo de tiempo en milisegundos para reiniciar el conteo")
    private long intervalMs = 60000;

    private final EasyConfigStore store;

    public RateLimitConfig(EasyConfigStore store) {
        this.store = store;
    }

    @PostConstruct
    public void loadFromStoreIfPresent() {
        String maxReq = store.get("rate.limit.max-requests");
        String interval = store.get("rate.limit.interval-ms");

        if (maxReq != null) {
            try {
                maxRequests = Integer.parseInt(maxReq);
            } catch (NumberFormatException ignored) {}
        }

        if (interval != null) {
            try {
                intervalMs = Long.parseLong(interval);
            } catch (NumberFormatException ignored) {}
        }
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getIntervalMs() {
        return intervalMs;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }
}
