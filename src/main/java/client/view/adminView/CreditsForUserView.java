package client.view.adminView;

import client.controller.Controller;
import client.entity.Client;
import client.entity.Credit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CreditsForUserView extends JFrame {
    public static CreditsForUserView jframe;


    private JScrollPane scrollPane;
    private JButton back, add, delete;
    private JComboBox columnNamesBox;
    private JTextField poiskTextField;


    List<Credit> creditList;
    List<Client> clientList;
    List<String> datas;
    List<Integer> idClients, idCredits;

    DefaultTableModel tableModel;
    JTable table;


    String[] columnNamesUnits = {
            "Имя",
            "Фамилия",
            "Паспорт",
            "Цель выдачи кредита",
            "Сумма выдачи($)",
            "Процентная ставка(%)",
            "Срок",
            "Дата выдачи"
    };

    String[] columnNamesFind = {
            "Цель выдачи кредита",
            "Сумма выдачи",
            "Процентная ставка",
            "Срок"
    };

    private Controller controller;

    public CreditsForUserView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(1000, 300);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Выданные кредиты");


        delete = new JButton("Удалить");
        delete.setBounds(475, 230, 100, 20);
        add(delete);

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
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
        scrollPane.setBounds(10, 10, 965, 200);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        watchAllCredits();

        poiskTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                watchCredit();
            }
        });

        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCredit();
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
        clientList = new ArrayList<>();
        datas = new ArrayList<>();
        idClients = new ArrayList<>();
        idCredits = new ArrayList<>();

        creditList = controller.watchAllCreditsForUsers();
        clientList = controller.watchAllClientWithCredit();
        datas = controller.getDatasByCreditAllClients();
        String[][] array = new String[creditList.size()][8];
        for (int i = 0; i < creditList.size(); i++) {
            array[i][0] = clientList.get(i).getName();
            array[i][1] = clientList.get(i).getSurname();
            array[i][2] = clientList.get(i).getPassport();
            array[i][3] = creditList.get(i).getCreditType();
            array[i][4] = (creditList.get(i).getSum());
            array[i][5] = (creditList.get(i).getPercent());
            array[i][6] = creditList.get(i).getTerm();
            array[i][7] = datas.get(i);
            idClients.add(clientList.get(i).getIdClient());
            idCredits.add(creditList.get(i).getIdCredit());
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

    private void deleteCredit() {
        int row = table.getSelectedRow();
        Client client = Client.newBuilder()
                .idClient(idClients.get(row))
                .name((String) tableModel.getValueAt(row,0))
                .surname((String) tableModel.getValueAt(row,1))
                .passport((String) tableModel.getValueAt(row,2))
                .build();

        Credit credit = Credit.newBuilder()
                .idCredit(idCredits.get(row))
                .creditType((String) tableModel.getValueAt(row, 3))
                .sum((String) tableModel.getValueAt(row, 4))
                .percent((String) tableModel.getValueAt(row, 5))
                .term((String) tableModel.getValueAt(row, 6))
                .build();
        String data = datas.get(row);

        controller.deleteCreditForUser(client, credit, data);
        watchAllCredits();
    }

    private void backToMainAdminView() {
        controller.backToMainAdminViewFromUserCreditsView();
    }
}

