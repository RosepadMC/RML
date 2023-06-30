package net.buj.rml.loader;

public class ModCandidate {
    private final JarLoader jarLoader;
    private boolean isRegistered = false;

    public ModCandidate(JarLoader jarLoader) {
        this.jarLoader = jarLoader;
    }

    public void register(RosepadModEntry entry) {
        if (entry.getLoader() != jarLoader) throw new IllegalArgumentException("Incompatible mod entry provided");
        if (isRegistered) throw new IllegalStateException("Attempting to register a registered mod");
        jarLoader.getModLoader().register(entry);
        isRegistered = true;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}
