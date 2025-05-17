package dev.codefortress.core.easy_geo_block;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_licensing.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(GeoBlockProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.geo-block", name = "enabled", havingValue = "true")
public class GeoBlockAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public GeoBlockProperties geoBlockProperties() {
        EasyConfigScanner.preload(GeoBlockProperties.class);
        return new GeoBlockProperties();
    }

    @Bean
    public GeoLocationProvider geoLocationProvider() {
        return new IpApiGeoLocationProvider();
    }

    @Bean
    public GeoBlockService geoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        return new GeoBlockService(provider, props);
    }

    @Bean
    public GeoBlockInterceptor geoBlockInterceptor(
        GeoBlockService service,
        SecuritySuiteLicenseProperties props,
        LicenseValidator validator
    ) {
        LicenseCheckResult result = validator.validate(props);
        if (result != LicenseCheckResult.VALID && result != LicenseCheckResult.TRIAL) {
            throw new LicenseException("Geo-block está disponible solo con licencia activa.");
        }
        return new GeoBlockInterceptor(service);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(geoBlockInterceptor(
                geoBlockService(geoLocationProvider(), geoBlockProperties()),
                new SecuritySuiteLicenseProperties(),
                new LicenseValidator(
                    new LicenseEnvironmentResolver(),
                    new LicenseRemoteValidator(),
                    new StoredLicenseCache(),
                    new LicenseSignatureVerifier()))
        ).order(1);
    }
}
