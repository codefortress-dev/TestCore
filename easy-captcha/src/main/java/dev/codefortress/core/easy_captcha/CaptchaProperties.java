package dev.codefortress.core.easy_captcha;

import dev.codefortress.configui.EasyConfigProperty;
import dev.codefortress.configui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "easy.captcha")
@AutoLoadable
public class CaptchaProperties {

    @EasyConfigProperty(description = "Habilita o deshabilita la protección captcha.")
    private boolean enabled = false;

    @EasyConfigProperty(description = "Tiempo mínimo esperado para enviar un formulario (ms). Protege contra bots veloces.")
    private long minResponseTimeMillis = 1000;

    @EasyConfigProperty(description = "Activa validación 'honeypot' basada en campos ocultos que los bots suelen completar.")
    private boolean honeypotEnabled = true;

    @EasyConfigProperty(description = "Nombre del campo honeypot esperado en los formularios.")
    private String honeypotField = "email_confirm";

    @EasyConfigProperty(description = "Mensaje mostrado cuando la validación captcha falla.")
    private String blockMessage = "Captcha no superado. Parece un comportamiento automático.";

    // Getters y Setters

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getMinResponseTimeMillis() {
        return minResponseTimeMillis;
    }

    public void setMinResponseTimeMillis(long minResponseTimeMillis) {
        this.minResponseTimeMillis = minResponseTimeMillis;
    }

    public boolean isHoneypotEnabled() {
        return honeypotEnabled;
    }

    public void setHoneypotEnabled(boolean honeypotEnabled) {
        this.honeypotEnabled = honeypotEnabled;
    }

    public String getHoneypotField() {
        return honeypotField;
    }

    public void setHoneypotField(String honeypotField) {
        this.honeypotField = honeypotField;
    }

    public String getBlockMessage() {
        return blockMessage;
    }

    public void setBlockMessage(String blockMessage) {
        this.blockMessage = blockMessage;
    }
}
