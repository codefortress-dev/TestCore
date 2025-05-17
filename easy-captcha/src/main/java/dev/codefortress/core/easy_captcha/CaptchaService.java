package dev.codefortress.core.easy_captcha;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

    private final CaptchaProperties properties;

    public CaptchaService(CaptchaProperties properties) {
        this.properties = properties;
    }

    public boolean isSuspicious(HttpServletRequest request) {
        if (!properties.isEnabled()) return false;

        String startTimeStr = request.getParameter("_formStartTime");
        if (startTimeStr != null) {
            try {
                long startTime = Long.parseLong(startTimeStr);
                long duration = System.currentTimeMillis() - startTime;
                if (duration < properties.getMinResponseTimeMillis()) return true;
            } catch (NumberFormatException e) {
                return true;
            }
        } else {
            return true;
        }

        if (properties.isHoneypotEnabled()) {
            String honeypot = request.getParameter(properties.getHoneypotField());
            if (honeypot != null && !honeypot.isBlank()) return true;
        }

        return false;
    }

    public String getBlockMessage() {
        return properties.getBlockMessage();
    }
}
