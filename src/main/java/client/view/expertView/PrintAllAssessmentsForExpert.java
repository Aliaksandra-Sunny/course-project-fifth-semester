package client.view.expertView;

import client.controller.Controller;
import client.entity.Expert;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.util.List;

public class PrintAllAssessmentsForExpert extends JFrame {
    public static PrintAllAssessmentsForExpert jframe ;

    private JScrollPane scrollPane;
    private JButton back;
    private JComboBox columnNamesBox;
    private JTextField poiskTextField;


    List<Expert> expertList;
    DefaultTableModel tableModel;
    JTable table;


    String[] columnNames = {
            "Фамилия",
            "Имя",
            "Логин",
            "Оценки"
    };
    String[] poiskNames = {
            "Фамилия",
            "Имя",
            "Логин"
    };

    private Controller controller;

    public PrintAllAssessmentsForExpert(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(740, 300);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Оценки по кредитам");



        back = new JButton("Выход");
        back.setBounds(600, 230, 100, 20);
        add(back);

        columnNamesBox = new JComboBox(poiskNames);
        columnNamesBox.setBounds(20, 230, 150, 20);
        add(columnNamesBox);

        poiskTextField = new JTextField();
        poiskTextField.setBounds(200, 230, 100, 20);
        add(poiskTextField);

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        tableModel.setColumnIdentifiers(columnNames);
        table = new JTable(tableModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(sorter);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
        scrollPane.setBounds(10, 10, 705, 200);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        poiskTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                watchExpert();
            }
        });


        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainExpertView();
            }
        });

    }


    private void watchExpert() {
        String columnName = (String) columnNamesBox.getSelectedItem();
        String string = poiskTextField.getText() + "%";
        tableModel.setRowCount(0);
        expertList = new ArrayList<>();
        expertList = controller.watchExpert(columnName, string);
        String[][] array = new String[expertList.size()][8];
        for (int i = 0; i < expertList.size(); i++) {
            array[i][0] = expertList.get(i).getSurname();
            array[i][1] = (expertList.get(i).getName());
            array[i][2] = (expertList.get(i).getLogin());
            array[i][3] = expertList.get(i).getAssessments();
        }
        for (int i = 0; i < expertList.size(); i++)
            tableModel.addRow(array[i]);
    }


    private void backToMainExpertView() {
        controller.backToMainExpertViewFromAssessmentsView();
    }
}
