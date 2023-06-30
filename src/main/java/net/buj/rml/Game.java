package net.buj.rml;

import net.buj.rml.annotations.PublicAPI;
import net.buj.rml.events.EventLoop;
import net.buj.rml.loader.RosepadModLoader;
import net.buj.rml.options.GameOptions;
import net.buj.rml.registry.Blocks;
import net.buj.rml.registry.Items;
import net.buj.rml.registry.Materials;
import net.buj.rml.registry.Entities;

/**
 * Global RML values. Must be initialized before loading any mods
 */
public final class Game {
    private Game() {}

    /**
     * Minecraft implementation
     * @deprecated Use {@link net.buj.rml.Game#MINECRAFT} instead
     */
    @Deprecated
    public static MinecraftImpl minecraft;
    /**
     * Environment of the current game
     * @deprecated Use {@link net.buj.rml.Game#ENVIRONMENT} instead
     */
    @Deprecated
    public static Environment environment;
    /**
     * options.txt for client, server.properties for server
     * @deprecated Use {@link net.buj.rml.Game#OPTIONS} instead
     */
    @Deprecated
    public static GameOptions options;

    /**
     * Items registry
     * @apiNote Reserved for future APIs
     * @deprecated Use {@link net.buj.rml.Game#ITEMS} instead
     */
    @Deprecated
    public static Items items;
    /**
     * Blocks registry
     * @apiNote Reserved for future APIs
     * @deprecated Use {@link net.buj.rml.Game#BLOCKS} instead
     */
    @Deprecated
    public static Blocks blocks;
    /**
     * Materials registry
     * @apiNote Reserved for future APIs
     * @deprecated Use {@link net.buj.rml.Game#MATERIALS} instead
     */
    @Deprecated
    public static Materials materials;
    /**
     * Entities registry
     * @apiNote Reserved for future APIs
     * @deprecated Use {@link net.buj.rml.Game#ENTITIES} instead
     */
    @Deprecated
    public static Entities entities;

    /**
     * Mod loader
     * @deprecated Use {@link net.buj.rml.Game#MOD_LOADER} instead
     */
    @Deprecated
    public static RosepadModLoader modLoader;
    /**
     * Global event loop
     * @deprecated Use {@link net.buj.rml.Game#EVENT_LOOP} instead
     */
    @Deprecated
    public static EventLoop eventLoop;

    /**
     * Local chat implementation
     * @deprecated Use {@link net.buj.rml.Game#CHAT} instead
     */
    @Deprecated
    public static Chat chat;


    /**
     * Minecraft implementation
     */
    @PublicAPI
    public static MinecraftImpl MINECRAFT;
    /**
     * Environment of the current game
     */
    @PublicAPI
    public static Environment ENVIRONMENT;
    /**
     * options.txt for client, server.properties for server
     */
    @PublicAPI
    public static GameOptions OPTIONS;

    /**
     * Items registry
     * @apiNote Reserved for future APIs
     */
    @PublicAPI
    public static Items ITEMS;
    /**
     * Blocks registry
     * @apiNote Reserved for future APIs
     */
    @PublicAPI
    public static Blocks BLOCKS;
    /**
     * Materials registry
     * @apiNote Reserved for future APIs
     */
    @PublicAPI
    public static Materials MATERIALS;
    /**
     * Entities registry
     * @apiNote Reserved for future APIs
     */
    @PublicAPI
    public static Entities ENTITIES;

    /**
     * Mod loader
     */
    @PublicAPI
    public static RosepadModLoader MOD_LOADER;
    /**
     * Global event loop
     */
    @PublicAPI
    public static EventLoop EVENT_LOOP;

    /**
     * Local chat implementation
     */
    @PublicAPI
    public static Chat CHAT;


    /**
     * Internal function for providing legacy support.
     * @deprecated Will be removed in future releases
     */
    @Deprecated
    public static void __syncDeprecated() {
        minecraft = MINECRAFT;
        environment = ENVIRONMENT;
        options = OPTIONS;
        items = ITEMS;
        blocks = BLOCKS;
        materials = MATERIALS;
        entities = ENTITIES;
        modLoader = MOD_LOADER;
        eventLoop = EVENT_LOOP;
        chat = CHAT;
    }
}
