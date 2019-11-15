package client.view.expertView;

import client.controller.Controller;
import client.entity.Credit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public class CreditForAssessmentForExpertView extends JFrame{
    public static CreditForAssessmentForExpertView jframe;

    private JScrollPane scrollPane;
    private JButton addAssessments, back;


    List<Credit> creditList;
    DefaultTableModel tableModel;
    JTable table;


    String[] columnNames = {
            "Цель выдачи кредита",
            "Сумма выдачи($)",
            "Процентная ставка(%)",
            "Срок",
            "На оценке"
    };

    private Controller controller;

    public CreditForAssessmentForExpertView(Controller controller) {
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


        addAssessments = new JButton("Выставить оценки");
        add(addAssessments);
        addAssessments.setBounds(410,230,160,20);

        back = new JButton("Выход");
        back.setBounds(600, 230, 100, 20);
        add(back);



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
        watchAllCreditsForAssessment();

        addAssessments.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddAssessmentView();
            }
        });
        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainExpertView();
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


    private void openAddAssessmentView(){
        controller.openAddAssessmentView();
    }
    private void backToMainExpertView() {
        controller.backToMainExpertViewFromCreditsForAssessmentForExpertView();
    }
}

