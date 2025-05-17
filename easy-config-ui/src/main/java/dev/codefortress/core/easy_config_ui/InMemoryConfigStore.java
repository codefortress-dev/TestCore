package dev.codefortress.core.easy_config_ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryConfigStore implements EasyConfigStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public String get(String key) {
        return store.get(key);
    }

    @Override
    public void set(String key, String value) {
        store.put(key, value);
    }

    @Override
    public boolean contains(String key) {
        return store.containsKey(key);
    }
}
