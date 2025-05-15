package dev.codefortress.core.easy_licensing;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class StoredLicenseCache {

    private static final String LICENSE_DIR = System.getProperty("user.home") + File.separator + ".easy-licenses";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(LicenseInfo licenseInfo) {
        try {
            File dir = new File(LICENSE_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File licenseFile = getFileForProduct(licenseInfo.getProduct());
            objectMapper.writeValue(licenseFile, licenseInfo);
        } catch (IOException e) {
            throw new LicenseException("No se pudo guardar la licencia localmente", e);
        }
    }

    public LicenseInfo load(String product) {
        try {
            File licenseFile = getFileForProduct(product);
            if (!licenseFile.exists()) {
                throw new LicenseException("No hay licencia activada localmente para el producto: " + product);
            }

            return objectMapper.readValue(licenseFile, LicenseInfo.class);
        } catch (IOException e) {
            throw new LicenseException("Error leyendo la licencia local", e);
        }
    }

    private File getFileForProduct(String product) {
        String safeProductName = product.replaceAll("[^a-zA-Z0-9\\-_]", "_");
        return Paths.get(LICENSE_DIR, safeProductName + ".license.json").toFile();
    }
}
