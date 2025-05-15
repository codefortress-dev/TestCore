package dev.codefortress.core.easy_geo_block;

import dev.codefortress.configui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
    public GeoBlockInterceptor geoBlockInterceptor(GeoBlockService geoBlockService) {
        return new GeoBlockInterceptor(geoBlockService);
    }

    @Bean
    public GeoLocationProvider geoLocationProvider() {
        return new IpApiGeoLocationProvider(); // implementación default
    }

    @Bean
    public GeoBlockService geoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        return new GeoBlockService(provider, props);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(geoBlockInterceptor(geoBlockService(geoLocationProvider(), geoBlockProperties())))
                .order(0);
    }

    @Bean
    public GeoBlockProperties geoBlockProperties() {
        GeoBlockProperties props = new GeoBlockProperties();
        EasyConfigScanner.preload(GeoBlockProperties.class); // habilita configuración en caliente
        return props;
    }
}
