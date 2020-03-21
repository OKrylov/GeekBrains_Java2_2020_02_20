package ru.geekbrains.java2.client.view;

import ru.geekbrains.java2.client.controller.ClientController;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class AuthDialog extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField loginText;
    private JPasswordField passwordText;

    private ClientController controller;

    public AuthDialog(ClientController controller) {
        this.controller = controller;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400, 250);
        setLocationRelativeTo(null);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void onOK() {
        String login = loginText.getText().trim();
        String pass = new String(passwordText.getPassword()).trim();
        try {
            controller.sendAuthMessage(login, pass);
        } catch (IOException e) {
            showError("Ошибка при попытки аутентификации");
        }
    }

    private void onCancel() {
        System.exit(0);
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
