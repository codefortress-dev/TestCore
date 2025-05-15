package dev.codefortress.core.easy_security_core_jwt;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AutoLoadable
@EasyConfigProperty(description = "Clave secreta HS256 para firmar JWTs")
@ConfigurationProperties(prefix = "easy.security.jwt")
public class JwtProperties {
    private String secretKey;
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

