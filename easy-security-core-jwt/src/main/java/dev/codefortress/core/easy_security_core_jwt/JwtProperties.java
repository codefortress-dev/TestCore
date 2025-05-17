package dev.codefortress.core.easy_security_core_jwt;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "easy.jwt")
@AutoLoadable
public class JwtProperties {

    @EasyConfigProperty(description = "Clave secreta para firmar y verificar JWTs (HS256)")
    private String secretKey = "default-secret";

    @EasyConfigProperty(description = "Tiempo de expiración del token en segundos")
    private long expirationSeconds = 3600;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }
}
