package net.buj.rml.world;

import java.util.Optional;

import net.buj.rml.annotations.Nullable;
import net.buj.rml.registry.Items;
import net.buj.rml.registry.Items.Item;

public class ItemStack {
    public int stackSize;
    public @Nullable Items.Item stackItem;

    public ItemStack add(int count) {
        stackSize += count;
        if (stackSize <= 0) {
            stackItem = null;
        }
        return this;
    }

    public ItemStack remove(int count) {
        stackSize -= count;
        if (stackSize <= 0) {
            stackItem = null;
        }
        return this;
    }

    public ItemStack item(Items.Item item) {
        stackItem = item;
        return this;
    }

    public Optional<Item> item() {
        if (stackItem == null || stackItem.getKey().equals("minecraft", "air")) {
            return Optional.empty();
        }
        return Optional.of(stackItem);
    }
}
