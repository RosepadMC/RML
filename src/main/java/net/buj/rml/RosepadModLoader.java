package net.buj.rml;

import com.moandjiezana.toml.Toml;
import net.buj.rml.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class RosepadModLoader {
    public final List<RosepadMod> mods = new ArrayList<>();
    public final Map<RosepadMod, RosepadModEntry> meta = new HashMap<>();
    public final List<ClassLoader> loaders = new ArrayList<>();
    private final Map<String, Class<?>> cache = new HashMap<>();

    @SafeVarargs
    private final <T> List<T> concat(List<T>... lists) {
        List<T> list = new ArrayList<>();
        for (List<T> sub : lists) {
            list.addAll(sub);
        }
        return list;
    }

    private String validateVersion(String version) {
        if (!version.matches("^(\\d+\\.)*\\d+(-[a-z]+)?$")) {
            throw new IllegalArgumentException("'version' tag has invalid format");
        }
        return version;
    }

    private String validateModId(String id) {
        if (!id.matches("[a-z][a-z\\d_-]*[a-z\\d]$")) {
            throw new IllegalArgumentException("'id' tag has invalid format");
        }
        return id;
    }

    private Environment validateEnvironment(@Nullable String env) {
        if (env == null) return null;
        switch (env) {
            case "both":
                return null;
            case "server":
                return Environment.SERVER;
            case "client":
                return Environment.CLIENT;
            default:
                throw new IllegalArgumentException("'environment' tag has invalid format");
        }
    }

    private List<String> validateStrList(@Nullable List<Object> authors, int type) {
        if ((authors == null || authors.isEmpty()) && type == 1) throw new IllegalArgumentException("No author defined");

        List<String> lists = new ArrayList<>();

        if (authors == null) return lists;

        for (Object author : authors) {
            if (author instanceof String) lists.add((String) author);
            else switch (type) {
                case 1:
                    throw new IllegalArgumentException("Invalid author");
                case 2:
                    throw new IllegalArgumentException("Invalid main class");
                case 3:
                    throw new IllegalArgumentException("Invalid mixin class");
            }
        }

        return lists;
    }

    private void loadModFromFile(Environment env, File file) {
        RosepadModEntry rEntry;

        try {
            JarFile jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("mod.toml");

            if (entry == null) {
                return;
            }

            InputStream stream = jar.getInputStream(entry);
            Toml toml = new Toml().read(stream);

            List<String> bothMains = validateStrList(toml.containsPrimitive("main.both")
                    ? Collections.singletonList(Objects.requireNonNull(toml.getString("main.both")))
                    : toml.getList("main.both"), 2);
            List<String> bothMixins = validateStrList(toml.containsPrimitive("mixin.both")
                    ? Collections.singletonList(Objects.requireNonNull(toml.getString("mixin.both")))
                    : toml.getList("mixin.both"), 2);

            rEntry = new RosepadModEntry(
                    Objects.requireNonNull(toml.getString("name")),
                    validateModId(Objects.requireNonNull(toml.getString("id"))),
                    validateVersion(Objects.requireNonNull(toml.getString("version"))),
                    validateEnvironment(toml.getString("environment")),
                    validateStrList(toml.containsPrimitive("author")
                            ? Collections.singletonList(Objects.requireNonNull(toml.getString("author")))
                            : toml.getList("author"), 1),
                    toml.getString("url"),
                    toml.getString("website"),
                    toml.getString("description"),
                    concat(validateStrList(toml.containsPrimitive("main.server")
                            ? Collections.singletonList(Objects.requireNonNull(toml.getString("main.server")))
                            : toml.getList("main.server"), 2), bothMains),
                    concat(validateStrList(toml.containsPrimitive("main.client")
                            ? Collections.singletonList(Objects.requireNonNull(toml.getString("main.client")))
                            : toml.getList("main.client"), 2), bothMains),
                    concat(validateStrList(toml.containsPrimitive("mixin.server")
                            ? Collections.singletonList(Objects.requireNonNull(toml.getString("mixin.server")))
                            : toml.getList("mixin.server"), 2), bothMixins),
                    concat(validateStrList(toml.containsPrimitive("mixin.client")
                            ? Collections.singletonList(Objects.requireNonNull(toml.getString("mixin.client")))
                            : toml.getList("mixin.client"), 2), bothMixins)
            );

            stream.close();
            jar.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // TODO: All the actual modloader stuff

        RosepadJarLoader loader;

        try {
            URL[] urls = new URL[1];
            urls[0] = file.toURI().toURL();

            loader = new RosepadJarLoader(this, urls, ClassLoader.getSystemClassLoader());

            List<String> mains = env == Environment.CLIENT ? rEntry.clientMain : rEntry.serverMain;
            for (String main : mains) {
                Class<?> klass = loader.loadClass(main);
                Class<? extends RosepadMod> modClass = klass.asSubclass(RosepadMod.class);
                Constructor<? extends RosepadMod> constructor = modClass.getConstructor();
                RosepadMod mod = constructor.newInstance();

                loaders.add(loader);

                mod.pre(env);

                mods.add(mod);
                meta.put(mod, rEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadModsFromFile(Environment env, File file) {
        if (!file.exists()) return;

        if (file.isFile()) {
            if (file.getName().toLowerCase(Locale.ROOT).endsWith(".jar")) loadModFromFile(env, file);
            return;
        }
        if (file.isDirectory()) {
            for (File sub : Objects.requireNonNull(file.listFiles())) {
                loadModsFromFile(env, sub);
            }
        }
    }

    private @Nullable Class<?> tryFindClass(String name, ClassLoader loader) {
        try {
            if (loader instanceof RosepadJarLoader) {
                return ((RosepadJarLoader) loader).findClass(name, false);
            }
            return loader.loadClass(name);
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    public @Nullable Class<?> tryFindClass(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        for (ClassLoader loader : loaders) {
            Class<?> klass = tryFindClass(name, loader);
            if (klass != null) {
                cache.put(name, klass);
                return klass;
            }
        }
        cache.put(name, null);
        return null;
    }

    public void load(Environment env, File modsDir) {
        loadModsFromFile(env, modsDir); // TODO: Use MinecraftImpl's getMinecraftDir instead
        for (Map.Entry<String, Class<?>> entry : new HashMap<>(cache).entrySet()) {
            if (entry.getValue() == null) {
                cache.remove(entry.getKey());
            }
        }
        for (RosepadMod mod : mods) {
            mod.init(env);
        }
    }
}
