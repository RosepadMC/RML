package net.buj.rml;

import net.buj.rml.loader.EntryPoint;

public abstract class RosepadMod implements EntryPoint {
    public abstract void pre(Environment environment);
    public abstract void init(Environment environment);
}
