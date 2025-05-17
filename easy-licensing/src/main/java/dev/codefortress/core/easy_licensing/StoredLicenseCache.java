package dev.codefortress.core.easy_licensing;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class StoredLicenseCache {

    private final Path cacheDir = Paths.get(System.getProperty("user.home"), ".easy-licenses");

    public void store(LicenseInfo info) {
        try {
            if (!Files.exists(cacheDir)) {
                Files.createDirectories(cacheDir);
            }
            Path file = cacheDir.resolve("license-" + info.getProduct() + ".json");
            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                writer.write("{");
                writer.write("\"key\":\"" + info.getKey() + "\",");
                writer.write("\"status\":\"" + info.getStatus().name() + "\",");
                writer.write("\"source\":\"CACHE\"");
                writer.write("}");
            }
        } catch (IOException ignored) {}
    }

    public LicenseInfo get(String product) {
        try {
            Path file = cacheDir.resolve("license-" + product + ".json");
            if (!Files.exists(file)) return null;

            String content = Files.readString(file);
            Map<String, String> parsed = parse(content);
            return new LicenseInfo(product, parsed.get("key"), LicenseCheckResult.valueOf(parsed.get("status")), "CACHE");

        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.replaceAll("[{}\" ]", "");
        String[] parts = json.split(",");
        for (String part : parts) {
            String[] pair = part.split(":");
            if (pair.length == 2) map.put(pair[0], pair[1]);
        }
        return map;
    }
}
