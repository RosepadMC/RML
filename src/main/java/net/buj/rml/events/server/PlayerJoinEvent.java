package net.buj.rml.events.server;

import net.buj.rml.annotations.NotNull;
import net.buj.rml.events.CancellableEvent;


public final class PlayerJoinEvent extends CancellableEvent {
    private final String username;
    private String message;

    public PlayerJoinEvent(String username, String joinMessage) {
        this.username = username;
        this.message = joinMessage;
    }

    public String getUsername() {
        return username;
    }

    public String getJoinMessage() {
        return message;
    }

    public void setJoinMessage(@NotNull String message) {
        this.message = message;
    }
}
