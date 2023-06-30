package net.buj.rml.loader;

import net.buj.rml.annotations.PublicAPI;

import java.nio.file.Path;
import java.util.List;

public final class ExtensionContext {
    private final List<ModCandidate> candidates;
    private final GameJar gameJar;
    private final String modId;

    public ExtensionContext(
        List<ModCandidate> candidates,
        GameJar gameJar,
        String modId
    ) {
        this.candidates = candidates;
        this.gameJar = gameJar;
        this.modId = modId;
    }

    @PublicAPI
    public List<ModCandidate> listCandidates() {
        return candidates;
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
    @PublicAPI
    public Path[] patchJar() {
        return gameJar.patchJar(modId);
    }
}
