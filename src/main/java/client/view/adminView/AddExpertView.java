package client.view.adminView;

import client.controller.Controller;
import client.entity.Expert;
import client.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddExpertView extends JFrame {

    public static AddExpertView jframe;
    private JLabel image;
    private JLabel label;

    private JTextField loginField, passwordField, nameField, surnameField;

    private JButton back, addExpert;

    private Controller controller;

    public AddExpertView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    public void initComponents() {
        setSize(470, 350);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Добавить эксперта");

        image = new JLabel(new ImageIcon(getClass().getResource("/client/images/expert.png")));
        image.setBounds(20, 20, 165, 180);
        add(image);

        label = new JLabel("Имя");
        add(label);
        label.setBounds(250, 30, 60, 20);

        nameField  = new JTextField();
        add(nameField);
        nameField.setBounds(250, 50, 150, 20);

        label = new JLabel("Фамилия");
        add(label);
        label.setBounds(250, 70, 60, 20);

        surnameField = new JTextField();
        add(surnameField);
        surnameField.setBounds(250, 90, 150, 20);

        label = new JLabel("Логин");
        add(label);
        label.setBounds(250, 110, 60, 20);

        loginField = new JTextField();
        add(loginField);
        loginField.setBounds(250, 130, 150, 20);

        label = new JLabel("Пароль");
        add(label);
        label.setBounds(250, 150, 60, 20);

        passwordField = new JTextField();
        add(passwordField);
        passwordField.setBounds(250, 170, 150, 20);

        addExpert = new JButton("Добавить");
        add(addExpert);
        addExpert.setBounds(250,210,150,20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(300, 260, 100, 20);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backToAdminViewFromaddExpertViewView();
            }
        });

        addExpert.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpert();
            }
        });
    }

    private void backToAdminViewFromaddExpertViewView() {
        controller.backToAdminViewFromaddExpertsView();
    }

    private void addExpert(){
        User user = User.newBuilder()
                .login(loginField.getText())
                .password(passwordField.getText())
                .role("expert")
                .build();
        Expert expert = Expert.newBuilder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .build();
        controller.regExpert(user,expert);
        controller.backToExpertsViewFromAddExpert();
    }
}
