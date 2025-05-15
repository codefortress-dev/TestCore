package dev.codefortress.core.easy_rate_limit;

import dev.codefortress.configui.EasyConfigStore;
import dev.codefortress.configui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
public class RateLimitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RateLimitConfig rateLimitConfig(EasyConfigStore configStore) {
        // Carga los valores definidos vía anotaciones
        RateLimitConfig config = new RateLimitConfig();
        EasyConfigScanner.preload(configStore, "dev.codefortress.core.easy_rate_limit");
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimitStorage rateLimitStorage() {
        return new InMemoryRateLimitStorage();
    }

    @Bean
    public RateLimitInterceptor rateLimitInterceptor(RateLimitStorage storage, RateLimitConfig config) {
        return new RateLimitInterceptor(storage, config);
    }

    @Bean
    public WebMvcConfigurer rateLimitConfigurer(RateLimitInterceptor interceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(interceptor).addPathPatterns("/**");
            }
        };
    }
}
