package net.buj.rml.registry;

import net.buj.rml.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Blocks extends Registry<Blocks.Block> {
    public Blocks(Items source) {
        this.source = source;
    }

    private final Items source;

    public abstract static class Block extends Items.Item {
    }

    public int getFreeId() {
        return source.getFreeId();
    }

    public Optional<Blocks.Block> byId(int id) {
        Optional<Items.Item> item = source.byId(id);
        if (item.isEmpty()) return Optional.empty();
        Items.Item item$2 = item.get();
        if (item$2 instanceof Blocks.Block) return Optional.of((Blocks.Block) item$2);
        return Optional.empty();
    }

    protected abstract @Nullable Blocks.Block createBlock(int id);

    public @Nullable Blocks.Block create(int id) {
        if (source.create(id) == null) return null;
        return createBlock(id);
    }

    public @Nullable Blocks.Block free() {
        return create(getFreeId());
    }

    /**
     * Get all registered items
     *
     * @return Blocks list without `null` elements
     */
    public List<Blocks.Block> all() {
        List<Blocks.Block> items = new ArrayList<>();
        for (Items.Item item : source.all()) {
            if (item instanceof Blocks.Block) items.add((Block) item);
        }
        return items;
    }
}
