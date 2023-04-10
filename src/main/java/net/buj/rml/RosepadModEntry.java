package net.buj.rml;

import net.buj.rml.annotations.NotNull;
import net.buj.rml.annotations.Nullable;

import java.util.List;

public class RosepadModEntry {
    @NotNull
    String name;
    @NotNull String id;
    @NotNull String version;
    @Nullable Environment environment;
    @NotNull List<String> author;
    @Nullable
    String url;
    @Nullable String website;
    @Nullable String description;
    @NotNull List<String> serverMain;
    @NotNull List<String> clientMain;
    @NotNull List<String> extensionMain;
    @NotNull List<String> serverMixin;
    @NotNull List<String> clientMixin;

    public RosepadModEntry(
            @NotNull String name,
            @NotNull String id,
            @NotNull String version,
            @Nullable Environment environment,
            @NotNull List<String> author,
            @Nullable String url,
            @Nullable String website,
            @Nullable String description,
            @NotNull List<String> serverMain,
            @NotNull List<String> clientMain,
            @NotNull List<String> extensionMain,
            @NotNull List<String> serverMixin,
            @NotNull List<String> clientMixin
    ) {
        this.name = name;
        this.id = id;
        this.version = version;
        this.environment = environment;
        this.author = author;
        this.url = url;
        this.website = website;
        this.description = description;
        this.serverMain = serverMain;
        this.clientMain = clientMain;
        this.serverMixin = serverMixin;
        this.clientMixin = clientMixin;
    }
}
