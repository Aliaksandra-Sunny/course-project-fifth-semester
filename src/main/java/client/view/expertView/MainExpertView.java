package client.view.expertView;

import client.controller.Controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainExpertView extends JFrame {
    public static MainExpertView jframe;

    public JLabel imageLabel;
    public ImageIcon image;
    private JButton watchAllCreditsForAssessment, printAllAssessments, back;

    private Controller controller;

    public MainExpertView(Controller controller) {
        jframe = this;
        this.setBackground(Color.PINK);
        this.setResizable(false);
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Эксперт");
        setSize(450, 230);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);

        image = new ImageIcon("E:\\images\\expert.jpg");
        imageLabel = new JLabel(image);
        imageLabel.setBounds(10, 20, 150, 160);
        add(imageLabel);

        watchAllCreditsForAssessment = new JButton("Все кредиты на оценке");
        add(watchAllCreditsForAssessment);
        watchAllCreditsForAssessment.setBounds(200, 50, 200, 20);

        printAllAssessments = new JButton("Оценки по всем кредитам");
        add(printAllAssessments);
        printAllAssessments.setBounds(200, 80, 200, 20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(300, 150, 100, 20);


        watchAllCreditsForAssessment.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                watchAllCreditsForAssessment();
            }
        });

        printAllAssessments.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printAllAssessments();
            }
        });
        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToAuthorization();
            }
        });
    }


    private void watchAllCreditsForAssessment() {
        controller.watchAllCreditsForAssessmentForExpertView();
    }

    private void printAllAssessments() {
        controller.openPrintAllAssessmentsForExpertView();
    }

    private void backToAuthorization() {
        controller.backToAuthorizationViewFromMainExpertView();
    }
}
