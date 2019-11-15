package client.view.adminView;

import client.controller.Controller;

import client.entity.Worker;

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

public class WorkersView extends JFrame {
    public static WorkersView jframe;
    private JLabel imageLabel;
    private ImageIcon image;
    private JScrollPane scrollPane;

    private JButton addNewWorker, back, delete;
    private JTextField findWorkerField;

    List<Worker> workerList;
    DefaultTableModel tableModel;

    JTable table;


    String[] columnNames = {"Работник"};
    public int idWorker;

    private Controller controller;

    public WorkersView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(470, 350);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Работники(admin)");

        image = (new ImageIcon(("E:\\images\\user1.jpg")));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(240, 20, 190, 178);
        add(imageLabel);


        addNewWorker = new JButton("Добавить");
        add(addNewWorker);
        addNewWorker.setBounds(300,200,100,20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(300, 260, 100, 20);
        delete = new JButton("Удалить");
        add(delete);
        delete.setBounds(300, 230, 100, 20);

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
        watchAllWorkers();


        addNewWorker.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewWorkerView();
            }
        });
        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWorker();
            }
        });

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backToAdminViewFromWorkersView();
            }
        });
    }

    private void watchAllWorkers() {
        tableModel.setRowCount(0);
        workerList = new ArrayList<>();
        workerList = controller.watchAllWorkersForAdmin();
        String[][] array = new String[workerList.size()][8];
        for (int i = 0; i < workerList.size(); i++) {
            array[i][0] =  workerList.get(i).getSurname() + " " + workerList.get(i).getName() + " " + workerList.get(i).getPatronymic();
        }
        for (int i = 0; i < workerList.size(); i++)
            tableModel.addRow(array[i]);
        if (workerList.size() == 0) {
            JOptionPane.showMessageDialog(UsersView.jframe, "Список пуст!");
        }
    }

    private void deleteWorker() {
        int[] selectedRows = table.getSelectedRows();
        for (int selectedRow : selectedRows) {
            String worker = (String) tableModel.getValueAt(selectedRow, 0);
            controller.deleteWorker(worker);
        }
        watchAllWorkers();
    }

    private void addNewWorkerView(){
        controller.openAddWorkerView();
    }

    private void backToAdminViewFromWorkersView(){
        controller.backToAdminViewFromWorkersView();
    }
}

