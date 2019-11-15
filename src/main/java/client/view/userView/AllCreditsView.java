package client.view.userView;

import client.controller.Controller;
import client.entity.Client;
import client.entity.Credit;
import client.entity.User;
import client.model.Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AllCreditsView extends JFrame {
    public static AllCreditsView jframe;
    private Client client;
    private Credit credit;

    private JScrollPane scrollPane;
    private JButton back, take, delete;
    private JComboBox columnNamesBox;
    private JTextField poiskTextField;


    List<Credit> creditList;
    DefaultTableModel tableModel;
    JTable table;


    String[] columnNamesUnits = {
            "Цель выдачи кредита",
            "Сумма выдачи($)",
            "Процентная ставка(%)",
            "Срок"
    };

    String[] columnNamesFind = {
            "Цель выдачи кредита",
            "Сумма выдачи",
            "Процентная ставка",
            "Срок"
    };

    private Controller controller;

    public AllCreditsView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    public AllCreditsView(Controller controller, User user) {
        jframe = this;
        this.controller = controller;
        client = new Model().findClient(user);
        initComponents();
    }

    public AllCreditsView(Controller controller, Client client) {
        jframe = this;
        this.controller = controller;
        this.client = client;
        initComponents();
    }

    private void initComponents() {
        setSize(740, 300);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Справочная информация по действующим кредитам");

        take = new JButton("Взять");
        take.setBounds(350, 230, 100, 20);
        add(take);

        back = new JButton("Выход");
        back.setBounds(600, 230, 100, 20);
        add(back);

        columnNamesBox = new JComboBox(columnNamesFind);
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
        tableModel.setColumnIdentifiers(columnNamesUnits);
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

        take.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeCredit();
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
        }
        for (int i = 0; i < creditList.size(); i++)
            tableModel.addRow(array[i]);
    }


    private void takeCredit() {
        int row = table.getSelectedRow();
        creditList = controller.watchAllCredits();
        final Credit credit = Credit.newBuilder()
                .creditType((String) tableModel.getValueAt(row, 0))
                .sum((String) tableModel.getValueAt(row, 1))
                .percent((String) tableModel.getValueAt(row, 2))
                .term((String) tableModel.getValueAt(row, 3))
                .build();

        Credit findCredit = creditList.stream().filter(o -> credit.getCreditType().equals(o.getCreditType()) &&
                credit.getSum().equals(o.getSum()) && credit.getPercent().equals(o.getPercent()) &&
                credit.getTerm().equals(o.getTerm())).findFirst().get();
        controller.takeCredit(client, findCredit);
        watchAllCredits();

    }

    private void backToMainAdminView() {
        controller.backToMainUserViewFromCreditsView(client);
    }
}
