package dev.codefortress.core.easy_captcha;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_licensing.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.captcha", name = "enabled", havingValue = "true")
public class CaptchaAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public CaptchaProperties captchaProperties() {
        EasyConfigScanner.preload(CaptchaProperties.class);
        return new CaptchaProperties();
    }

    @Bean
    public CaptchaService captchaService(CaptchaProperties props) {
        return new CaptchaService(props);
    }

    @Bean
    public CaptchaInterceptor captchaInterceptor(
        CaptchaService service,
        SecuritySuiteLicenseProperties props,
        LicenseValidator validator
    ) {
        LicenseCheckResult result = validator.validate(props);
        if (result != LicenseCheckResult.VALID && result != LicenseCheckResult.TRIAL) {
            throw new LicenseException("El módulo Captcha requiere licencia válida.");
        }

        List<String> protectedPaths = List.of("/auth", "/register", "/api/contact");
        return new CaptchaInterceptor(service, protectedPaths);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(captchaInterceptor(
                captchaService(captchaProperties()),
                new SecuritySuiteLicenseProperties(),
                new LicenseValidator(
                    new LicenseEnvironmentResolver(),
                    new LicenseRemoteValidator(),
                    new StoredLicenseCache(),
                    new LicenseSignatureVerifier()))
        ).order(2);
    }
}
