package dev.codefortress.core.easy_licensing;
import java.time.LocalDate;

public class LicenseInfo {

    private String product;
    private String domain;
    private LocalDate validUntil;
    private LicenseCheckResult status;

    public LicenseInfo(String product, String domain, LocalDate validUntil, LicenseCheckResult status) {
        this.product = product;
        this.domain = domain;
        this.validUntil = validUntil;
        this.status = status;
    }

    public String getProduct() {
        return product;
    }

    public String getDomain() {
        return domain;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public LicenseCheckResult getStatus() {
        return status;
    }
}