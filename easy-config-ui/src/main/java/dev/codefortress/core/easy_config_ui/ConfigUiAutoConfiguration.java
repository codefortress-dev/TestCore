package dev.codefortress.core.easy_config_ui;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@AutoConfiguration
public class ConfigUiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(EasyConfigStore.class)
    public EasyConfigStore easyConfigStore() {
        return new InMemoryConfigStore();
    }

    @Bean
    @ConditionalOnMissingBean(EasyConfigController.class)
    public EasyConfigController easyConfigController(EasyConfigStore store) {
        return new EasyConfigController(store);
    }
}
