package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.configui.EasyConfigStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConditionalOnClass(EasyConfigStore.class)
public class JpaConfigStoreSaver implements EasyConfigStore {

    private final EasyConfigStore delegate;
    private final ConfigRepository repository;

    public JpaConfigStoreSaver(EasyConfigStore delegate, ConfigRepository repository) {
        this.delegate = delegate;
        this.repository = repository;
    }

    @Override
    public String get(String key) {
        return delegate.get(key);
    }

    @Override
    public void set(String key, String value) {
        String current = delegate.get(key);

        delegate.set(key, value); // Actualiza en memoria

        // Guarda en base de datos solo si cambió
        if (!Objects.equals(current, value)) {
            ConfigEntity entity = repository.findById(key).orElse(new ConfigEntity());
            entity.setKey(key);
            entity.setValue(value);
            entity.setSource("UI");
            repository.save(entity);
        }
    }

    @Override
    public boolean contains(String key) {
        return delegate.contains(key);
    }
}
