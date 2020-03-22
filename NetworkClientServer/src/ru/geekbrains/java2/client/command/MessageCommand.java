package ru.geekbrains.java2.client.command;

import java.io.Serializable;

public class MessageCommand implements Serializable {

    private final String username;
    private final String message;

    public MessageCommand(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
