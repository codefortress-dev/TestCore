package dev.codefortress.core.easy_licensing;

import java.time.Duration;
import java.time.Instant;

public class LicenseValidator {

    private static final int TRIAL_DAYS = 21;

    private final LicenseEnvironmentResolver environmentResolver;
    private final LicenseRemoteValidator remoteValidator;
    private final StoredLicenseCache cache;
    private final LicenseSignatureVerifier verifier;
    private final TrialMetadataStore trialMetadataStore = new TrialMetadataStore();

    public LicenseValidator(
        LicenseEnvironmentResolver environmentResolver,
        LicenseRemoteValidator remoteValidator,
        StoredLicenseCache cache,
        LicenseSignatureVerifier verifier
    ) {
        this.environmentResolver = environmentResolver;
        this.remoteValidator = remoteValidator;
        this.cache = cache;
        this.verifier = verifier;
    }

    public LicenseCheckResult validate(SecuritySuiteLicenseProperties props) {
        String product = "security-suite";
        String key = props.getKey();
        String domain = environmentResolver.resolveDomain();

        if (environmentResolver.isDevOrLocal()) {
            return LicenseCheckResult.DEVELOPMENT_MODE;
        }

        if (key != null && !key.isBlank()) {
            // Intentar validación remota
            LicenseInfo info = remoteValidator.validate(product, key, domain);
            if (info != null && info.getStatus() == LicenseCheckResult.VALID) {
                cache.store(info);
                return LicenseCheckResult.VALID;
            }

            // Fallback: validar desde cache local
            LicenseInfo cached = cache.get(product);
            if (cached != null && cached.getStatus() == LicenseCheckResult.VALID) {
                return LicenseCheckResult.VALID;
            }

            // Validación offline con firma digital (si aplicas)
            if (verifier.isValidSignature(key, product, domain)) {
                return LicenseCheckResult.VALID;
            }

            return LicenseCheckResult.INVALID;
        }

        // No hay clave: modo trial
        Instant trialStart = trialMetadataStore.getTrialStart(product);
        if (trialStart == null) {
            trialStart = Instant.now();
            trialMetadataStore.saveTrialStart(product, trialStart);
            return LicenseCheckResult.TRIAL;
        }

        long days = Duration.between(trialStart, Instant.now()).toDays();
        if (days <= TRIAL_DAYS) {
            return LicenseCheckResult.TRIAL;
        }

        return LicenseCheckResult.TRIAL_EXPIRED;
    }
}
