package net.buj.rml.events.server;

import net.buj.rml.annotations.NotNull;
import net.buj.rml.events.CancellableEvent;


public final class PlayerLeaveEvent extends CancellableEvent {
    private final String username;
    private String message;

    public PlayerLeaveEvent(String username, String leaveMessage) {
        this.username = username;
        this.message = leaveMessage;
    }

    public String getUsername() {
        return username;
    }

    public String getLeaveMessage() {
        return message;
    }

    public void setLeaveMessage(@NotNull String message) {
        this.message = message;
    }
}
