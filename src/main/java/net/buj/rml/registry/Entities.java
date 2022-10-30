package net.buj.rml.registry;

import java.util.Optional;

import net.buj.rml.Game;
import net.buj.rml.NamespacedKey;
import net.buj.rml.annotations.Nullable;
import net.buj.rml.registry.Registry.RegistryItem;

public class Entities extends Registry<Entities.EntityClass> {
    public class EntityClass extends RegistryItem {
        public Class<Entity> cls;

        public EntityClass(Class<Entity> _cls) {
            cls = _cls;
        }
    }

    public Optional<EntityClass> findFor(Entity entity) {
        for (EntityClass cls : registry.values()) {
            if (cls.cls == entity.getClass())
                return Optional.of(cls);
        }
        return Optional.empty();
    }

    public abstract static class Entity {
        private @Nullable NamespacedKey __key = null;

        public Optional<EntityClass> getEntityClass() {
            return Game.entities.findFor(this);
        }

        public NamespacedKey getKey() {
            if (__key != null) return __key;
            throw new NullPointerException("Entity class is not registered");
        }
    }
}
