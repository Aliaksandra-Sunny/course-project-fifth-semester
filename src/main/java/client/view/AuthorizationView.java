package client.view;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AuthorizationView extends JFrame {
    public static AuthorizationView jframe;
    private JLabel loginLabel, passwordLabel, imageLabel;
    ImageIcon image;

    private JButton loginButton;
    private JButton registrationButton;

    private JTextField loginField;
    private JPasswordField passwordField;

    private Controller controller ;

    public AuthorizationView(Controller controller) {
        jframe = this;
        this.setBackground(Color.PINK);
        this.setResizable(false);
        this.controller = controller;
        initComponents();
    }


    private void initComponents() {
        setTitle("Авторизация");
        setSize(600,300);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);

        image = new ImageIcon("E:\\images\\bank.jpg");
        imageLabel = new JLabel(image);
        imageLabel.setBounds(320, 20, 231, 195);
        add(imageLabel);

        loginLabel = new JLabel("Логин");
        add(loginLabel);
        loginLabel.setBounds(60,30,60,20);

        passwordLabel = new JLabel("Пароль");
        add(passwordLabel);
        passwordLabel.setBounds(60,80,60,20);

        loginButton = new JButton("Войти");
        add(loginButton);
        loginButton.setBounds(75,150,120,20);

        registrationButton = new JButton("Регистрация");
        add(registrationButton);
        registrationButton.setBounds(75,180,120,20);

        loginField = new JTextField();
        add(loginField);
        loginField.setBounds(60,50,150,20);

        passwordField = new JPasswordField();
        add(passwordField);
        passwordField.setBounds(60,100,150,20);

        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeData();
            }
        });

        registrationButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReg();
            }
        });
    }

    private void writeData() {
        String login = loginField.getText();
        String password = passwordField.getText();
        controller.writeMessage(login,password);
        loginField.setText("");
        passwordField.setText("");
    }

    private void openReg() {
        controller.openReg();
    }

}
