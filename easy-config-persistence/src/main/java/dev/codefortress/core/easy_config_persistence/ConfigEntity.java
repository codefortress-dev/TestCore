package dev.codefortress.core.easy_config_persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "easy_config")
public class ConfigEntity {

    @Id
    @Column(name = "config_key", nullable = false, unique = true)
    private String key;

    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    @Column(name = "type")
    private String type;

    @Column(name = "module")
    private String module;

    @Column(name = "source")
    private String source;

    public ConfigEntity() {}

    public ConfigEntity(String key, String value, String type, String module, String source) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.module = module;
        this.source = source;
    }

    // Getters y Setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
