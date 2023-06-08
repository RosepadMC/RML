package net.buj.rml;

import java.nio.file.Path;

public interface MinecraftImpl {
    int[] getVersion();
    String getVersionTag();
    String getVersionString();

    //TODO: Probably should be moved to the Environment enum
    /**
     * Get current environment's working directory.
     * @return A string depicting an absolute file path to the game's working directory. If current{@link Environment} is <code>CLIENT</code>, returns the path to <code>.minecraft</code>. Otherwise returns the path to the directory that contains <code>server.properties</code>
     */
    Path getGameDirectory();
}
