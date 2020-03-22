package ru.geekbrains.java2.client;

public enum CommandType {
    AUTH,
    AUTH_ERROR,
    PRIVATE_MESSAGE,
    BROADCAST_MESSAGE,
    MESSAGE,
    UPDATE_USERS_LIST,
    ERROR,
    END,
}
