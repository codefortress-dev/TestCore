package dev.codefortress.core.easy_security_core_jwt;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenUtil jwtTokenUtil(JwtProperties properties) {
        return new JwtTokenUtil(properties.getSecretKey(), properties.getExpirationSeconds());
    }
}