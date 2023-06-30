package net.buj.rml;

public interface Chat {
    /**
     * Clear chat
     **/
    void clear();
    /**
     * Add text to chat
     * <p>
     * On server: write in global chat
     **/
    void append(String text);
    /**
     * Show chat message to player
     * <p>
     * On client: does nothing
     **/
    void append(String username, String text);
}
