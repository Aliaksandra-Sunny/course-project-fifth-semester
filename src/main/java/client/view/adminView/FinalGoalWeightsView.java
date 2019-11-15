package client.view.adminView;

import client.controller.Controller;
import client.entity.Credit;
import client.entity.Expert;
import client.unit.ValueComparator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.*;
import java.util.List;

public class FinalGoalWeightsView extends JFrame {
    public static FinalGoalWeightsView jframe;

    int scaleSize;
    int N;
    double w1, w2, w3, w4, check;

    private JScrollPane scrollPane, scrollPane2;
    private JLabel nameTableLabel, summColumLabel, scaleSizeLabel, nLabel, goalWeightLabel;
    private JTextField firstSummColumn, secondSummColumn, thirdSummColumn, fourthSummColumn, scaleSizeField, nTextField;
    private JLabel checkLabel;
    private JButton writeResultToFileButton, createChartButton;

    private DecimalFormat numberFormat;
    private JFormattedTextField gW1Field, gW2Field, gW3Field, gW4Field, checkField;
    private JButton showResult, back;

    List<Expert> expertList;
    DefaultTableModel tableModel;
    JTable table;

    List<Credit> creditList;
    DefaultTableModel tableModel2;
    JTable table2;


    String[] columnNames = {
            "Эксперт",
            "1-й кредит",
            "2-й кредит",
            "3-й кредит",
            "4-й кредит"
    };

    String[] columnNames2 = {
            "Кредит",
            "Цель выдачи кредита",
            "Сумма выдачи",
            "Процентная ставка",
            "Срок",
            "На оценке"
    };

    private Controller controller;

    public FinalGoalWeightsView(Controller controller) {
        jframe = this;
        this.setBackground(Color.PINK);
        this.setResizable(true);
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Итоговые веса целей");
        setSize(740, 600);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);


        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        numberFormat = new DecimalFormat("0.000", symbols);


        summColumLabel = new JLabel("Суммарная оценка:");
        add(summColumLabel);
        summColumLabel.setBounds(10, 240, 190, 20);

        scaleSizeLabel = new JLabel("Размер шкалы:");
        add(scaleSizeLabel);
        scaleSizeLabel.setBounds(10, 270, 190, 20);

        nLabel = new JLabel("Нормированные оценки:");
        add(nLabel);
        nLabel.setBounds(10, 300, 190, 20);

        goalWeightLabel = new JLabel("Веса целей:");
        add(goalWeightLabel);
        goalWeightLabel.setBounds(10, 330, 190, 20);

        checkLabel = new JLabel("Проверка:");
        add(checkLabel);
        checkLabel.setBounds(10, 360, 190, 20);

        checkField = new JFormattedTextField(numberFormat);
        add(checkField);
        checkField.setBounds(157, 360, 120, 20);

        gW1Field = new JFormattedTextField(numberFormat);
        add(gW1Field);
        gW1Field.setBounds(157, 330, 120, 20);

        gW2Field = new JFormattedTextField(numberFormat);
        add(gW2Field);
        gW2Field.setBounds(295, 330, 120, 20);

        gW3Field = new JFormattedTextField(numberFormat);
        add(gW3Field);
        gW3Field.setBounds(433, 330, 120, 20);

        gW4Field = new JFormattedTextField(numberFormat);
        add(gW4Field);
        gW4Field.setBounds(571, 330, 120, 20);

        nTextField = new JTextField();
        add(nTextField);
        nTextField.setBounds(157, 300, 120, 20);

        scaleSizeField = new JTextField();
        add(scaleSizeField);
        scaleSizeField.setToolTipText("Размер шкалы");
        scaleSizeField.setBounds(157, 270, 120, 20);

        firstSummColumn = new JTextField();
        add(firstSummColumn);
        firstSummColumn.setBounds(157, 240, 120, 20);

        secondSummColumn = new JTextField();
        add(secondSummColumn);
        secondSummColumn.setBounds(295, 240, 120, 20);

        thirdSummColumn = new JTextField();
        add(thirdSummColumn);
        thirdSummColumn.setBounds(433, 240, 120, 20);

        fourthSummColumn = new JTextField();
        add(fourthSummColumn);
        fourthSummColumn.setBounds(571, 240, 120, 20);

