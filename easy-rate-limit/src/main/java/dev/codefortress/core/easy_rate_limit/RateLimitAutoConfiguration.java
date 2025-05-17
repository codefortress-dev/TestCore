package dev.codefortress.core.easy_rate_limit;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.rate-limit", name = "enabled", havingValue = "true", matchIfMissing = false)
public class RateLimitAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public RateLimitProperties rateLimitProperties() {
        EasyConfigScanner.preload(RateLimitProperties.class);
        return new RateLimitProperties();
    }

    @Bean
    public RateLimitInterceptor rateLimitInterceptor(RateLimitProperties props) {
        return new RateLimitInterceptor(props);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor(rateLimitProperties()))
                .order(0);
    }
}
