package net.buj.rml;

import net.buj.rml.options.GameOptions;
import net.buj.rml.registry.Blocks;
import net.buj.rml.registry.Items;
import net.buj.rml.registry.Materials;
import net.buj.rml.registry.Entities;

/**
 * Global RML values. Must be initialized before loading any mods
 */
public class Game {
    private Game() {}

    public static MinecraftImpl minecraft;
    public static Environment environment;
    public static GameOptions options;

    public static Items items;
    public static Blocks blocks;
    public static Materials materials;
    public static Entities entities;
}
