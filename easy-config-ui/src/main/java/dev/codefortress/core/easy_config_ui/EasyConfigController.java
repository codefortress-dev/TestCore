package dev.codefortress.core.easy_config_ui;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/easy-config")
public class EasyConfigController {

    private final EasyConfigStore configStore;

    public EasyConfigController(EasyConfigStore configStore) {
        this.configStore = configStore;
    }

    /**
     * Lista todas las propiedades disponibles y su valor actual
     */
    @GetMapping
    public List<ConfigEntry> list() {
        return configStore.getAll().entrySet().stream()
                .map(entry -> new ConfigEntry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el valor de una propiedad
     */
    @GetMapping("/{key}")
    public ConfigEntry get(@PathVariable String key) {
        return new ConfigEntry(key, configStore.get(key));
    }

    /**
     * Modifica una propiedad en caliente
     */
    @PostMapping("/{key}")
    public void update(@PathVariable String key, @RequestBody ConfigEntry entry) {
        configStore.set(key, entry.value());
    }

    /**
     * DTO simple
     */
    public record ConfigEntry(String key, String value) {}
}
