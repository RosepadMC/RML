package net.buj.rml.events.server;

import net.buj.rml.events.Event;


public final class PlayerLeaveEvent extends Event {
    private final String username;

    public PlayerLeaveEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
