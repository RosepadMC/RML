package net.buj.rml.events.server;

import net.buj.rml.events.CancellableEvent;


public final class ChatMessageEvent extends CancellableEvent {
    private final String username;
    private final String original;
    private String message;

    public ChatMessageEvent(String username, String message) {
        this.username = username;
        this.original = message;
        this.message = "<" + username + "> " + message;
    }

    public String getUsername() {
        return username;
    }

    public String getOriginalMessage() {
        return original;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
