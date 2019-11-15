package client.view.adminView;

import client.controller.Controller;
import client.entity.Credit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditCreditView extends JFrame {
    public static EditCreditView jframe;

    public int idCredit;

    public JLabel termLabel,percentLabel,sumLabel,creditTypeLabel;

    public JButton editCredit;
    public JButton back;

    public JTextField sumField, termField;

    public JComboBox percentBox, creditTypeBox;

    String[] percentArray = {"1.2","2.1","5.6","8.9","9.6","10.5","11.8","13.4","14.8","16.9"};
    public Controller controller;

    public int getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(int idCredit) {
        this.idCredit = idCredit;
    }

    public EditCreditView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Редактировать информацию о кредите");
        setSize(500, 350);
        setBackground(Color.pink);
        setLayout(null);
        setLocationRelativeTo(null);


        creditTypeLabel = new JLabel("Цель выдачи кредита:");
        creditTypeLabel.setBounds(20, 10, 150, 20);
        add(creditTypeLabel);

        creditTypeBox = new JComboBox(readCreditsType());
        creditTypeBox.setBounds(20, 30, 250, 20);
        add(creditTypeBox);

        sumLabel = new JLabel("Сумма кредита($):");
        sumLabel.setBounds(20, 60, 200, 20);
        add(sumLabel);

        sumField = new JTextField();
        sumField.setBounds(20, 80, 150, 20);
        add(sumField);

        percentLabel = new JLabel("Процентная ставка(%):");
        percentLabel.setBounds(20, 110, 150, 20);
        add(percentLabel);

        percentBox = new JComboBox(percentArray);
        percentBox.setBounds(20, 130, 150, 20);
        add(percentBox);

        termLabel = new JLabel("Срок действия:");
        termLabel.setBounds(20, 160, 150, 20);
        add(termLabel);

        termField= new JTextField();
        termField.setBounds(20, 180, 150, 20);
        add(termField);

        editCredit = new JButton("Изменить");
        editCredit.setBounds(60, 270, 130, 20);
        add(editCredit);

        back = new JButton("Назад");
        back.setBounds(270, 270, 130, 20);
        add(back);

        editCredit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editCredit();
            }
        });

        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToView();
            }
        });
    }


    private String[] readCreditsType() {
        java.util.List<String> creditsType = controller.getAllCreditsType();
        String[] array = new String[creditsType.size()];
        for (int i = 0; i < creditsType.size(); i++) {
            array[i] = creditsType.get(i);
        }
        return array;
    }



    private void editCredit() {
        Credit credit = Credit.newBuilder()
                .creditType((String) creditTypeBox.getSelectedItem())
                .sum((sumField.getText()))
                .percent((String) percentBox.getSelectedItem())
                .term(termField.getText())
                .build();
        controller.editCredit(idCredit,credit);
        controller.backToCreditsAdminViewFromEditCreditView();
    }

    private void backToView() {
        controller.backToCreditsAdminViewFromEditCreditView();
    }
}
