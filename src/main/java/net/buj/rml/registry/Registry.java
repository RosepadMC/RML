package net.buj.rml.registry;

import net.buj.rml.NamespacedKey;
import net.buj.rml.annotations.NotNull;
import net.buj.rml.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Registry<T extends Registry.RegistryItem> {
    protected final Map<NamespacedKey, T> registry = new HashMap<>();

    public abstract static class RegistryItem {
        public @Nullable NamespacedKey key = null;

        @Nullable
        public NamespacedKey getKey() {
            return key;
        }
    }

    public void register(NamespacedKey key, T value) {
        value.key = key;
        registry.put(key, value);
    }

    public @Nullable T get(NamespacedKey key) {
        return registry.get(key);
    }

    public @NotNull T get(NamespacedKey key, @NotNull T defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }
}
