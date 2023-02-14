package net.buj.rml.options;

public class KeyBinding {
    public KeyBinding(String name, String description, int code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public int code;
    public String name;
    public String description;
}
