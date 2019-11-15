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

public class SendCreditForAssessmentView extends JFrame {
    public static SendCreditForAssessmentView jframe;

    private JScrollPane scrollPane;
    private JButton back, add, delete;
    private JComboBox columnNamesBox;
    private JTextField poiskTextField;

    List<Credit> creditList;
    DefaultTableModel tableModel;
    JTable table;


    String[] columnNames = {
            "Цель выдачи кредита",
            "Сумма выдачи",
            "Процентная ставка",
            "Срок",
            "На оценке"
    };

    private Controller controller;

    public SendCreditForAssessmentView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(740, 300);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Отправить кредит на оценку");

        add = new JButton("Отправить");
        add.setBounds(350, 230, 100, 20);
        add(add);


        back = new JButton("Выход");
        back.setBounds(600, 230, 100, 20);
        add(back);

        columnNamesBox = new JComboBox(columnNames);
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
        watchAllCredits();


        poiskTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                watchCredit();
            }
        });

        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCredit();
            }
        });


        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainAdminView();
            }
        });

    }

    private void watchAllCredits() {
        tableModel.setRowCount(0);
        creditList = new ArrayList<>();
        creditList = controller.watchAllCredits();
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

    private void watchCredit() {
        String columnName = (String) columnNamesBox.getSelectedItem();
        String string = poiskTextField.getText() + "%";
        tableModel.setRowCount(0);
        creditList = new ArrayList<>();
        creditList = controller.watchCredit(columnName, string);
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


    private void addCredit() {
        int row = table.getSelectedRow();
        Credit credit = Credit.newBuilder()
                .creditType((String) tableModel.getValueAt(row, 0))
                .sum((String) tableModel.getValueAt(row, 1))
                .percent((String) tableModel.getValueAt(row, 2))
                .term((String) tableModel.getValueAt(row, 3))
                .assessment((String) tableModel.getValueAt(row, 4))
                .build();
        controller.sendCreditForAssessment(credit);
        watchAllCredits();
    }

    private void backToMainAdminView() {
        controller.backToCreditsForAssessmentViewFromSendCreditsView();
    }
}
