package dev.codefortress.core.easy_captcha;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

    private final CaptchaProperties properties;

    public CaptchaService(CaptchaProperties properties) {
        this.properties = properties;
    }

    /**
     * Verifica si la solicitud es sospechosa y debe ser bloqueada por comportamiento automático.
     *
     * @param request la solicitud HTTP entrante
     * @return true si parece un bot y debe ser bloqueado
     */
    public boolean isSuspicious(HttpServletRequest request) {
        if (!properties.isEnabled()) {
            return false;
        }

        // 1. Validación de tiempo mínimo de respuesta
        String startTimeStr = request.getParameter("_formStartTime");
        if (startTimeStr != null) {
            try {
                long startTime = Long.parseLong(startTimeStr);
                long duration = System.currentTimeMillis() - startTime;
                if (duration < properties.getMinResponseTimeMillis()) {
                    return true; // Demasiado rápido
                }
            } catch (NumberFormatException e) {
                return true; // El valor no es válido → sospechoso
            }
        } else {
            return true; // No se envió el tiempo → sospechoso
        }

        // 2. Validación honeypot (campo oculto que no debería completarse)
        if (properties.isHoneypotEnabled()) {
            String honeypot = request.getParameter(properties.getHoneypotField());
            if (honeypot != null && !honeypot.isBlank()) {
                return true; // El campo honeypot fue rellenado → bot
            }
        }

        return false; // Parece humano
    }

    public String getBlockMessage() {
        return properties.getBlockMessage();
    }
}
