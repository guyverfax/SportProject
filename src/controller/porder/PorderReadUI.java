package controller.porder;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import dao.EmployDao;
import dao.impl.EmployDaoImpl;
import dao.PorderDao;
import dao.impl.PorderDaoImpl;
import model.Employ;
import model.Porder;
import model.PorderSummary;
import dao.PorderSummaryDao;
import dao.impl.PorderSummaryDaoImpl;

import util.Tool;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class PorderReadUI extends JFrame {

	//登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginEmployRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private EmployDao employDao=new EmployDaoImpl();
	private PorderDao porderDao=new PorderDaoImpl();
	private PorderSummaryDao pordersummaryDao=new PorderSummaryDaoImpl();
	private List<PorderSummary> allPorders;
	private JTextField txtSearch;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PorderReadUI  frame = new PorderReadUI ();
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
	public PorderReadUI()  {
		setTitle("訂單查詢");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
        JPanel panelTop = new JPanel();
		panelTop.setLayout(null);
		panelTop.setBackground(new Color(255, 255, 204));
		panelTop.setBounds(0, 0, 780, 50);
		contentPane.add(panelTop);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(10, 10, 200, 30);
		panelTop.add(txtSearch);
		
		JButton btnSearch = new JButton("搜尋");
		btnSearch.setBounds(220, 10, 80, 30);
		panelTop.add(btnSearch);
		
		JLabel lblEmployno = new JLabel("員工編號: <dynamic>");
		lblEmployno.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblEmployno.setBounds(364, 10, 117, 31);
		panelTop.add(lblEmployno);
		lblEmployno.setText("員工編號: "+loingEmployno);
		
		JLabel lblEmployname = new JLabel("員工名字: <dynamic>");
		lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblEmployname.setBounds(470, 10, 117, 31);
		panelTop.add(lblEmployname);
		lblEmployname.setText("員工名字: "+loingEmployname);
		
		JLabel lblTime = new JLabel("現在時間:");
		lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblTime.setBounds(614, 9, 156, 31);
		panelTop.add(lblTime);
		Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
		
		// 訂單表格
		tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"訂單編號", "訂單日期", "客戶編號", "客戶名稱", "員工編號", "員工名稱", "產品列表", "總金額"})
			{
		     //惟讀
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("微軟正黑體", Font.PLAIN, 11));	
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 50, 775, 283);
		contentPane.add(scrollPane);
		DefaultTableModel tableModel=(DefaultTableModel) table.getModel();
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBackground(new Color(255, 255, 204));
		panelBottom.setLayout(null);
		panelBottom.setBounds(0, 343, 780, 95);
		contentPane.add(panelBottom);
		
		JButton btnDetail = new JButton("查看詳情");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "請選擇一筆訂單查看詳情");
		            return;
		        }

		        // 取得選中的訂單號碼 (假設訂單號碼在第 0 欄)
		        String porderNo = table.getValueAt(selectedRow, 0).toString();
		        
		        // 查詢訂單詳情
		        List<Porder> orderDetails = porderDao.readByPorderno(porderNo);

		        // 顯示訂單詳情視窗
		        new PorderDialog(PorderReadUI.this, orderDetails).setVisible(true);
			}
		});
		btnDetail.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnDetail.setBounds(10, 20, 100, 25);
		panelBottom.add(btnDetail);
		
			
		JButton btnBack = new JButton("回主頁");
		btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		 //回主頁
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderReadUI .this);
        	}
        });
		btnBack.setBounds(660, 20, 100, 30);
		panelBottom.add(btnBack);
		
		JButton btnToExcel = new JButton("匯出Excel");
		btnToExcel.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnToExcel.setBounds(132, 20, 100, 25);
		panelBottom.add(btnToExcel);
			
		loadPorderData();
		
		btnSearch.addActionListener(e -> searchPorder());
		btnToExcel.addActionListener(e -> exportToExcel());

	}
	
	
	private void searchPorder() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadPorderData(); // 如果沒輸入則顯示全部
            return;
        }

        List<PorderSummary> filteredPorders = allPorders.stream()
                .filter(p -> p.getPorderno().toLowerCase().contains(keyword) ||
                			 p.getOrderdate().toLowerCase().contains(keyword) ||
                		     p.getMemberno().toLowerCase().contains(keyword) ||
                             p.getMembername().toLowerCase().contains(keyword) ||
                             p.getEmployno().toLowerCase().contains(keyword) ||
                             p.getProducts().toLowerCase().contains(keyword) ||
                             String.valueOf(p.getTotalprice()).toLowerCase().contains(keyword) ||
                             p.getEmployname().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		
        tableModel.setRowCount(0);
        for (PorderSummary p : filteredPorders) {
            tableModel.addRow(new Object[]{p.getPorderno(), p.getOrderdate(), p.getMemberno(), p.getMembername(), p.getEmployno(), p.getEmployname(), p.getProducts(), p.getTotalprice()});
        }
    }
	
	private void loadPorderData() {
        tableModel.setRowCount(0);
        if ("Admin".equals(loginEmployRole)) {
            allPorders = pordersummaryDao.readAll();
        } else {
            allPorders = pordersummaryDao.readAllByEmployno(loingEmployno);
        }
        for (PorderSummary p : allPorders) {
            tableModel.addRow(new Object[]{p.getPorderno(), p.getOrderdate(), p.getMemberno(), p.getMembername(), p.getEmployno(), p.getEmployname(), p.getProducts(), p.getTotalprice()});
        }
    }
	
	public static void exportToExcel() {

		String filepath="庫存訂單清單.xls";
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("訂單詳細");

	    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	    int rowCount = tableModel.getRowCount();
	    int colCount = tableModel.getColumnCount();

	    // 建立標題列（Header Row）
	    Row headerRow = sheet.createRow(0);
	    CellStyle headerStyle = workbook.createCellStyle();
	    XSSFFont headerFont = (XSSFFont) workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);
	    headerStyle.setFont(headerFont);

	    for (int col = 0; col < colCount; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(tableModel.getColumnName(col));
	        cell.setCellStyle(headerStyle);
	    }

	    // 寫入資料（Data Rows）
	    for (int row = 0; row < rowCount; row++) {
	        Row excelRow = sheet.createRow(row + 1);
	        for (int col = 0; col < colCount; col++) {
	            Cell cell = excelRow.createCell(col);
	            Object value = tableModel.getValueAt(row, col);
	            if (value != null) {
	                cell.setCellValue(value.toString());
	            }
	        }
	    }

	    // 自動調整欄寬
	    for (int col = 0; col < colCount; col++) {
	        sheet.autoSizeColumn(col);
	    }

	    // 儲存檔案
	    File filePath = new File("庫存訂單清單.xlsx");
	    try (FileOutputStream fos = new FileOutputStream(filePath)) {
	        workbook.write(fos);
	        workbook.close();
	        JOptionPane.showMessageDialog(null, "建立 " + filepath + " 成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
	    } catch (IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Excel 匯出失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
	    }
	}	
	
}
