package client.view.expertView;

import client.controller.Controller;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AddAssessmentView extends JFrame {
    public static AddAssessmentView jframe;

    private JTextField z1z1, z2z1, z2z2, z3z1, z3z2, z3z3, z4z1, z4z2, z4z3, z4z4;
    private JLabel z1Label, z2Label, z3Label, z4Label;
    private JComboBox z1z2CB, z1z3CB, z1z4CB, z2z3CB, z2z4CB, z3z4CB;
    private JButton addButton, fillButton;

    private JLabel loginLabel;
    private JTextField loginField;

    private int[] assessment;
    private int scale;
    private String[] assForCB;


    private Controller controller;

    public AddAssessmentView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Оценки эксперта");
        setSize(400, 250);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);

        scale = controller.getScale();
        assessment = controller.getAssessments();
        assForCB = new String[scale + 1];

        //assessment for ComBox
        for (int i = 0; i < assessment.length; i++) {
            assForCB[i] = String.valueOf(assessment[i]);
        }
        assForCB[assForCB.length - 1] = "0";

        //1 colum
        z1Label = new JLabel("Z1");
        add(z1Label);
        z1Label.setBounds(48, 15, 20, 20);

        z1z1 = new JTextField("   0");
        add(z1z1);
        z1z1.setBounds(40, 45, 30, 20);

        z2z1 = new JTextField();
        add(z2z1);
        z2z1.setBounds(40, 85, 30, 20);

        z3z1 = new JTextField();
        add(z3z1);
        z3z1.setBounds(40, 125, 30, 20);

        z4z1 = new JTextField();
        add(z4z1);
        z4z1.setBounds(40, 165, 30, 20);

        //2 colum
        z2Label = new JLabel("Z2");
        add(z2Label);
        z2Label.setBounds(98, 15, 20, 20);

        z1z2CB = new JComboBox(assForCB);
        z1z2CB.setBounds(85, 45, 40, 20);
        add(z1z2CB);

        z2z2 = new JTextField("   0");
        add(z2z2);
        z2z2.setBounds(90, 85, 30, 20);

        z3z2 = new JTextField();
        add(z3z2);
        z3z2.setBounds(90, 125, 30, 20);

        z4z2 = new JTextField();
        add(z4z2);
        z4z2.setBounds(90, 165, 30, 20);

        //3colum
        z3Label = new JLabel("Z3");
        add(z3Label);
        z3Label.setBounds(148, 15, 20, 20);

        z1z3CB = new JComboBox(assForCB);
        z1z3CB.setBounds(135, 45, 40, 20);
        add(z1z3CB);

        z2z3CB = new JComboBox(assForCB);
        z2z3CB.setBounds(135, 85, 40, 20);
        add(z2z3CB);

        z3z3 = new JTextField("   0");
        add(z3z3);
        z3z3.setBounds(140, 125, 30, 20);

        z4z3 = new JTextField();
        add(z4z3);
        z4z3.setBounds(140, 165, 30, 20);

        //4 colum
        z4Label = new JLabel("Z4");
        add(z4Label);
        z4Label.setBounds(198, 15, 20, 20);

        z1z4CB = new JComboBox(assForCB);
        z1z4CB.setBounds(185, 45, 40, 20);
        add(z1z4CB);

        z2z4CB = new JComboBox(assForCB);
        z2z4CB.setBounds(185, 85, 40, 20);
        add(z2z4CB);

        z3z4CB = new JComboBox(assForCB);
        z3z4CB.setBounds(185, 125, 40, 20);
        add(z3z4CB);

        z4z4 = new JTextField("   0");
        add(z4z4);
        z4z4.setBounds(190, 165, 30, 20);

        //1 line
        z1Label = new JLabel("Z1");
        add(z1Label);
        z1Label.setBounds(10, 45, 20, 20);

        //2 line
        z2Label = new JLabel("Z2");
        add(z2Label);
        z2Label.setBounds(10, 85, 20, 20);

        // 3 line
        z3Label = new JLabel("Z3");
        add(z3Label);
        z3Label.setBounds(10, 125, 20, 20);

        //4 line
        z4Label = new JLabel("Z4");
        add(z4Label);
        z4Label.setBounds(10, 165, 20, 20);

        fillButton = new JButton("Заполнить");
        add(fillButton);
        fillButton.setBounds(245, 45, 120, 20);

        addButton = new JButton("Добавить");
        add(addButton);
        addButton.setBounds(245, 85, 120, 20);

        loginLabel = new JLabel("Логин:");
        add(loginLabel);
        loginLabel.setBounds(245, 140, 100, 20);

        loginField = new JTextField();
        loginField.setBounds(245, 165, 120, 20);
        loginField.setToolTipText("Ваш логин");
        add(loginField);



        fillButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillCells();
            }
        });

        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAssessments();
            }
        });

    }

    private void fillCells() {
        scale = controller.getScale();
        String valString;
        int valInt;

        if (z1z2CB.getSelectedItem() != "0") {
            valString = (String) z1z2CB.getSelectedItem();
            valInt = Integer.parseInt(valString);
            valInt = scale - valInt;
            z2z1.setText( String.valueOf(valInt));
        } else {
            z2z1.setText("0");
        }

        if (z1z3CB.getSelectedItem() != "0") {
            valString = (String) z1z3CB.getSelectedItem();
            valInt = Integer.parseInt(valString);
            valInt = scale - valInt;
            z3z1.setText( String.valueOf(valInt));
        } else {
            z3z1.setText("0");
        }

        if (z1z4CB.getSelectedItem() != "0") {
            valString = (String) z1z4CB.getSelectedItem();
            valInt = Integer.parseInt(valString);
            valInt = scale - valInt;
            z4z1.setText(String.valueOf(valInt));
        } else {
            z4z1.setText("0");
        }

        if (z2z4CB.getSelectedItem() != "0") {
            valString = (String) z2z4CB.getSelectedItem();
            valInt = Integer.parseInt(valString);
            valInt = scale - valInt;
            z4z2.setText( String.valueOf(valInt));
        } else {
            z4z2.setText("0");
        }

        if (z3z4CB.getSelectedItem() != "0") {
            valString = (String) z3z4CB.getSelectedItem();
            valInt = Integer.parseInt(valString);
            valInt = scale - valInt;
            z4z3.setText(String.valueOf(valInt));
        } else {
            z4z3.setText("0");
        }

        if (z2z3CB.getSelectedItem() != "0") {
            valString = (String) z2z3CB.getSelectedItem();
            valInt = Integer.parseInt(valString);
            valInt = scale - valInt;
            z3z2.setText( String.valueOf(valInt));
        } else {
            z3z2.setText("0");
        }


    }

    private void addAssessments() {
        String login;
        String expertAssessments = "";

        expertAssessments += z1z1.getText()+ " ";
        expertAssessments += (String) z1z2CB.getSelectedItem()+ " ";
        expertAssessments += (String) z1z3CB.getSelectedItem()+ " ";
        expertAssessments += (String) z1z4CB.getSelectedItem()+ " ";

        expertAssessments += z2z1.getText()+ " ";
        expertAssessments += z2z2.getText()+ " ";
        expertAssessments += (String) z2z3CB.getSelectedItem()+ " ";
        expertAssessments += (String) z2z4CB.getSelectedItem()+ " ";

        expertAssessments += z3z1.getText()+ " ";
        expertAssessments += z3z2.getText()+ " ";
        expertAssessments += z3z3.getText()+ " ";
        expertAssessments += (String) z3z4CB.getSelectedItem()+ " ";

        expertAssessments += z4z1.getText()+ " ";
        expertAssessments += z4z2.getText()+ " ";
        expertAssessments += z4z3.getText()+ " ";
        expertAssessments += z4z4.getText()+ " ";

        login = loginField.getText();

        controller.addAssessments(login,expertAssessments);

        loginField.setText("");
    }
}
