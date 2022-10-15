package net.buj.rml.registry;

import net.buj.rml.NamespacedKey;
import net.buj.rml.annotations.NotNull;
import net.buj.rml.annotations.Nullable;
import net.buj.rml.registry.Entities.Entity;
import net.buj.rml.world.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Replace Object with World
// TODO: Replace Entity with PlayerEntity

public abstract class Items extends Registry<Items.Item> {
    private final List<Items.Item[]> idList = new ArrayList<>();
    private Items.Item[] idCache = new Items.Item[0];

    public abstract static class Item {

        // General data
        public int id;
        public int iconIndex; // TODO: Replace with Texture
        public int itemStackLimit = 64;
        public boolean vanilla = false;
        public boolean full3D = false;
        public int rarity = 0;
        public int maxDamage = 32;
        public @Nullable String name = null;

        // Ton of accessors

        public int getIconIndex() {
            return iconIndex;
        }
        public int getIconIndex(final ItemStack stack) {
            return iconIndex;
        }
        public Item setIconIndex(final int i) {
            iconIndex = i;
            return this;
        }

        public int getMaxDamage() {
            return maxDamage;
        }
        public Item setMaxDamage(int md) {
            maxDamage = md;
            return this;
        }

        public int getItemStackLimit() {
            return itemStackLimit;
        }
        public Item setItemStackLimit(int isl) {
            itemStackLimit = isl;
            return this;
        }

        public int getRarity() {
            return rarity;
        }
        public Item setRarity(int r) {
            rarity = r;
            return this;
        }

        public boolean isVanilla() {
            return vanilla;
        }
        public Item setVanilla(boolean v) {
            vanilla = v;
            return this;
        }

        public boolean isFull3D() {
            return full3D;
        }
        public Item setFull3D(boolean td) {
            full3D = td;
            return this;
        }

        public @NotNull String getName() {
            if (name == null) {
                return "Known item";
            }
            return name;
        }
        public Item setName(String n) {
            name = n;
            return this;
        }

        // Events

        /**
         * Called when a block is clicked with an item
         *
         * @returns Whenever block click event needs to be canceled
         */
        public boolean onItemBlockClick(
                                        final ItemStack stack,
                                        final Entity playerEntity,
                                        final Object world,
                                        final int x,
                                        final int y,
                                        final int z,
                                        final int side) {
            return false;
        }

        /**
         * TODO: Documentation needed
         */
        public ItemStack onItemUse(
                                        final ItemStack stack,
                                        final Object world,
                                        final Entity playerEntity) {
            return stack;
        }

        /**
         * TODO: Documentation needed
         */
        public void onBlockDestroyed(
                                     final ItemStack stack,
                                     final int id, // Clearly world is not needed
                                     final int x,
                                     final int y,
                                     final int z) {}

        /**
         * TODO: Documentation needed
         */
        public void onEntityHit(
                                final ItemStack stack,
                                final Entity attacked) {}

        /**
         * Called when player hits entity with this item
         */
        public void onEntitySaddle(
                                final ItemStack stack,
                                final Entity saddled) {}

        /**
         * Called when player attempts to mine a block with this item
         *
         * @returns Whenever tool can harvest the block
         */
        public boolean onBlockHarvest(
                                      final Blocks.Block block,
                                      final Object world) { return false; }

        // No name for those

        /**
         * TODO: Documentation needed
         */
        public int damageVsEntity(final Entity attacked) { return 1; }

        /**
         * TODO: Documentation needed
         */
        public float damageVsBlock(
                                   final ItemStack stack,
                                   final Blocks.Block block) { return 1.0f; }
    }

    public int getFreeId() {
        Items.Item[] array = null;
        for (int i = 0; i < idList.size() * 256; i++) {
            if (i % 256 == 0) array = idList.get(i / 256);
            if (array[i % 256] == null) return i;
        }
        idList.add(new Items.Item[256]);
        idCache = new Items.Item[idList.size() * 256];
        return idList.size() * 256;
    }

    public Optional<Items.Item> byId(int id) {
        if (id < 0) return Optional.empty();
        if (id >= idList.size() * 256) return Optional.empty();
        if (idCache[id] != null) {
            return Optional.of(idCache[id]);
        }
        Items.Item[] items = idList.get(id / 256);
        if (items[id / 256] == null) return Optional.empty();
        idCache[id] = items[id / 256];
        return Optional.of(idCache[id]);
    }

    protected abstract @Nullable Items.Item createItem(int id);

    public @Nullable Items.Item create(int id) {
        boolean resetCache = false;
        while (idList.size() <= id / 256) {
            resetCache = true;
            idList.add(new Items.Item[256]);
        }
        if (resetCache) idCache = new Items.Item[idList.size() * 256];

        return createItem(id);
    }

    public @Nullable Items.Item free() {
        return create(getFreeId());
    }

    /**
     * Get all registered items
     *
     * @return Items list without `null` elements
     */
    public List<Items.Item> all() {
        List<Items.Item> items = new ArrayList<>();
        for (Items.Item[] array : idList) {
            for (Items.Item item : array) {
                if (item != null) items.add(item);
            }
        }
        return items;
    }

    @Override
    public void register(NamespacedKey key, Items.Item value) {
        if (byId(value.id).isPresent()) throw new IllegalArgumentException("Detected ID conflict: Item " + key + " attempted to take already registered item ID " + value.id);
        int len = value.id / 256;
        if (len >= idList.size()) {
            while (len >= idList.size()) {
                idList.add(new Items.Item[256]);
            }
            idCache = new Items.Item[idList.size() * 256];
        }
        super.register(key, value);
    }
}
