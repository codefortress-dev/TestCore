package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({EasyConfigStore.class, JpaConfigStore.class})
public class ConfigPersistenceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EasyConfigStore easyConfigStore(ConfigRepository repository) {
        // Prioriza JpaConfigStore sobre InMemoryConfigStore
        return new JpaConfigStore(repository);
    }

    @Bean
    public JpaConfigStoreSaver jpaConfigStoreSaver(EasyConfigStore store, ConfigRepository repository) {
        return new JpaConfigStoreSaver(store, repository);
    }

    @Bean
    public JpaConfigStoreInitializer jpaConfigStoreInitializer(JpaConfigStore jpaStore,
                                                                EasyConfigStore activeStore,
                                                                ConfigRepository repository) {
        return new JpaConfigStoreInitializer(jpaStore, activeStore, repository);
    }
}
