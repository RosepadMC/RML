package net.buj.rml;

public abstract class Chat {
    /**
     * Clear chat
     **/
    public abstract void clear();
    /**
     * Add text to chat
     *
     * On server: write in global chat
     **/
    public abstract void append(String text);
    /**
     * Show chat message to player
     *
     * On client: does nothing
     **/
    public abstract void append(String username, String text);
}
