package client.view.userView;

import client.controller.Controller;
import client.entity.Client;
import client.entity.Credit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AllUsersCreditsView extends JFrame {
    public static AllUsersCreditsView jframe;
    private Client client;
    private Credit credit;
    private List<Credit> credits;

    private JScrollPane scrollPane;
    private JButton back, writeInFileBt, createChartBt;



    List<Credit> creditList;
    DefaultTableModel tableModel;
    JTable table;


    String[] columnNamesUnits = {
            "№",
            "Цель выдачи кредита",
            "Сумма выдачи($)",
            "Процентная ставка(%)",
            "Срок",
            "Дата выдачи"
    };


    private Controller controller;

    public AllUsersCreditsView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }


    public AllUsersCreditsView(Controller controller, Client client) {
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
        setTitle("Справочная информация по моим кредитам");

        writeInFileBt = new JButton("Записать в файл");
        writeInFileBt.setBounds(20, 230, 150, 20);
        add(writeInFileBt);

        createChartBt = new JButton("Построить диаграмму");
        createChartBt.setBounds(190,230,190,20);
        add(createChartBt);

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
        watchAllCreditsForUser();


        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainAdminView();
            }
        });
        writeInFileBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeResultToFile();
            }
        });
        createChartBt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createChart();
            }
        });

    }

    private void createChart() {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(AllUsersCreditsView.jframe, "Список ваших кредитов пуст!");
        } else {
            List<String> list = new ArrayList<>();
            String str;
            for (int i = 0; i < table.getRowCount(); i++) {
                str = (i+1) +";"+tableModel.getValueAt(i, 3);
                list.add(str);
            }
            controller.openCreateChartViewForUser(list);
        }
    }

    private void watchAllCreditsForUser() {
        tableModel.setRowCount(0);
        creditList = new ArrayList<>();
        List<String> datas;
        datas = controller.getDatasByCredit(client);
        creditList = controller.watchAllCreditsForUser(client);

        String[][] array = new String[creditList.size()][8];
        for (int i = 0; i < creditList.size(); i++) {
            array[i][0] = String.valueOf((i+1));
            array[i][1] = creditList.get(i).getCreditType();
            array[i][2] = (creditList.get(i).getSum());
            array[i][3] = (creditList.get(i).getPercent());
            array[i][4] = creditList.get(i).getTerm();
            array[i][5] = datas.get(i);

        }
        for (int i = 0; i < creditList.size(); i++)
            tableModel.addRow(array[i]);
    }

    private void backToMainAdminView() {
        controller.backToMainUserViewFromUsersCreditsView(client);
    }

    private void writeResultToFile() {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(AllUsersCreditsView.jframe, "Список ваших кредитов пуст!");
        } else {
            try (PrintWriter printWriter = new PrintWriter("E:\\БГУИР\\3 курс\\5 семестр\\ПСП\\kurs_project\\src\\main\\java\\client\\record.txt")) {
                printWriter.println("                         Информация по кредитам" + "\n");
                for (int i = 0; i < table.getRowCount(); i++) {
                    printWriter.println("Кредит: " + (i + 1) + "\n");
//                    printWriter.println(client.getPassport() "\n");
                    printWriter.println("Цель выдачи кредита: " + tableModel.getValueAt(i, 1) + "\n");
                    printWriter.println("Сумма выдачи($): " + tableModel.getValueAt(i, 2) + "\n");
                    printWriter.println("Процентная ставка(%): " + tableModel.getValueAt(i, 3) + "\n");
                    printWriter.println("Срок: " + tableModel.getValueAt(i, 4) + "\n");
                    printWriter.println("Дата выдачи: " + tableModel.getValueAt(i, 5) + "\n");
                    printWriter.println();
                }
                JOptionPane.showMessageDialog(AllUsersCreditsView.jframe, "Данные записаны!");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File("E:\\БГУИР\\3 курс\\5 семестр\\ПСП\\kurs_project\\src\\main\\java\\client\\record.txt"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}

