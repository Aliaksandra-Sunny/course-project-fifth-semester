package client.view.adminView;

import client.controller.Controller;
import client.entity.User;

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

public class UsersView extends JFrame {
    public static UsersView jframe;

    private JLabel imageLabel;
    private ImageIcon image;
    private JScrollPane scrollPane;
    private JButton back, delete;
    private JTextField findUserField;


    List<User> userList;
    DefaultTableModel tableModel;

    JTable table;


    String[] columnNames = {"Пользователь"};

    private Controller controller;

    public UsersView(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setSize(470, 350);
        setBackground(Color.PINK);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Пользователи/Клиенты(admin)");

        image = (new ImageIcon(("E:\\images\\user1.jpg")));
        imageLabel = new JLabel(image);
        imageLabel.setBounds(240, 20, 190, 178);
        add(imageLabel);

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
        scrollPane.setBounds(10, 10, 200, 270);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        watchAllUsers();



        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backToAdminViewFromUsersView();
            }
        });

    }

    private void watchAllUsers() {
        tableModel.setRowCount(0);
        userList = new ArrayList<>();
        userList = controller.watchAllUsersForAdmin();
        String[][] array = new String[userList.size()][8];
        for (int i = 0; i < userList.size(); i++) {
            array[i][0] = userList.get(i).getLogin();
        }
        for (int i = 0; i < userList.size(); i++)
            tableModel.addRow(array[i]);
        if (userList.size() == 0) {
            JOptionPane.showMessageDialog(UsersView.jframe, "Список пуст!");
        }
    }

    private void deleteUser() {
        int[] selectedRows = table.getSelectedRows();
        for (int selectedRow : selectedRows) {
            String user = (String) tableModel.getValueAt(selectedRow, 0);
            controller.deleteUser(user);
        }
        watchAllUsers();
    }

    private void backToAdminViewFromUsersView() {
        controller.backToAdminViewFromUsersView();
    }
}