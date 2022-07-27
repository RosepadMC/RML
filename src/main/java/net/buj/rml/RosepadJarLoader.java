package net.buj.rml;

import net.buj.rml.annotations.Nullable;

import java.net.URL;
import java.net.URLClassLoader;

public class RosepadJarLoader extends URLClassLoader {
    RosepadModLoader loader;

    public RosepadJarLoader(RosepadModLoader loader, URL[] urls, ClassLoader parent) {
        super(urls, parent);
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
            klass = loader.tryFindClass(name);
        }
        if (klass == null) throw new ClassNotFoundException();
        return klass;
    }
}
