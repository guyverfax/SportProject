package controller.report;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import dao.PorderDao;
import dao.ProductDao;
import dao.impl.PorderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Product;
import util.Tool;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ProductChart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ProductDao productDao = new ProductDaoImpl();
	PorderDao porderDao = new PorderDaoImpl();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductChart frame = new ProductChart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductChart() {
		setTitle("產品銷售統計圖");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 建立 Panel 容納圖表
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(2, 1)); // 設定 2 列 1 欄

        // 生成長條圖和圓餅圖
        JFreeChart barChart = createBarChart();
        JFreeChart pieChart = createPieChart();

        // 將圖表加入 ChartPanel
        ChartPanel barChartPanel = new ChartPanel(barChart);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);

        // 加入 JPanel
        chartPanel.add(barChartPanel);
        chartPanel.add(pieChartPanel);

        // 設定 JFrame 內容
        getContentPane().add(chartPanel);
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        
        JButton btnBack = new JButton("按此回主頁");
        btnBack.setForeground(Color.RED);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(ProductChart.this);
        	}
        });
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 30));
        panel.add(btnBack);
    }
	
	// 產生長條圖
    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> productSales = porderDao.readAmountByProduct();

        for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
        	String productName=productDao.readByProductno(entry.getKey()).getProductname();
            dataset.addValue(entry.getValue(), "銷售數量", entry.getKey()+"-"+productName);
        }
        
        JFreeChart barChart = ChartFactory.createBarChart(
                "產品銷售統計", "產品編號", "銷售數量",
                dataset, PlotOrientation.VERTICAL, true, true, false
        );
        
        // 設定中文字型
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        Font font = new Font("Microsoft JhengHei", Font.PLAIN, 9); // 設定為微軟正黑體
        barChart.getTitle().setFont(font);  // 圖表標題字型
        plot.getDomainAxis().setLabelFont(font); // X 軸標籤
        plot.getDomainAxis().setTickLabelFont(font); // X 軸刻度
        plot.getRangeAxis().setLabelFont(font); // Y 軸標籤
        plot.getRangeAxis().setTickLabelFont(font); // Y 軸刻度
        renderer.setDefaultItemLabelFont(font); // 數據標籤字型

        // 設定數據標籤
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true); // 顯示數值
        renderer.setDefaultItemLabelFont(font);
        
        return barChart;
        
    }
    
    // 產生圓餅圖
    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> productSales = porderDao.readAmountByProduct();

        for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
        	String productName=productDao.readByProductno(entry.getKey()).getProductname();
            dataset.setValue(entry.getKey()+"-"+productName, entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
            "產品銷售比例", dataset, true, true, false
        );

        // 設定中文字型
        PiePlot plot = (PiePlot) pieChart.getPlot();
        Font font = new Font("Microsoft JhengHei", Font.PLAIN, 9);
        pieChart.getTitle().setFont(font); // 圖表標題
        plot.setLabelFont(font); // 標籤字型
        
        // 設定數值顯示格式
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%")
        );
        plot.setLabelGenerator(labelGenerator);

        return pieChart;
    }

}
