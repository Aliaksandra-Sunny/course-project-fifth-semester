package client.view.adminView;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AssessmentsOfOneExpertView extends JFrame {
    public static AssessmentsOfOneExpertView jframe;
    public JTextField z1z1, z1z2, z1z3, z1z4, z2z1, z2z2, z2z3, z2z4, z3z1, z3z2, z3z3, z3z4, z4z1, z4z2, z4z3, z4z4;
    public JLabel z1Label, z2Label, z3Label, z4Label;


    private Controller controller;

    public AssessmentsOfOneExpertView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Оценки эксперта");
        setSize(290, 250);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);



        //1 colum
        z1Label = new JLabel("Z1");
        add(z1Label);
        z1Label.setBounds(48, 15, 20, 20);

        z1z1 = new JTextField();
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

        z1z2 = new JTextField();
        add(z1z2);
        z1z2.setBounds(90, 45, 30, 20);

        z2z2 = new JTextField();
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

        z1z3 = new JTextField();
        add(z1z3);
        z1z3.setBounds(140, 45, 30, 20);

        z2z3 = new JTextField();
        add(z2z3);
        z2z3.setBounds(140, 85, 30, 20);

        z3z3 = new JTextField();
        add(z3z3);
        z3z3.setBounds(140, 125, 30, 20);

        z4z3 = new JTextField();
        add(z4z3);
        z4z3.setBounds(140, 165, 30, 20);

        //4 colum
        z4Label = new JLabel("Z4");
        add(z4Label);
        z4Label.setBounds(198, 15, 20, 20);

        z1z4 = new JTextField();
        add(z1z4);
        z1z4.setBounds(190, 45, 30, 20);

        z2z4 = new JTextField();
        add(z2z4);
        z2z4.setBounds(190, 85, 30, 20);

        z3z4 = new JTextField();
        add(z3z4);
        z3z4.setBounds(190, 125, 30, 20);

        z4z4 = new JTextField();
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


    }


}

