package net.buj.rml.loader;

import net.buj.rml.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class GameJar {
    private @NotNull Path path;

    public GameJar(@NotNull Path path) {
        this.path = path;
    }

    /**
     * <p>
     *  Apply custom patches to minecraft jar. Useful for mixins or injecting mod loader-specific patches
     * </p>
     * <p>
     *  Calling this method will "release" the jar file. Destination path is guaranteed to be unique for provided modid.
     *  Patcher must put a valid jar file at the destination path. A file may already exist in destination path in case
     *  extension developer wants to implement caching.
     * </p>
     *
     * @return Array containing source and destination paths
     */
    public Path[] patchJar(String modid) {
        Path newPath = path.getParent().resolve(".mod" + modid + ".jar");
        Path[] arr = new Path[] { path, newPath };
        path = newPath;
        return arr;
    }

    public URL getURL() throws MalformedURLException {
        return path.toUri().toURL();
    }
}
