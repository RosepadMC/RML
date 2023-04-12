package net.buj.rml.events.server;

import net.buj.rml.events.Event;


public final class PlayerJoinEvent extends Event {
    private final String username;

    public PlayerJoinEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
