package net.buj.rml.loader;

import net.buj.rml.annotations.Nullable;

import java.net.URL;
import java.net.URLClassLoader;

public class JarLoader extends URLClassLoader {
    private final RosepadModLoader loader;

    public RosepadModLoader getModLoader() {
        return loader;
    }

    public JarLoader(RosepadModLoader loader, URL[] urls) {
        super(urls, null); // Java class resolution sucks, I'll do it myself
        this.loader = loader;
    }

    protected @Nullable Class<?> tryFindClass(String name) {
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    public Class<?> findClass(String name, boolean recursive) throws ClassNotFoundException {
        Class<?> klass = tryFindClass(name);
        if (klass == null && recursive) {
            return loader.findClass(name, this);
        }
        if (klass == null) throw new ClassNotFoundException();
        return klass;
    }
}
