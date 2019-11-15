package client.view.adminView;

import client.controller.Controller;

import client.entity.User;
import client.entity.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddWorkerView extends JFrame {
    public static AddWorkerView jframe;
    private JLabel image;
    private JLabel label;

    private JTextField loginField, passwordField, nameField, surnameField, patronymicField, salaryField;

    private JButton back, addWorker;

    private Controller controller;

    public AddWorkerView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    public void initComponents() {
        setSize(470, 350);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Добавить работника");

        image = new JLabel(new ImageIcon(getClass().getResource("/client/images/worker.jpg")));
        image.setBounds(20, 20, 200, 120);
        add(image);

        label = new JLabel("Имя");
        add(label);
        label.setBounds(250, 30, 60, 20);

        nameField  = new JTextField();
        add(nameField);
        nameField.setBounds(250, 50, 150, 20);

        label = new JLabel("Отчество");
        add(label);
        label.setBounds(250, 70, 60, 20);

        patronymicField  = new JTextField();
        add(patronymicField);
        patronymicField.setBounds(250, 90, 150, 20);

        label = new JLabel("Фамилия");
        add(label);
        label.setBounds(250, 110, 60, 20);

        surnameField = new JTextField();
        add(surnameField);
        surnameField.setBounds(250, 130, 150, 20);

        label = new JLabel("Заработная плата($)");
        add(label);
        label.setBounds(250, 150, 150, 20);

        salaryField = new JTextField();
        add(salaryField);
        salaryField.setBounds(250, 170, 150, 20);

        label = new JLabel("Логин");
        add(label);
        label.setBounds(20, 150, 60, 20);

        loginField = new JTextField();
        add(loginField);
        loginField.setBounds(20, 170, 150, 20);

        label = new JLabel("Пароль");
        add(label);
        label.setBounds(20, 190, 60, 20);

        passwordField = new JTextField();
        add(passwordField);
        passwordField.setBounds(20, 210, 150, 20);

        addWorker = new JButton("Добавить");
        add(addWorker);
        addWorker.setBounds(250,210,150,20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(300, 260, 100, 20);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backToAdminViewFromaddWorkerViewView();
            }
        });

        addWorker.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWorker();
            }
        });
    }

    private void backToAdminViewFromaddWorkerViewView() {
        controller.backToAdminViewFromaddWorkersView();
    }

    private void addWorker(){
        User user = User.newBuilder()
                .login(loginField.getText())
                .password(passwordField.getText())
                .role("worker")
                .build();
        Worker worker = Worker.newBuilder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .patronymic(patronymicField.getText())
                .salary(Integer.valueOf(salaryField.getText()))
                .build();
        controller.regWorker(user,worker);
        controller.backToWorkersAdminViewFromaddWorkerView();
    }
}


