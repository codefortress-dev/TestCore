package dev.codefortress.core.easy_rate_limit;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación simple de almacenamiento de rate limit en memoria.
 */
public class InMemoryRateLimitStorage implements RateLimitStorage {

    private static class RequestData {
        int count;
        long startTime;
    }

    private final Map<String, RequestData> requestMap = new ConcurrentHashMap<>();

    /**
     * Intervalo por defecto (puede ser modificado desde configuración externa)
     */
    private long intervalMs = 60000;

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }

    @Override
    public int increment(String key) {
        long now = Instant.now().toEpochMilli();

        RequestData data = requestMap.computeIfAbsent(key, k -> {
            RequestData newData = new RequestData();
            newData.startTime = now;
            return newData;
        });

        if ((now - data.startTime) > intervalMs) {
            data.startTime = now;
            data.count = 1;
        } else {
            data.count++;
        }

        return data.count;
    }

    @Override
    public void reset(String key) {
        requestMap.remove(key);
    }

    @Override
    public boolean isLimitExceeded(String key, int maxRequests) {
        int count = increment(key);
        return count > maxRequests;
    }
}
