package client.view.adminView;

import client.controller.Controller;
import client.entity.Credit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CreditsForAssessmentView extends JFrame {
    public static CreditsForAssessmentView jframe;

    private JScrollPane scrollPane;
    private JButton back, add, delete;


    List<Credit> creditList;
    DefaultTableModel tableModel;
    JTable table;


    String[] columnNamesUnits = {
            "Цель выдачи кредита",
            "Сумма выдачи ($)",
            "Процентная ставка (%)",
            "Срок",
            "На оценке"
    };



    private Controller controller;

    public CreditsForAssessmentView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(740, 300);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Кредиты на оценивании");

        add = new JButton("Добавить");
        add.setBounds(350, 230, 100, 20);
        add(add);

        delete = new JButton("Удалить");
        delete.setBounds(475, 230, 100, 20);
        add(delete);

        back = new JButton("Выход");
        back.setBounds(600, 230, 100, 20);
        add(back);



        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnNamesUnits);
        table = new JTable(tableModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(sorter);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
        scrollPane.setBounds(10, 10, 705, 200);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        watchAllCreditsForAssessment();




        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCreditForAssessment();
            }
        });

        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCreditForAssessment();
            }
        });


        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainAdminView();
            }
        });

    }

    private void watchAllCreditsForAssessment() {
        tableModel.setRowCount(0);
        creditList = new ArrayList<>();
        creditList = controller.watchAllCreditsForAssessment();
        String[][] array = new String[creditList.size()][8];
        for (int i = 0; i < creditList.size(); i++) {
            array[i][0] = creditList.get(i).getCreditType();
            array[i][1] = (creditList.get(i).getSum());
            array[i][2] = (creditList.get(i).getPercent());
            array[i][3] = creditList.get(i).getTerm();
            array[i][4] = creditList.get(i).getAssessment();

        }
        for (int i = 0; i < creditList.size(); i++)
            tableModel.addRow(array[i]);
    }


    private void deleteCreditForAssessment() {
        int row = table.getSelectedRow();
        Credit credit = Credit.newBuilder()
                .creditType((String) tableModel.getValueAt(row, 0))
                .sum((String) tableModel.getValueAt(row, 1))
                .percent((String ) tableModel.getValueAt(row, 2))
                .term((String) tableModel.getValueAt(row, 3))
                .assessment((String)tableModel.getValueAt(row,4))
                .build();
        controller.deleteCreditForAssessment(credit);
        watchAllCreditsForAssessment();
    }

    private void addCreditForAssessment() {
        controller.openSendCreditForAssessmentView();
    }

    private void backToMainAdminView() {
        controller.backToMainAdminViewFromCreditsForAssessmentView();
    }
}

