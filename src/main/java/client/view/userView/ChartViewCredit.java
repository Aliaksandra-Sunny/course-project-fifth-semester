package client.view.userView;

import client.controller.Controller;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class ChartViewCredit extends JFrame {
    public static ChartViewCredit jframe;

    public Controller controller;

    public DefaultCategoryDataset dcd = new DefaultCategoryDataset();

    public ChartViewCredit(Controller controller) {
        jframe = this;
        this.controller = controller;
        initComponets();
    }

    private  void initComponets(){
        setTitle("Диаграмма");
        setSize(700, 500);
        setBackground(Color.BLUE);
        setLayout(null);
        setLocationRelativeTo(null);


        JFreeChart jchart = ChartFactory.createBarChart("Сравнение процентных ставок по кредитам", "Кредиты", "Процентная ставка(%)", dcd, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = jchart.getCategoryPlot();
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        axis.setUpperMargin(0.15);
        plot.setRangeGridlinePaint(Color.black);
        ChartPanel panel = new ChartPanel(jchart);
        panel.setBounds(0, 0, 685, 450);
        add(panel);


    }


}
