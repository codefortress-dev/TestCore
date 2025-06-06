package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnClass(EasyConfigStore.class)
public class JpaConfigStoreInitializer {

    private final JpaConfigStore jpaStore;
    private final EasyConfigStore activeStore;
    private final ConfigRepository repository;

    public JpaConfigStoreInitializer(JpaConfigStore jpaStore,
                                     EasyConfigStore activeStore,
                                     ConfigRepository repository) {
        this.jpaStore = jpaStore;
        this.activeStore = activeStore;
        this.repository = repository;
    }

    @PostConstruct
    public void applyDatabaseConfigurations() {
        List<ConfigEntity> configs = repository.findAll();

        for (ConfigEntity entity : configs) {
            String key = entity.getKey();
            String value = entity.getValue();
            activeStore.set(key, value);
        }

        System.out.println("[easy-config-persistence] Configuración cargada desde base de datos.");
    }
}
