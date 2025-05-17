package dev.codefortress.core.easy_licensing;

import java.io.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.Properties;

public class TrialMetadataStore {

    private static final Path TRIAL_DIR = Paths.get(System.getProperty("user.home"), ".easy-licenses");
    private static final String FILENAME = "trial-metadata.properties";

    public Instant getTrialStart(String product) {
        try {
            Properties props = load();
            String value = props.getProperty(product);
            return (value != null) ? Instant.parse(value) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveTrialStart(String product, Instant instant) {
        try {
            Properties props = load();
            props.setProperty(product, instant.toString());
            try (OutputStream out = Files.newOutputStream(TRIAL_DIR.resolve(FILENAME))) {
                props.store(out, "Easy-License Trial Metadata");
            }
        } catch (Exception ignored) {}
    }

    private Properties load() throws IOException {
        if (!Files.exists(TRIAL_DIR)) Files.createDirectories(TRIAL_DIR);
        Path file = TRIAL_DIR.resolve(FILENAME);
        Properties props = new Properties();
        if (Files.exists(file)) {
            try (InputStream in = Files.newInputStream(file)) {
                props.load(in);
            }
        }
        return props;
    }
}

