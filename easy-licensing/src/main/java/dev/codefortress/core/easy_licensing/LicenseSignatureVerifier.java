package dev.codefortress.core.easy_licensing;
public class LicenseSignatureVerifier {

    /**
     * Verifica si una licencia cargada localmente es válida.
     * En esta versión base, solo verifica que no esté expirada.
     * En futuras versiones, puede incluir firma digital.
     */
    public boolean isValid(LicenseInfo licenseInfo, LicenseProperties properties) {
        if (licenseInfo == null) return false;

        // Verifica que el producto coincida
        if (!properties.getProduct().equalsIgnoreCase(licenseInfo.getProduct())) {
            return false;
        }

        // Verifica que la fecha de expiración no haya pasado
        if (licenseInfo.getValidUntil() == null || licenseInfo.getValidUntil().isBefore(java.time.LocalDate.now())) {
            return false;
        }

        // Aquí se podría validar una firma digital en el futuro
        return true;
    }
}

