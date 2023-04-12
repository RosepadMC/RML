package net.buj.rml.events;

public abstract class Event {
    public static enum Priority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST,
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isCancelable() {
        return false;
    }

    public void cancel() {
        throw new RuntimeException("Event cannot be canceled");
    }
}
