package net.buj.rml.registry;

import net.buj.rml.annotations.NotNull;
import net.buj.rml.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Entities extends Registry<Entities.Entity> {
    public abstract static class Entity {}
}
