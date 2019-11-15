package client.view.adminView;

import client.controller.Controller;
import client.entity.Expert;

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

public class ExpertsView extends JFrame {

    public static ExpertsView jframe;
    private JLabel imageLabel;
    private ImageIcon image;
    private JScrollPane scrollPane;
    private JButton addNewExpert,back, delete;


    List<Expert> expertList;
    DefaultTableModel tableModel;

    JTable table;


    String[] columnNames = {"Эксперт"};

    private Controller controller;

    public ExpertsView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(470, 350);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Эксперты(admin)");

        image = (new ImageIcon(("E:\\images\\expert.png")));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(240, 20, 185, 180);
        add(imageLabel);

        addNewExpert = new JButton("Добавить");
        add(addNewExpert);
        addNewExpert.setBounds(300,200,100,20);

        delete = new JButton("Удалить");
        add(delete);
        delete.setBounds(300,230,100,20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(300, 260, 100, 20);

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };


        tableModel.setColumnIdentifiers(columnNames);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        add(scrollPane);
        scrollPane.setBounds(10, 10, 220, 270);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        watchAllExperts();

        addNewExpert.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewExpertView();
            }
        });
        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExpert();
            }
        });
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backToAdminViewFromExpertsView();
            }
        });

    }

    private void watchAllExperts() {
        tableModel.setRowCount(0);
        expertList = new ArrayList<>();
        expertList = controller.watchAllExpertsForAdmin();
        String[][] array = new String[expertList.size()][8];
        for (int i = 0; i < expertList.size(); i++) {
            array[i][0] = expertList.get(i).getSurname() + " " + expertList.get(i).getName();
        }
        for (int i = 0; i < expertList.size(); i++)
            tableModel.addRow(array[i]);
        if (expertList.size() == 0) {
            JOptionPane.showMessageDialog(UsersView.jframe, "Список пуст!");
        }
    }

    private void addNewExpertView(){
        controller.openAddExpertView();
    }

    private void deleteExpert() {
        int[] selectedRows = table.getSelectedRows();
        for (int selectedRow : selectedRows) {
            String expert = (String) tableModel.getValueAt(selectedRow, 0);
            controller.deleteExpert(expert);
        }
        watchAllExperts();
    }

    private void backToAdminViewFromExpertsView(){
        controller.backToAdminViewFromExpertsView();
    }

}
