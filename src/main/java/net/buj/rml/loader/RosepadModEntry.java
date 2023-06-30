package net.buj.rml.loader;

import com.moandjiezana.toml.Toml;
import net.buj.rml.Environment;
import net.buj.rml.annotations.NotNull;
import net.buj.rml.annotations.Nullable;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class RosepadModEntry {
    private final String id;
    private final Version version;
    private @Nullable String name = null;
    private @Nullable String url = null;
    private @Nullable String website = null;
    private @Nullable String description = null;
    private @Nullable Environment environment = null;
    private final List<String> author = new ArrayList<>();
    private final List<EntryPoint> mains = new ArrayList<>();
    // TODO: Mixins
    //private final List<String> serverMixin = new ArrayList<>();
    //private final List<String> clientMixin = new ArrayList<>();
    private final List<LoaderExtension> extensionMain = new ArrayList<>();
    private final JarLoader loader;

    /*public RosepadModEntry(
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
    }*/

    public RosepadModEntry(@NotNull String id, @NotNull Version version, @NotNull JarLoader loader) {
        this.id = id;
        this.version = version;
        this.loader = loader;
    }

    public RosepadModEntry setName(String name) {
        this.name = name;
        return this;
    }

    public RosepadModEntry setRepositoryURL(String url) {
        this.url = url;
        return this;
    }

    public RosepadModEntry setHomepageURL(String url) {
        this.website = url;
        return this;
    }

    public RosepadModEntry setDescription(String description) {
        this.description = description;
        return this;
    }

    public RosepadModEntry setLaunchEnvironment(Environment env) {
        this.environment = env;
        return this;
    }

    public RosepadModEntry addAuthor(String author) {
        this.author.add(author);
        return this;
    }

    public RosepadModEntry addEntryPoint(EntryPoint entryPoint) {
        this.mains.add(entryPoint);
        return this;
    }


    public String getID() {
        return id;
    }

    public Version getVersion() {
        return version;
    }

    public String getName() {
        if (name == null) return getID();
        return name;
    }

    public @Nullable String getRepositoryURL() {
        return url;
    }

    public @Nullable String getHomepageURL() {
        return website;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public @Nullable Environment getLaunchEnvironment() {
        return environment;
    }

    public List<String> getAuthor() {
        return author;
    }

    public List<EntryPoint> getEntryPoints() {
        return mains;
    }

    public List<LoaderExtension> getExtensions() {
        return extensionMain;
    }

    public JarLoader getLoader() {
        return loader;
    }


    public static @Nullable RosepadModEntry from(JarLoader jarLoader, Environment environment) throws Exception {
        InputStream stream = jarLoader.getResourceAsStream("mod.toml");
        if (stream == null) {
            return null;
        }

        Toml toml = new Toml().read(stream);

        String id = toml.getString("id");
        if (id == null) throw new NullPointerException("Missing key 'id'");

        String version$1 = toml.getString("version");
        if (version$1 == null) throw new NullPointerException("Missing key 'id'");
        Version version = Version.from(version$1);

        RosepadModEntry entry = new RosepadModEntry(id, version, jarLoader);

        String name = toml.getString("name");
        if (name != null) entry.setName(name);

        String environment$1 = toml.getString("environment");
        if (environment$1 != null) {
            Environment environment$2;
            if (environment$1.equalsIgnoreCase("client")) {
                environment$2 = Environment.CLIENT;
            }
            else if (environment$1.equalsIgnoreCase("server")) {
                environment$2 = Environment.SERVER;
            }
            else throw new IllegalArgumentException("Invalid 'environment' field");

            if (!environment$2.equals(environment)) {
                return null;
            }

            entry.setLaunchEnvironment(environment$2);
        }

        String repoURL = toml.getString("url");
        if (repoURL != null) entry.setRepositoryURL(repoURL);

        String homepageURL = toml.getString("website");
        if (homepageURL != null) entry.setHomepageURL(homepageURL);

        String description = toml.getString("description");
        if (description != null) entry.setDescription(description);

        if (toml.contains("author")) {
            if (toml.containsPrimitive("author")) {
                entry.addAuthor(toml.getString("author"));
            }
            else {
                for (Object author : toml.getList("author")) {
                    if (author instanceof String) {
                        entry.addAuthor(author.toString());
                    }
                    else throw new IllegalArgumentException("'author' is not a string");
                }
            }
        }

        List<String> classesToLoad = new ArrayList<>();
        if (toml.contains("main.both")) {
            if (toml.containsPrimitive("main.both")) {
                classesToLoad.add(toml.getString("main.both"));
            }
            else {
                classesToLoad.addAll(toml.getList("main.both"));
            }
        }
        if (toml.contains("main." + environment.name().toLowerCase())) {
            if (toml.containsPrimitive("main." + environment.name().toLowerCase())) {
                classesToLoad.add(toml.getString("main." + environment.name().toLowerCase()));
            }
            else {
                classesToLoad.addAll(toml.getList("main." + environment.name().toLowerCase()));
            }
        }
        for (String classFullPath : classesToLoad) {
            Class<?> klass$1 = jarLoader.findClass(classFullPath);
            Class<? extends EntryPoint> klass = klass$1.asSubclass(EntryPoint.class);
            Constructor<? extends EntryPoint> constructor = klass.getConstructor();
            entry.addEntryPoint(constructor.newInstance());
        }

        classesToLoad = new ArrayList<>();
        if (toml.contains("main.loader")) {
            if (toml.containsPrimitive("main.loader")) {
                classesToLoad.add(toml.getString("main.loader"));
            }
            else {
                for (Object loader : toml.getList("main.loader")) {
                    if (loader instanceof String) {
                        entry.addAuthor(loader.toString());
                    }
                    else throw new IllegalArgumentException("'main.loader' is not a string");
                }
            }
        }
        for (String classFullPath : classesToLoad) {
            Class<?> klass$1 = jarLoader.findClass(classFullPath);
            Class<? extends LoaderExtension> klass = klass$1.asSubclass(LoaderExtension.class);
            Constructor<? extends LoaderExtension> constructor = klass.getConstructor();
            entry.extensionMain.add(constructor.newInstance());
        }

        return entry;
    }
}
