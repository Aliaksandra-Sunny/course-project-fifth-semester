package client.view.userView;

import client.controller.Controller;
import client.entity.Client;
import client.entity.User;
import client.view.AuthorizationView;
import client.view.RegistrationView;
import client.view.adminView.EditCreditView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EditOwnInfoView extends JFrame{
    public static EditOwnInfoView jframe;

    Client editClient  = new Client();
    Client newClient = new Client();

    private JLabel loginLabel, passwordLabel, imageLabel, nameLabel, surnameLabel, passportLabel;
    private ImageIcon image;
    private JButton registrationButton, back;

    public JTextField  nameField, surnameField, passportField;

    private Controller controller;

    public EditOwnInfoView(Controller controller, Client editClient) {
        jframe = this;
        this.editClient=editClient;
        this.controller = controller;
        initComponents();

    }

    private void initComponents() {
        setTitle("Редактирование данных");
        setSize(500, 340);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);

        image = new ImageIcon("E:\\images\\user1.jpg");
        imageLabel = new JLabel(image);
        imageLabel.setBounds(260, 20, 190, 178);
        add(imageLabel);

        nameLabel = new JLabel("Имя");
        add(nameLabel);
        nameLabel.setBounds(50,20,60,20);

        nameField = new JTextField();
        add(nameField);
        nameField.setBounds(50,40,150,20);

        surnameLabel = new JLabel("Фамилия");
        add(surnameLabel);
        surnameLabel.setBounds(50,60,60,20);

        surnameField = new JTextField();
        add(surnameField);
        surnameField.setBounds(50,80,150,20);

        passportLabel = new JLabel("Серия и номер паспорта");
        add(passportLabel);
        passportLabel.setBounds(50,100,150,20);

        passportField = new JTextField();
        add(passportField);
        passportField.setBounds(50,120,150,20);


        registrationButton = new JButton("Редактировать");
        add(registrationButton);
        registrationButton.setBounds(50,170,150,20);

        back = new JButton("Назад");
        add(back);
        back.setBounds(340, 200, 100, 20);

        registrationButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editСlient();
            }
        });

        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToView();
            }
        });
    }

    private void editСlient() {
         newClient = Client.newBuilder()
                .idClient(editClient.getIdClient())
                .name(nameField.getText())
                .surname(surnameField.getText())
                .passport(passportField.getText())
                .idUserFK(editClient.getIdUserFK())
                .build();
        if(controller.editClient(newClient)){
            JOptionPane.showMessageDialog(EditOwnInfoView.jframe, "Успешно!");
        }else {
            JOptionPane.showMessageDialog(EditOwnInfoView.jframe, "Ошибка редактирования!");

        }
    }

    private void backToView() {
        editClient = Client.newBuilder()
                .idClient(editClient.getIdClient())
                .name(nameField.getText())
                .surname(surnameField.getText())
                .passport(passportField.getText())
                .idUserFK(editClient.getIdUserFK())
                .build();
        controller.backToMainUserView(newClient);
    }


}
