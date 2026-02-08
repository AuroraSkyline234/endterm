package com.example.endterm.patterns;

import java.util.Set;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    private final Set<String> allowedTypes = Set.of("WEAPON", "POTION");

    private ConfigManager() {}

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public boolean isAllowedType(String type) {
        return type != null && allowedTypes.contains(type.toUpperCase());
    }
}
