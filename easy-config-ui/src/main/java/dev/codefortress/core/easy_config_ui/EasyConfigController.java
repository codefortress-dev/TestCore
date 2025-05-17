package dev.codefortress.core.easy_config_ui;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/easy-config")
public class EasyConfigController {

    private final EasyConfigStore configStore;

    public EasyConfigController(EasyConfigStore configStore) {
        this.configStore = configStore;
    }

    @GetMapping
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (EasyConfigScanner.ConfigMetadata meta : EasyConfigScanner.getAll()) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("key", meta.key());
            entry.put("description", meta.description());
            entry.put("value", configStore.get(meta.key()));
            result.add(entry);
        }
        return result;
    }

    @PostMapping("/{key}")
    public Map<String, Object> update(@PathVariable String key, @RequestBody Map<String, String> body) {
        if (!configStore.contains(key)) {
            throw new RuntimeException("La clave '" + key + "' no está registrada.");
        }
        String value = body.get("value");
        configStore.set(key, value);
        return Map.of("key", key, "newValue", value);
    }
}
