package ru.geekbrains.java2.client.controller;

@FunctionalInterface
public interface MessageHandler {
    void handle(String message);
}
