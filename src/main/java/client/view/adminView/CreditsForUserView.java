package client.view.adminView;

import client.controller.Controller;
import client.entity.Client;
import client.entity.Credit;
import client.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreditsForUserView extends JFrame {
    public static CreditsForUserView jframe;


    private JScrollPane scrollPane;
    private JButton back, delete, shaw;
    private JComboBox columnNamesBox;
    private JTextField poiskTextField;

    UserCredit userCredit;
    List<Credit> creditList = new ArrayList<>();
    List<Client> clientList = new ArrayList<>();
    List<String> datas = new ArrayList<>();
    List<Integer> idClients = new ArrayList<>();
    List<Integer> idCredits = new ArrayList<>();
    List<UserCredit> userCredits = new ArrayList<>();
    List<UserCredit> finds = new ArrayList<>();

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
            "Имя",
            "Фамилия",
            "Паспорт",
            "Цель выдачи кредита",
            "Сумма выдачи($)",
            "Процентная ставка(%)",
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

        shaw = new JButton("Показать");
        add(shaw);
        shaw.setBounds(350, 230, 100,20 );

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
        shaw.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                watchAllCredits();
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
        creditList.clear();
        clientList.clear();
        creditList = controller.watchAllCreditsForUsers();
        clientList = controller.watchAllClientWithCredit();
        datas = controller.getDatasByCreditAllClients();
        userCredits.clear();
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


            userCredit = new UserCredit();
            userCredit.setFirstName(array[i][0]);
            userCredit.setSurName(array[i][1]);
            userCredit.setPassport(array[i][2]);
            userCredit.setTypeCredit(array[i][3]);
            userCredit.setSum(array[i][4]);
            userCredit.setPercent(array[i][5]);
            userCredit.setTerm(array[i][6]);
            userCredit.setData(datas.get(i));
            userCredit.setIdCredit(creditList.get(i).getIdCredit());
            userCredit.setIdClient(clientList.get(i).getIdClient());
            userCredits.add(userCredit);

        }
        for (int i = 0; i < creditList.size(); i++)
            tableModel.addRow(array[i]);
    }

    private void watchCredit() {
        String columnName = (String) columnNamesBox.getSelectedItem();
        String find = poiskTextField.getText();

        finds.clear();
        switch (columnName) {
            case "Имя":
                for (UserCredit credit : userCredits) {
                    if (credit.getFirstName().equals(find)) {
                        finds.add(credit);
                    }
                }
                break;
            case "Фамилия":
                userCredits.stream().filter(userCredit -> userCredit.getSurName().equals(find)).forEach(finds::add);
                break;
            case "Паспорт":
                userCredits.stream().filter(userCredit -> userCredit.getPassport().equals(find)).forEach(finds::add);
                break;
            case "Цель выдачи кредита":
                userCredits.stream().filter(userCredit -> userCredit.getTypeCredit().equals(find)).forEach(finds::add);
                break;
            case "Сумма выдачи($)":
                userCredits.stream().filter(userCredit -> userCredit.getSum().equals(find)).forEach(finds::add);
                break;
            case "Процентная ставка(%)":
                userCredits.stream().filter(userCredit -> userCredit.getPercent().equals(find)).forEach(finds::add);
                break;
            case "Срок":
                userCredits.stream().filter(userCredit -> userCredit.getTerm().equals(find)).forEach(finds::add);
                break;

        }

        idCredits.clear();
        idClients.clear();
        tableModel.setRowCount(0);
        String[][] array = new String[finds.size()][8];
        for (int i = 0; i < finds.size(); i++) {
            array[i][0] = finds.get(i).getFirstName();
            array[i][1] = finds.get(i).getSurName();
            array[i][2] = finds.get(i).getPassport();
            array[i][3] = finds.get(i).getTypeCredit();
            array[i][4] = (finds.get(i).getSum());
            array[i][5] = (finds.get(i).getPercent());
            array[i][6] = finds.get(i).getTerm();
            array[i][7] = finds.get(i).getData();
            idClients.add(finds.get(i).getIdClient());
            idCredits.add(finds.get(i).getIdCredit());
        }
        for (int i = 0; i < finds.size(); i++)
            tableModel.addRow(array[i]);


    }

    private void deleteCredit() {
        int row = table.getSelectedRow();
        Client client = Client.newBuilder()
                .idClient(idClients.get(row))
                .name((String) tableModel.getValueAt(row, 0))
                .surname((String) tableModel.getValueAt(row, 1))
                .passport((String) tableModel.getValueAt(row, 2))
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

