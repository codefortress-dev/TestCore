package dev.codefortress.core.easy_licensing;

public class LicenseInfo {

    private String product;
    private String key;
    private LicenseCheckResult status;
    private String source; // REMOTE, CACHE, OFFLINE, etc.

    public LicenseInfo() {}

    public LicenseInfo(String product, String key, LicenseCheckResult status, String source) {
        this.product = product;
        this.key = key;
        this.status = status;
        this.source = source;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LicenseCheckResult getStatus() {
        return status;
    }

    public void setStatus(LicenseCheckResult status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
