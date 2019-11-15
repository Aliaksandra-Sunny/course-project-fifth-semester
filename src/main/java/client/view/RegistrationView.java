package client.view;

import client.controller.Controller;
import client.entity.Client;
import client.entity.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class RegistrationView extends JFrame {
    public static RegistrationView jframe;

    private JLabel loginLabel, passwordLabel, imageLabel, nameLabel, surnameLabel, passportLabel;
private ImageIcon image;
    private JButton registrationButton, back;

    private JTextField loginField, passwordField, nameField, surnameField, passportField;

    private Controller controller;

    public RegistrationView(Controller controller) {
        jframe = this;
        this.controller = controller;
        // windowClose();
        initComponents();

    }

    private void initComponents() {
        setTitle("Регистрация клиента");
        setSize(500, 340);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);

        image = new ImageIcon("E:\\images\\user1.jpg");
        imageLabel = new JLabel(image);
        imageLabel.setBounds(260, 20, 190, 178);
        add(imageLabel);

        loginLabel = new JLabel("Логин");
        add(loginLabel);
        loginLabel.setBounds(50,20,60,20);

        loginField = new JTextField();
        add(loginField);
        loginField.setBounds(50,40,150,20);

        passwordLabel = new JLabel("Пароль");
        add(passwordLabel);
        passwordLabel.setBounds(50,60,60,20);

        passwordField = new JTextField();
        add(passwordField);
        passwordField.setBounds(50,80,150,20);

        nameLabel = new JLabel("Имя");
        add(nameLabel);
        nameLabel.setBounds(50,100,60,20);

        nameField = new JTextField();
        add(nameField);
        nameField.setBounds(50,120,150,20);

        surnameLabel = new JLabel("Фамилия");
        add(surnameLabel);
        surnameLabel.setBounds(50,140,60,20);

        surnameField = new JTextField();
        add(surnameField);
        surnameField.setBounds(50,160,150,20);

        passportLabel = new JLabel("Серия и номер паспорта");
        add(passportLabel);
        passportLabel.setBounds(50,180,150,20);

        passportField = new JTextField();
        add(passportField);
        passportField.setBounds(50,200,150,20);


        registrationButton = new JButton("Зарегистироваться");
        add(registrationButton);
        registrationButton.setBounds(50,230,150,20);

        back = new JButton("Назад");
        add(back);
        back.setBounds(340, 260, 100, 20);

        registrationButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regNewСlient();
            }
        });

        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToView();
            }
        });
    }

    private void regNewСlient() {
        User user = User.newBuilder()
                .login(loginField.getText())
                .password(passwordField.getText())
                .role("user")
                .build();
        Client client = Client.newBuilder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .passport(passportField.getText())
                .build();
        controller.regClient(user,client);
    }

    private void backToView() {
        controller.backToAuthorizationView();
    }


    public static void windowClose(){
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jframe.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent event)
            {
                Object[] options = { "Да", "Нет" };
                int rc = JOptionPane.showOptionDialog(
                        event.getWindow(), "Закрыть окно?",
                        "Подтверждение", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (rc == 0) {
                    event.getWindow().setVisible(false);
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
