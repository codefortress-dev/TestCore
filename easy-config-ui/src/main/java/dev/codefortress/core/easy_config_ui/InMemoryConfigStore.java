package dev.codefortress.core.easy_config_ui;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryConfigStore implements EasyConfigStore {

    private final Map<String, String> configValues = new ConcurrentHashMap<>();

    @Override
    public String get(String key) {
        return configValues.get(key);
    }

    @Override
    public void set(String key, String value) {
        configValues.put(key, value);
    }

    @Override
    public Map<String, String> getAll() {
        return Map.copyOf(configValues);
    }
}
