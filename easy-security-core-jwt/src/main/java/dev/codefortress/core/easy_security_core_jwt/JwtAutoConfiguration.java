package dev.codefortress.core.easy_security_core_jwt;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    @Bean
    public JwtProperties jwtProperties() {
        EasyConfigScanner.preload(JwtProperties.class);
        return new JwtProperties();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(JwtProperties props) {
        return new JwtTokenUtil(props.getSecretKey(), props.getExpirationSeconds());
    }
}