        nameTableLabel = new JLabel("Оценки целей экспертами:");
        add(nameTableLabel);
        nameTableLabel.setBounds(10, 10, 300, 20);

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
        scrollPane.setBounds(10, 30, 705, 200);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        watchAllSumAssessments();


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable obj = (JTable) e.getSource();
                    int row = obj.getSelectedRow();
                    String str = (String) tableModel.getValueAt(row, 0);
                    String[] strArr = str.split(" ");
                    String assessments = controller.getAssByNameAndSName(strArr[0], strArr[1]);
                    controller.openAssessmentsOfOneExpertView(assessments);
                }
            }
        });

        resultSum();

        tableModel2 = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        tableModel2.setColumnIdentifiers(columnNames2);
        table2 = new JTable(tableModel2);
        RowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(tableModel2);
        table2.setRowSorter(sorter2);
        scrollPane2 = new JScrollPane(table2);
        add(scrollPane2);
        scrollPane2.setBounds(10, 390, 705, 90);
        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        watchAllCreditsForAssessment();


        scaleSize = controller.getScale();
        N = controller.getN() * (controller.getN() - 1); /**N=N(N-1)*/
        nTextField.setText(String.valueOf(N));
        scaleSizeField.setText(String.valueOf(scaleSize));

        w1 = Double.parseDouble(firstSummColumn.getText())/scaleSize/N;
        w2 = Double.parseDouble(secondSummColumn.getText())/scaleSize/N;
        w3 = Double.parseDouble(thirdSummColumn.getText())/scaleSize/N;
        w4 = Double.parseDouble(fourthSummColumn.getText())/scaleSize/N;

        Double sumW = w1+w2+w3+w4;

        w1/=sumW;
        w2/=sumW;
        w3/=sumW;
        w4/=sumW;


        check = w1 + w2 + w3 + w4;

        gW1Field.setValue(w1);
        gW2Field.setValue(w2);
        gW3Field.setValue(w3);
        gW4Field.setValue(w4);

        checkField.setValue(check);

        showResult = new JButton("Результат");
        add(showResult);
        showResult.setBounds(30, 500, 120, 20);

        writeResultToFileButton = new JButton("Запись в файл");
        add(writeResultToFileButton);
        writeResultToFileButton.setToolTipText("Запись результата в файл");
        writeResultToFileButton.setBounds(180, 500, 150, 20);

        createChartButton = new JButton("Построить диаграмму");
        createChartButton.setToolTipText("Построить диаграмму");
        add(createChartButton);
        createChartButton.setBounds(340, 500, 180, 20);

        back = new JButton("Выход");
        add(back);
        back.setBounds(540, 500, 120, 20);

        showResult.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showResult();
            }
        });
        writeResultToFileButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeResultToFile();
            }
        });
        createChartButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateChartView();
            }
        });
        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });

    }

    public void watchAllSumAssessments() {
        tableModel.setRowCount(0);
        expertList = new ArrayList<>();
        String[] ass;

        expertList = controller.watchAllSummAssessments();


        String[][] array = new String[expertList.size()][5];
        for (int i = 0; i < expertList.size(); i++) {
            array[i][0] = expertList.get(i).getSurname() + " " + expertList.get(i).getName();
            ass = expertList.get(i).getAssessments().split(" ");
            array[i][1] = ass[0];
            array[i][2] = ass[1];
            array[i][3] = ass[2];
            array[i][4] = ass[3];
        }
        for (int i = 0; i < expertList.size(); i++)
            tableModel.addRow(array[i]);
    }

    public void resultSum() {
        expertList = new ArrayList<>();

        String[] ass;
        int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0;

        expertList = controller.watchAllSummAssessments();
        for (int i = 0; i < expertList.size(); i++) {
            ass = expertList.get(i).getAssessments().split(" ");
            sum1 += Integer.parseInt(ass[0]);
            sum2 += Integer.parseInt(ass[1]);
            sum3 += Integer.parseInt(ass[2]);
            sum4 += Integer.parseInt(ass[3]);
        }

        firstSummColumn.setText(String.valueOf(sum1));
        secondSummColumn.setText(String.valueOf(sum2));
        thirdSummColumn.setText(String.valueOf(sum3));
        fourthSummColumn.setText(String.valueOf(sum4));
    }

    public void showResult() {
        String title = "Наиболее предпочтительные варианты";
        JOptionPane.showMessageDialog(MainAdminView.jframe, Result(), title, JOptionPane.INFORMATION_MESSAGE);

    }

    public String Result() {
        w1 = (double) Double.valueOf(numberFormat.format(w1));
        w2 = (double) Double.valueOf(numberFormat.format(w2));
        w3 = (double) Double.valueOf(numberFormat.format(w3));
        w4 = (double) Double.valueOf(numberFormat.format(w4));

        HashMap<String, Double> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Double> sorted_map = new TreeMap<>(bvc);

        map.put("1-й кредит", w1);
        map.put("2-й кредит", w2);
        map.put("3-й кредит", w3);
        map.put("4-й кредит", w4);

        if (w1 == w2 & w2 == w3 & w3 == w4) {
            String answer = "1-й кредит = " + w1 + "\n2-й кредит = " + w2 + "\n3-й кредит = " + w3 + "\n4-й кредит = " + w4;
            return answer;
        } else {
            sorted_map.putAll(map);
            String msg = sorted_map.toString();
            return msg;
        }
    }

    private void watchAllCreditsForAssessment() {
        tableModel2.setRowCount(0);
        creditList = new ArrayList<>();
        creditList = controller.watchAllCreditsForAssessment();
        String[][] array = new String[creditList.size()][8];
        for (int i = 0; i < creditList.size(); i++) {
            array[i][0] = i + 1 + "-й";
            array[i][1] = creditList.get(i).getCreditType();
            array[i][2] = (creditList.get(i).getSum());
            array[i][3] = (creditList.get(i).getPercent());
            array[i][4] = creditList.get(i).getTerm();
            array[i][5] = creditList.get(i).getAssessment();

        }
        for (int i = 0; i < creditList.size(); i++)
            tableModel2.addRow(array[i]);
    }

    private void writeResultToFile() {
        if (table2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(FinalGoalWeightsView.jframe, "Список кредитов на оценке пуст!");
        } else {
            try (PrintWriter printWriter = new PrintWriter("E:\\БГУИР\\3 курс\\5 семестр\\ПСП\\kurs_project\\src\\main\\java\\client\\record.txt")) {
                printWriter.println("                         Информация по кредитам" + "\n");
                for (int i = 0; i < table2.getRowCount(); i++) {
                    printWriter.println("Кредит: " + tableModel2.getValueAt(i, 0) + "\n");
                    printWriter.println("Цель выдачи кредита: " + tableModel2.getValueAt(i, 1) + "\n");
                    printWriter.println("Сумма выдачи($): " + tableModel2.getValueAt(i, 2) + "\n");
                    printWriter.println("Процентная ставка(%): " + tableModel2.getValueAt(i, 3) + "\n");
                    printWriter.println("Срок: " + tableModel2.getValueAt(i, 4) + "\n");
                    printWriter.println();
                }
                printWriter.println("Наиболее предпочтительные варианты:" + "\n");
                printWriter.println(Result() + "\n");
                JOptionPane.showMessageDialog(FinalGoalWeightsView.jframe, "Данные записаны!");

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

    private void openCreateChartView() {
        w1 = (double) Double.valueOf(numberFormat.format(w1));
        w2 = (double) Double.valueOf(numberFormat.format(w2));
        w3 = (double) Double.valueOf(numberFormat.format(w3));
        w4 = (double) Double.valueOf(numberFormat.format(w4));

        List<String> list = new ArrayList<>();
        String w1S, w2S, w3S, w4S;
        if (w2 != 0) {
            w1S = "1-й кредит" + ";" + w1;
            w2S = "2-й кредит" + ";" + w2;
            w3S = "3-й кредит" + ";" + w3;
            w4S = "4-й кредит" + ";" + w4;

            list.add(w1S);
            list.add(w2S);
            list.add(w3S);
            list.add(w4S);
            controller.openCreateChartView(list);
        }else {
            JOptionPane.showMessageDialog(FinalGoalWeightsView.jframe, "Невозможно построить диаграмму!\nУ Вас один кредит на оценке");
        }


    }

    private void back() {
        controller.backToMainAdminViewFromFinalGoalView();
    }
}
