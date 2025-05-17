package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(SecuritySuiteLicenseProperties.class)
public class LicensingAutoConfiguration {

    @Bean
    public SecuritySuiteLicenseProperties validatedSecuritySuiteLicenseProperties(SecuritySuiteLicenseProperties props) {
        EasyConfigScanner.preload(SecuritySuiteLicenseProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    @Bean
    public LicenseValidator licenseValidator(
        LicenseEnvironmentResolver env,
        LicenseRemoteValidator remote,
        StoredLicenseCache cache,
        LicenseSignatureVerifier verifier
    ) {
        return new LicenseValidator(env, remote, cache, verifier);
    }

    @Bean
    public LicenseRemoteValidator licenseRemoteValidator() {
        return new LicenseRemoteValidator();
    }

    @Bean
    public LicenseEnvironmentResolver licenseEnvironmentResolver() {
        return new LicenseEnvironmentResolver();
    }

    @Bean
    public StoredLicenseCache storedLicenseCache() {
        return new StoredLicenseCache();
    }

    @Bean
    public LicenseSignatureVerifier licenseSignatureVerifier() {
        return new LicenseSignatureVerifier("LICENSE_SECRET"); // reemplaza con el valor real
    }
}
