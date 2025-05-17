package dev.codefortress.core.easy_security_core_jwt;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    /**
     * Este método valida y registra la clase JwtProperties para escaneo por la UI.
     * Se asegura que los valores externos del usuario estén correctos al iniciar.
     */
    @Bean
    public JwtProperties validatedJwtProperties(JwtProperties props) {
        EasyConfigScanner.preload(JwtProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    /**
     * Instancia JwtTokenUtil usando los valores ya validados por Spring y tu anotación.
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil(JwtProperties props) {
        return new JwtTokenUtil(props.getSecretKey(), props.getExpirationSeconds());
    }
}

