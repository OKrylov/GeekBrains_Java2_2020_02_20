package ru.geekbrains.java2.client.controller;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.view.AuthDialog;
import ru.geekbrains.java2.client.view.ClientChat;
import ru.geekbrains.java2.client.model.NetworkService;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

import static ru.geekbrains.java2.client.Command.*;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private String nickname;

    public ClientController(String serverHost, int serverPort) {
        this.networkService = new NetworkService(serverHost, serverPort);
        this.authDialog = new AuthDialog(this);
        this.clientChat = new ClientChat(this);
    }

    public void runApplication() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {
        networkService.setSuccessfulAuthEvent(new AuthEvent() {
            @Override
            public void authIsSuccessful(String nickname) {
                ClientController.this.setUserName(nickname);
                clientChat.setTitle(nickname);
                ClientController.this.openChat();
            }
        });
        authDialog.setVisible(true);

    }

    private void openChat() {
        authDialog.dispose();
        networkService.setMessageHandler(new MessageHandler() {
            @Override
            public void handle(String message) {
                clientChat.appendMessage(message);
            }
        });
        clientChat.setVisible(true);
    }

    private void setUserName(String nickname) {
        this.nickname = nickname;
    }

    private void connectToServer() throws IOException {
        try {
            networkService.connect(this);
        } catch (IOException e) {
            System.err.println("Failed to establish server connection");
            throw e;
        }
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendCommand(authCommand(login, pass));
    }

    public void sendMessageToAllUsers(String message) {
        try {
            networkService.sendCommand(broadcastMessageCommand(message));
        } catch (IOException e) {
            clientChat.showError("Failed to send message!");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        networkService.close();
    }

    public String getUsername() {
        return nickname;
    }

    public void sendPrivateMessage(String username, String message) {
        try {
            networkService.sendCommand(privateMessageCommand(username, message));
        } catch (IOException e) {
            showErrorMessage(e.getMessage());
        }
    }

    public void showErrorMessage(String errorMessage) {
        if (clientChat.isActive()) {
            clientChat.showError(errorMessage);
        }
        else if (authDialog.isActive()) {
            authDialog.showError(errorMessage);
        }
        System.err.println(errorMessage);
    }

    public void updateUsersList(List<String> users) {
        users.remove(nickname);
        users.add(0, "All");
        clientChat.updateUsers(users);
    }
}
