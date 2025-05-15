package dev.codefortress.core.easy_licensing;
import java.time.LocalDate;

public class LicenseValidator {

    private final LicenseProperties properties;
    private final LicenseEnvironmentResolver environmentResolver;
    private final LicenseRemoteValidator remoteValidator;
    private final StoredLicenseCache licenseCache;
    private final LicenseSignatureVerifier signatureVerifier;

    public LicenseValidator(
            LicenseProperties properties,
            LicenseEnvironmentResolver environmentResolver,
            LicenseRemoteValidator remoteValidator,
            StoredLicenseCache licenseCache,
            LicenseSignatureVerifier signatureVerifier) {
        this.properties = properties;
        this.environmentResolver = environmentResolver;
        this.remoteValidator = remoteValidator;
        this.licenseCache = licenseCache;
        this.signatureVerifier = signatureVerifier;
    }

    public LicenseInfo validate() {
        // Detecta si es un entorno de desarrollo
        if (environmentResolver.isDevelopmentEnvironment()) {
            return new LicenseInfo(
                    properties.getProduct(),
                    "localhost",
                    LocalDate.now().plusYears(100),
                    LicenseCheckResult.DEVELOPMENT_MODE
            );
        }

        // Si no hay clave, se asume modo prueba
        if (properties.getKey() == null || properties.getKey().isBlank()) {
            return new LicenseInfo(
                    properties.getProduct(),
                    "unknown",
                    LocalDate.now().plusDays(15),
                    LicenseCheckResult.TRIAL
            );
        }

        // Intenta validación remota
        try {
            LicenseInfo licenseInfo = remoteValidator.validateOnline(properties);
            licenseCache.save(licenseInfo); // Guarda localmente si es válida
            return licenseInfo;
        } catch (Exception e) {
            // Si falla la conexión remota, intenta validación offline
            try {
                LicenseInfo cached = licenseCache.load(properties.getProduct());
                if (signatureVerifier.isValid(cached, properties)) {
                    return cached;
                }
            } catch (Exception ex) {
                // No hay caché o está corrupta
            }
            return new LicenseInfo(
                    properties.getProduct(),
                    "unknown",
                    LocalDate.now(),
                    LicenseCheckResult.INVALID
            );
        }
    }
}
