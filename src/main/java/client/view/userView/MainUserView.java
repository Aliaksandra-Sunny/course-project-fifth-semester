package client.view.userView;

import client.controller.Controller;
import client.entity.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainUserView extends JFrame {
    public static MainUserView jframe;

    User user = new User();
    private JButton showMyCreditBt, addCreditBt, editOwnInfoBt;
    private JButton exitBt;

    private Controller controller;

    public MainUserView(Controller controller) {
        jframe = this;
        this.setBackground(Color.PINK);
        this.setResizable(false);
        this.controller = controller;
        initComponents();
    }

    public MainUserView(Controller controller, User user) {
        jframe = this;
        this.setBackground(Color.PINK);
        this.setResizable(false);
        this.controller = controller;
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Клиент");
        setSize(430, 180);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);

        addCreditBt = new JButton("Взять кредит");
        addCreditBt.setBounds(20,20,150,20);
        add(addCreditBt);

        editOwnInfoBt = new JButton("Именить личные данные");
        editOwnInfoBt.setBounds(190,20,220,20);
        add(editOwnInfoBt);

        showMyCreditBt = new JButton("Мои кредиты");
        showMyCreditBt.setBounds(20,50,150,20);
        add(showMyCreditBt);

        exitBt = new JButton("Выход");
        exitBt.setBounds(260,90,100,20);
        add(exitBt);


        editOwnInfoBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditInfoView();
            }
        });
        addCreditBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAllCreditsView();
            }
        });
        showMyCreditBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyCredit();
            }
        });

        exitBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToAuthorization();
            }
        });

    }

    private void showMyCredit() {
        controller.showMyCredit(user);
    }

    private void openAllCreditsView() {
        controller.openAllCreditsView(user);
    }

    private void openEditInfoView() {
        controller.openEditUserInfoView(user);

    }

    private void backToAuthorization() {
        controller.backToAuthorizationViewFromMainUserView();
    }

}
