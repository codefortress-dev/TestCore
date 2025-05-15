package dev.codefortress.core.easy_licensing;
import java.net.InetAddress;

public class LicenseEnvironmentResolver {

    /**
     * Determina si la app está corriendo en un entorno de desarrollo/local
     */
    public boolean isDevelopmentEnvironment() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName().toLowerCase();

            return hostname.contains("localhost")
                    || hostname.contains("127.0.0.1")
                    || hostname.contains("dev")
                    || hostname.contains("test")
                    || System.getenv("ENV") != null && System.getenv("ENV").equalsIgnoreCase("dev")
                    || System.getProperty("env") != null && System.getProperty("env").equalsIgnoreCase("dev");

        } catch (Exception e) {
            return false;
        }
    }
}
