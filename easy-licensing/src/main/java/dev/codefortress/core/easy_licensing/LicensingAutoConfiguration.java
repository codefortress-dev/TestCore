package dev.codefortress.core.easy_licensing;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@AutoConfiguration
@EnableConfigurationProperties(LicenseProperties.class)
public class LicensingAutoConfiguration {

    
    @Bean
    public LicenseValidator licenseValidator(
        LicenseProperties props,
        LicenseEnvironmentResolver env,
        LicenseRemoteValidator remote,
        StoredLicenseCache cache,
        LicenseSignatureVerifier verifier) {
    return new LicenseValidator(props, env, remote, cache, verifier);
}

}