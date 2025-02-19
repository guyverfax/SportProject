package controller.porder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Employ;
import dao.EmployDao;
import dao.impl.EmployDaoImpl;

import model.Porder;
import dao.PorderDao;
import dao.impl.PorderDaoImpl;

import model.PorderSummary;
import dao.PorderSummaryDao;
import dao.impl.PorderSummaryDaoImpl;

import util.Tool;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class PorderReadUI extends JFrame {

	//登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPorderno;
	private JTextField txtMembername;
	private JTextField txtEmployname;
	private JTextField txtProductname;
	private JTable pordersummaryTable;
	private EmployDao employDao=new EmployDaoImpl();
	private PorderDao porderDao=new PorderDaoImpl();
	private PorderSummaryDao pordersummaryDao=new PorderSummaryDaoImpl();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PorderReadUI frame = new PorderReadUI();
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
	public PorderReadUI() {
		setTitle("訂單查詢");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 255, 204));
		panel2.setBounds(0, 10, 780, 95);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		JLabel lblPoderno = new JLabel("訂單號碼:");
		lblPoderno.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblPoderno.setBounds(10, 10, 59, 15);
		panel2.add(lblPoderno);
		
		txtPorderno = new JTextField();
		txtPorderno.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		txtPorderno.setBounds(66, 10, 65, 21);
		panel2.add(txtPorderno);
		txtPorderno.setColumns(10);
		
		JLabel lblMembername = new JLabel("客戶名字:");
		lblMembername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblMembername.setBounds(145, 10, 59, 15);
		panel2.add(lblMembername);
		
		txtMembername = new JTextField();
		txtMembername.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		txtMembername.setColumns(10);
		txtMembername.setBounds(201, 10, 65, 21);
		panel2.add(txtMembername);
		
		JLabel lblEmployname = new JLabel("員工名字:");
		lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblEmployname.setBounds(281, 10, 59, 15);
		panel2.add(lblEmployname);
		
		txtEmployname = new JTextField();
		txtEmployname.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		txtEmployname.setColumns(10);
		txtEmployname.setBounds(337, 10, 65, 21);
		panel2.add(txtEmployname);

		JLabel lblProductname = new JLabel("產品名稱:");
		lblProductname.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblProductname.setBounds(412, 10, 59, 15);
		panel2.add(lblProductname);
		
		txtProductname = new JTextField();
		txtProductname.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		txtProductname.setColumns(10);
		txtProductname.setBounds(468, 10, 65, 21);
		panel2.add(txtProductname);
		
		JPanel panel3 = new JPanel();
		panel3.setBounds(0, 115, 780, 218);
		contentPane.add(panel3);
		panel3.setLayout(null);
		
		// 訂單表格
		pordersummaryTable = new JTable();
		pordersummaryTable.setFont(new Font("微軟正黑體", Font.PLAIN, 11));		
        JScrollPane scrollPane = new JScrollPane(pordersummaryTable);
        scrollPane.setBounds(0, 0, 775, 213);
        panel3.add(scrollPane);
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(new Color(255, 255, 204));
		panel4.setLayout(null);
		panel4.setBounds(0, 343, 780, 95);
		contentPane.add(panel4);
				
		txtPorderno.setText("");
		txtMembername.setText("");
		txtEmployname.setText("");
		txtProductname.setText("");
		
		if (loginRole.equals("User"))
		{
			txtEmployname.setText(loingEmployname);
		}
	    
		JButton btnSearch = new JButton("搜尋");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPorderSummary();
			}
		});
		
		btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnSearch.setBounds(543, 10, 59, 23);
		panel2.add(btnSearch);
		
		JLabel lblLogin = new JLabel("登入帳號:");
		lblLogin.setBounds(629, 10, 122, 15);
		panel2.add(lblLogin);
		lblLogin.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
		lblLogin.setText("登入帳號:"+loingEmployusername);
		
		JLabel lblTime = new JLabel("現在時間:");
		lblTime.setBounds(629, 33, 134, 15);
		panel2.add(lblTime);
		lblTime.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
		Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
		
		JButton btnDetail = new JButton("查看詳情");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = pordersummaryTable.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "請選擇一筆訂單查看詳情");
		            return;
		        }

		        // 取得選中的訂單號碼 (假設訂單號碼在第 0 欄)
		        String porderNo = pordersummaryTable.getValueAt(selectedRow, 0).toString();
		        
		        // 查詢訂單詳情
		        List<Porder> orderDetails = porderDao.readAllByPorderno(porderNo);

		        // 顯示訂單詳情視窗
		        new PorderReadDialog(PorderReadUI.this, orderDetails).setVisible(true);
			}
		});
		btnDetail.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnDetail.setBounds(10, 60, 85, 23);
		panel4.add(btnDetail);
		
		/*
		JButton btnToWord = new JButton("匯出(Word)");
		btnToWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = pordersummaryTable.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "請選擇一筆訂單匯出");
		            return;
		        }
		        // 取得選中的訂單號碼 (假設訂單號碼在第 0 欄)
		        String porderNo = pordersummaryTable.getValueAt(selectedRow, 0).toString();
		        String memberNo = pordersummaryTable.getValueAt(selectedRow, 1).toString();
		        String memberName = pordersummaryTable.getValueAt(selectedRow, 2).toString();
		        int totalAmount = (int) pordersummaryTable.getValueAt(selectedRow, 6);
		        
		        // 查詢訂單詳情
		        List<Porder> orderDetails = porderDao.readAllByPorderno(porderNo);

		        // 顯示訂單詳情視窗
		        new PorderReadToWord(PorderReadUI.this, orderDetails, 
		        		porderNo,
		        		memberNo,
		        		totalAmount).setVisible(true);
			}
		});
		btnToWord.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnToWord.setBounds(111, 60, 108, 23);
		panel4.add(btnToWord);
		
		*/
		
		JButton btnBack = new JButton("回主頁");
		 //回主頁
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderReadUI.this);
        	}
        });
		btnBack.setBounds(660, 55, 108, 30);
		panel4.add(btnBack);
		
		
		JButton btnPrint = new JButton("匯出");
		btnPrint.addActionListener(e -> {
			
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);
            DefaultTableModel tableModel=(DefaultTableModel) pordersummaryTable.getModel();
            if (option == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                	writer.write('\ufeff');
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        writer.print(tableModel.getColumnName(i) + (i == tableModel.getColumnCount() - 1 ? "\n" : ","));
                    }
                    for (int row = 0; row < tableModel.getRowCount(); row++) {
                        for (int col = 0; col < tableModel.getColumnCount(); col++) {
                            writer.print(tableModel.getValueAt(row, col) + (col == tableModel.getColumnCount() - 1 ? "\n" : ","));
                        }
                    }
                    JOptionPane.showMessageDialog(this, "資料已成功匯出！");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "匯出失敗：" + ex.getMessage());
                }
            }
        });
		btnPrint.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnPrint.setBounds(119, 62, 85, 23);
		panel4.add(btnPrint);
		
	}
	
	private void updateTable(List<PorderSummary> orders) {
		DefaultTableModel model = new DefaultTableModel(new String[]{"訂單號", "客戶編號", "客戶名字", "員工編號", "員工名字", "產品", "總金額"},0)
				{
					@Override
					public boolean isCellEditable(int row, int column) {
						return false; // 所有欄位都不可編輯
					}
				};
	    for (PorderSummary order : orders) {
	        model.addRow(new Object[]{order.getPorderno(), order.getMemberno(), order.getMembername(), order.getEmployno(), order.getEmployname(), order.getProducts(), order.getTotalprice()});
	    }
	    pordersummaryTable.setModel(model);
		pordersummaryTable.getColumnModel().getColumn(0).setPreferredWidth(120);  // 訂單號碼
		pordersummaryTable.getColumnModel().getColumn(1).setPreferredWidth(80); // 客戶編號
		pordersummaryTable.getColumnModel().getColumn(2).setPreferredWidth(80); // 客戶名字
		pordersummaryTable.getColumnModel().getColumn(3).setPreferredWidth(80); // 員工編號
		pordersummaryTable.getColumnModel().getColumn(4).setPreferredWidth(80); // 員工名字
		pordersummaryTable.getColumnModel().getColumn(5).setPreferredWidth(150); // 產品
		pordersummaryTable.getColumnModel().getColumn(6).setPreferredWidth(80); // 總金額
	}
	
	private void searchPorderSummary() {
	    String orderNo = txtPorderno.getText().trim();
	    String memberKeyword = txtMembername.getText().trim();
	    String employeeKeyword = txtEmployname.getText().trim();
	    String productKeyword = txtProductname.getText().trim();
	    //Date startDate = dateChooserStart.getDate();
	    //Date endDate = dateChooserEnd.getDate();
	    
	    //增加Role判斷

	    // SQL 查詢條件
	    String sql = "SELECT * FROM pordersummary WHERE 1=1";
	    List<Object> params = new ArrayList<>();
	    
	    if (loginRole.equals("User"))
	    {
	    	sql += " AND employno LIKE ?";
	        params.add("%" + loingEmployno + "%");
	    }
	    	
	    if (!orderNo.isEmpty()) {
	        sql += " AND porderno LIKE ?";
	        params.add("%" + orderNo + "%");
	    }
	    if (!memberKeyword.isEmpty()) {
	        sql += " AND membername LIKE ?";
	        params.add("%" + memberKeyword + "%");
	    }
	    if (!employeeKeyword.isEmpty()) {
	        sql += " AND employname LIKE ?";
	        params.add("%" + employeeKeyword + "%");
	    }
	    if (!productKeyword.isEmpty()) {
	        sql += " AND products LIKE ?";
	        params.add("%" + productKeyword + "%"); // 搜尋包含該產品的訂單
	    }
	    
	    /*
	    if (startDate != null) {
	        sql += " AND order_date >= ?";
	        params.add(new java.sql.Date(startDate.getTime()));
	    }
	    if (endDate != null) {
	        sql += " AND order_date <= ?";
	        params.add(new java.sql.Date(endDate.getTime()));
	    }
		*/
	    
	    // 執行 SQL 查詢
	    List<PorderSummary> orders = pordersummaryDao.readAll(sql, params);
	    updateTable(orders);
	}
}
