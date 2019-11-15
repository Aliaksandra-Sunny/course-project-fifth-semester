package client.view.adminView;

import client.controller.Controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainAdminView extends JFrame {
    public static MainAdminView jframe;

    private JButton printAllUsers, printAllCredits, addCreditType;
    private JButton  back, shawAllCreditsForUserBt;

    private Controller controller;

    public MainAdminView(Controller controller) {
        jframe = this;
        this.setBackground(Color.PINK);
        this.setResizable(false);
        this.controller = controller;
        //windowClose();
        initComponents();
    }

    private void initComponents() {
        setTitle("Администратор");
        setSize(480, 190);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);


        printAllUsers = new JButton("Просмотреть всех клиентов");
        add(printAllUsers);
        printAllUsers.setBounds(10, 40, 220, 20);

        printAllCredits = new JButton("Список действующих кредитов");
        add(printAllCredits);
        printAllCredits.setBounds(240,40,220,20);

        addCreditType = new JButton("Цели выдачи кредита");
        add(addCreditType);
        addCreditType.setBounds(10,70,220,20);

        shawAllCreditsForUserBt = new JButton("Список выданных кредитов");
        add(shawAllCreditsForUserBt);
        shawAllCreditsForUserBt.setBounds(240,70,220,20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(320, 110, 100, 20);


        printAllUsers.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutUsers();
            }
        });

        printAllCredits.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutCredits();
            }
        });

        shawAllCreditsForUserBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shawAllCreditsForUser();
            }
        });

        addCreditType.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutCreditsType();
            }
        });
        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToAuthorization();
            }
        });

    }

    private void shawAllCreditsForUser() {
        controller.openCreditsForUserView();
    }

    private void readDataAboutUsers() {
        controller.openAdminUserView();
    }


    private void readDataAboutCreditsType(){
        controller.watchAllCreditsType();
    }

    private void readDataAboutCredits(){controller.watchAllCreditsView();}


    private void backToAuthorization() {
        controller.backToAuthorizationViewFromMainAdminView();
    }


}
