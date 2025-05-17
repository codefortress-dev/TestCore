package dev.codefortress.core.easy_licensing;

public class LicenseEnvironmentResolver {

    public boolean isDevOrLocal() {
        String env = System.getenv("EASY_ENV");
        return env == null || env.equalsIgnoreCase("dev") || env.equalsIgnoreCase("local") ||
               isLocalhost(System.getenv("HOSTNAME"));
    }

    private boolean isLocalhost(String hostname) {
        return hostname == null || hostname.contains("localhost");
    }

    public String resolveDomain() {
        String env = System.getenv("APP_DOMAIN");
        return (env != null && !env.isBlank()) ? env : "localhost";
    }
}
