package net.buj.rml;

import net.buj.rml.annotations.NotNull;

import java.util.Objects;

public final class NamespacedKey {
    private final @NotNull String namespace;
    private final @NotNull String key;

    public NamespacedKey(@NotNull String namespace, @NotNull String key) {
        if (namespace == null) throw new IllegalArgumentException("namespace must not be null");
        if (key == null) throw new IllegalArgumentException("key must not be null");

        this.namespace = namespace;
        this.key = key;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamespacedKey that = (NamespacedKey) o;
        return namespace.equals(that.namespace) && key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, key);
    }
}
