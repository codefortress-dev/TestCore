package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JpaConfigStore implements EasyConfigStore {

    private final ConfigRepository configRepository;
    private final Map<String, String> cache = new HashMap<>();

    public JpaConfigStore(ConfigRepository configRepository) {
        this.configRepository = configRepository;
        preloadCache();
    }

    private void preloadCache() {
        configRepository.findAll().forEach(entity ->
            cache.put(entity.getKey(), entity.getValue())
        );
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    @Transactional
    public void set(String key, String value) {
        cache.put(key, value);

        ConfigEntity entity = configRepository.findById(key).orElse(new ConfigEntity());
        entity.setKey(key);
        entity.setValue(value);
        entity.setSource("DB");

        configRepository.save(entity);
    }

    @Override
    public boolean contains(String key) {
        return cache.containsKey(key);
    }
}
