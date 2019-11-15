package client.view.adminView;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EnterScaleSizeView extends JFrame {
    public static EnterScaleSizeView jframe;

    private JButton enter;
    private JTextField field;
    private JLabel label;
    private JComboBox scaleBox;
    private Integer scale[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,52};

    private Controller controller;

    public EnterScaleSizeView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(300, 100);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Шкала");

        label = new JLabel("Размер < 51");
        add(label);
        label.setBounds(27, 15, 150, 20);


        scaleBox = new JComboBox(scale);
        scaleBox.setBounds(110, 15, 50, 20);
        add(scaleBox);

        enter = new JButton("ОК");
        add(enter);
        enter.setBounds(190, 15, 60, 20);

        enter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterScaleSize();
            }
        });

    }

    private void enterScaleSize() {
        String size = String.valueOf(scaleBox.getSelectedItem());
        controller.enterScaleSize(size);
        controller.closedEnterScaleSizeView();
    }


}
