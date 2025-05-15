package dev.codefortress.core.easy_captcha;

import dev.codefortress.configui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
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
    public CaptchaService captchaService(CaptchaProperties props) {
        return new CaptchaService(props);
    }

    @Bean
    public CaptchaInterceptor captchaInterceptor(CaptchaService service, CaptchaProperties props) {
        // Para versión simple, protegemos todos los POST por defecto (se puede ajustar)
        List<String> protectedPaths = List.of("/api/", "/auth/", "/contact/");
        return new CaptchaInterceptor(service, protectedPaths);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(captchaInterceptor(captchaService(captchaProperties()), captchaProperties()))
                .order(0); // prioridad alta
    }

    @Bean
    public CaptchaProperties captchaProperties() {
        CaptchaProperties props = new CaptchaProperties();
        EasyConfigScanner.preload(CaptchaProperties.class); // para UI dinámica (modo Pro)
        return props;
    }
}
