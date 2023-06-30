package net.buj.rml.loader;

import net.buj.rml.Environment;

public interface EntryPoint {
    void pre(Environment environment);
    void init(Environment environment);
}
