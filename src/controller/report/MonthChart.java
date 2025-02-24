package controller.report;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;

import dao.RevenueDao;
import dao.impl.RevenueDaoImpl;
import model.Employ;
import model.PorderSummary;
import model.Revenue;
import util.Tool;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MonthChart extends JFrame {

	//登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginEmployRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable salesTable;
	private static DefaultTableModel tableModel;

	private RevenueDao revenueDao=new RevenueDaoImpl();
	private List<Revenue> allRevenue;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonthChart frame = new MonthChart();
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
	public MonthChart() {
		setTitle("月銷售報表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelChart = new JPanel();
		panelChart.setBounds(10, 73, 716, 209);
		contentPane.add(panelChart);
				
		JPanel panelTable = new JPanel();
		panelTable.setBounds(10, 292, 716, 261);
		contentPane.add(panelTable);
		panelTable.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 716, 251);
		panelTable.add(scrollPane);
		
		// 訂單表格
		tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"年", "月", "銷售額"})
		{
		//惟讀
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		salesTable = new JTable();
		salesTable.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
		scrollPane.setViewportView(salesTable);
		salesTable.setModel(tableModel);
		
		loadPorderData();
		
		JFreeChart lineChart = generateChart(allRevenue);
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		lineChartPanel.setPreferredSize(new Dimension(719, 209)); 
		panelChart.add(lineChartPanel);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 726, 53);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnBack = new JButton("按此回主頁");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tool.goMainUI(MonthChart.this);
			}
		});
		btnBack.setBounds(270, 0, 194, 49);
		btnBack.setForeground(Color.RED);
		btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		panel.add(btnBack);
			
	}
	
	private void loadPorderData() {
        tableModel.setRowCount(0);
        allRevenue=revenueDao.readAll();
        for (Revenue r : allRevenue) {
            tableModel.addRow(new Object[]{r.getYear(), r.getMonth(), r.getRevenue()});
        }
    }
	
	// 生成 JFreeChart 圖表
    private static JFreeChart generateChart(List<Revenue> revenue) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Revenue r : revenue) {
            String period = r.getYear() + "-" + r.getMonth();
            dataset.addValue(r.getRevenue(), "Sales", period);
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Monthly Sales Data",
                "Month",
                "Sales ($)",
                dataset
        );

        return lineChart;
    }
}
