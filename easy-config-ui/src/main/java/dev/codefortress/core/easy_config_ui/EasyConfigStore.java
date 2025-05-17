package dev.codefortress.core.easy_config_ui;

public interface EasyConfigStore {
    String get(String key);
    void set(String key, String value);
    boolean contains(String key);
}
