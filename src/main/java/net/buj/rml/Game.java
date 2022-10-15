package net.buj.rml;

import net.buj.rml.registry.Items;

/**
 * Global RML values. Must be initialized before loading any mods
 */
public class Game {
    private Game() {}

    public static MinecraftImpl minecraft;
    public static Environment environment;

    public static Items items;
}
