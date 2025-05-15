package dev.codefortress.core.easy_licensing;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "easy.license")
public class LicenseProperties {

    /**
     * Clave del producto (por ejemplo: "security-suite", "gateway-suite")
     */
    private String product;

    /**
     * Clave de activación del usuario (proporcionada al pagar)
     */
    private String key;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}