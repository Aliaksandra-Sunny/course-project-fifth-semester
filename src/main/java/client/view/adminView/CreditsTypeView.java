package client.view.adminView;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CreditsTypeView extends JFrame{
    public static CreditsTypeView jframe;

    private JScrollPane scrollPane;
    private JButton back, delete, add;
    private JTextField addField;
    private JLabel addLabel;

    List<String> types;
    DefaultTableModel tableModel;
    JTable table;

    String[] columnNames = {"Цель кредита"};

    private Controller controller;

    public CreditsTypeView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(470, 350);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Цели выдачи кредита(admin)");

        back = new JButton("Выход");
        add(back);
        back.setBounds(250, 260, 100, 20);

        delete = new JButton("Удалить");
        add(delete);
        delete.setBounds(250, 200, 100, 20);

        add = new JButton("Добавить");
        add(add);
        add.setBounds(250, 100, 100, 20);

        addLabel = new JLabel("Цель выдачи кредита:");
        addLabel.setBounds(250, 50, 200, 20);
        add(addLabel);

        addField = new JTextField();
        addField.setBounds(250, 70, 150, 20);
        add(addField);

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnNames);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        add(scrollPane);
        scrollPane.setBounds(10, 10, 200, 270);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        watchAllCreditsType();

        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCreditsType();
            }
        });

        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCreditsType();
            }
        });

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backToMainAdminView();
            }
        });

    }

    private void watchAllCreditsType() {
        tableModel.setRowCount(0);
        types = new ArrayList<>();
        types = controller.getAllCreditsType();
        String[][] array = new String[types.size()][8];
        for (int i = 0; i < types.size(); i++) {
            array[i][0] = types.get(i);
        }
        for (int i = 0; i < types.size(); i++) {
            tableModel.addRow(array[i]);
        }
        if (types.size() == 0) {
            JOptionPane.showMessageDialog(CreditsTypeView.jframe, "Список пуст!");
        }
    }

    private void deleteCreditsType() {
        int[] selectedRows = table.getSelectedRows();
        for (int selectedRow : selectedRows) {
            String type = (String) tableModel.getValueAt(selectedRow, 0);
            controller.deleteCreditsType(type);
        }
        watchAllCreditsType();
    }

    private void addCreditsType() {
        String type = addField.getText();
        JOptionPane.showMessageDialog(CreditsTypeView.jframe, controller.addCreditsType(type));
        addField.setText("");
        watchAllCreditsType();
    }


    private void backToMainAdminView() {
        controller.backToAdminViewFromCreditsTypeView();
    }
}

