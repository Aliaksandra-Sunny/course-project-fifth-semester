package client.view.adminView;

import client.controller.Controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainAdminView extends JFrame {
    public static MainAdminView jframe;
    private JLabel image;

    private JButton enterScaleSize,printAllAssessments,printAllUsers, printAllExperts, printAllWorkers, viewTotalGoalWeight, printAllCredits, addCreditType, printCrSendForAss;
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
        setSize(480, 300);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);


        printAllUsers = new JButton("Просмотреть всех клиентов");
        add(printAllUsers);
        printAllUsers.setBounds(10, 40, 220, 20);

        enterScaleSize = new JButton("Ввести размер шкалы");
        add(enterScaleSize);
        enterScaleSize.setBounds(240, 130, 220, 20);;

        printCrSendForAss = new JButton("Кредиты на оценивании");
        add(printCrSendForAss);
        printCrSendForAss.setBounds(240, 40, 220, 20);

        printAllWorkers = new JButton("Посмотреть всех работников");
        add(printAllWorkers);
        printAllWorkers.setBounds(10,70,220,20);

        printAllExperts = new JButton("Посмотреть экспертов ");
        add(printAllExperts);
        printAllExperts.setBounds(10,100,220,20);;

        printAllAssessments = new JButton("Оценки экспертов ");
        add(printAllAssessments);
        printAllAssessments.setBounds(10,130,220,20);

        viewTotalGoalWeight =new JButton("Просмотр итоговых весов целей");
        add(viewTotalGoalWeight);
        viewTotalGoalWeight.setToolTipText("Просмотр итоговых весов всех целей");
        viewTotalGoalWeight.setBounds(240,70,220,20);

        printAllCredits = new JButton("Список действующих кредитов");
        add(printAllCredits);
        printAllCredits.setBounds(240,100,220,20);

        addCreditType = new JButton("Цели выдачи кредита");
        add(addCreditType);
        addCreditType.setBounds(10,160,220,20);

        shawAllCreditsForUserBt = new JButton("Список выданных кредитов");
        add(shawAllCreditsForUserBt);
        shawAllCreditsForUserBt.setBounds(240,160,220,20);


        back = new JButton("Выход");
        add(back);
        back.setBounds(320, 240, 100, 20);


        printAllUsers.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutUsers();
            }
        });
        printAllWorkers.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutWorkers();
            }
        });
        printAllExperts.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutExsperts();
            }
        });
        printAllCredits.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutCredits();
            }
        });
        printCrSendForAss.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutCrForAss();
            }
        });
        printAllAssessments.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readDataAboutAssessments();
            }
        });
        enterScaleSize.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterScaleSize();
            }
        });
        viewTotalGoalWeight.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTotalGoalWeight();
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

    private void readDataAboutWorkers() {
        controller.openAdminWorkerView();
    }

    private void readDataAboutExsperts() {
        controller.openAdminExpertView();
    }

    private void readDataAboutAssessments() {
        controller.openPrintAllAssessmentsForAdminView();
    }



    private void readDataAboutCreditsType(){
        controller.watchAllCreditsType();
    }

    private void readDataAboutCredits(){controller.watchAllCreditsView();}

    private void readDataAboutCrForAss(){controller.watchAllCreditsForAssessmentView();}

    private void enterScaleSize(){
        controller.openEnterScaleSizeView();
    }

    private void viewTotalGoalWeight(){
        controller.openFinalGoalWeightsView();
    }
    private void backToAuthorization() {
        controller.backToAuthorizationViewFromMainAdminView();
    }


}
