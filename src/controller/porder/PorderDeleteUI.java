package controller.porder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.PorderDao;
import dao.PorderSummaryDao;
import dao.impl.PorderDaoImpl;
import dao.impl.PorderSummaryDaoImpl;
import util.DbConnection;
import util.Tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dao.PorderDao;
import dao.impl.PorderDaoImpl;
import model.Employ;
import model.Porder;
import model.PorderSummary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import dao.PorderDao;
import dao.impl.PorderDaoImpl;
import model.Porder;
import java.awt.Font;

public class PorderDeleteUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnDelete, btnViewDetails, btnSearch;
    private JTextField txtSearch;
    
    private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginEmployRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();
    
    private PorderDao porderDao = new PorderDaoImpl();
    private PorderSummaryDao pordersummaryDao=new PorderSummaryDaoImpl();
    private List<PorderSummary> allPorders; // 儲存完整訂單列表

    public PorderDeleteUI() {
  
        setTitle("刪除訂單");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panelTop = new JPanel();
        panelTop.setBounds(10, 0, 780, 60);
        panelTop.setBackground(new Color(255, 255, 204));
        getContentPane().add(panelTop);
        panelTop.setLayout(null);

        // 搜尋輸入框
        txtSearch = new JTextField();
        txtSearch.setBounds(10, 10, 200, 30);
        panelTop.add(txtSearch);

        // 搜尋按鈕
        btnSearch = new JButton("搜尋");
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

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 765, 300);
        getContentPane().add(scrollPane);

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"訂單編號", "客戶編號", "客戶名稱", "員工編號", "員工名稱", "產品列表", "總金額"})
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
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(40);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.getColumnModel().getColumn(6).setPreferredWidth(40);
        scrollPane.setViewportView(table);
        
        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(new Color(255, 255, 204));
        panelBottom.setBounds(10, 379, 765, 74);
        getContentPane().add(panelBottom);
        panelBottom.setLayout(null);
        
        btnDelete = new JButton("刪除訂單");
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(140, 20, 100, 30);
        panelBottom.add(btnDelete);
                
        btnViewDetails = new JButton("查看詳情");
        btnViewDetails.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnViewDetails.setBounds(20, 20, 100, 30);
        panelBottom.add(btnViewDetails);
                        
        JButton btnBack = new JButton("回到主頁");
        btnBack.setBounds(654, 18, 87, 30);
        panelBottom.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderDeleteUI.this);        		
        		dispose();
            }
        });
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        loadPorderData();
        
        btnDelete.addActionListener(e -> deletePorder());
        btnViewDetails.addActionListener(e -> viewPorderDetails());        
        btnSearch.addActionListener(e -> searchPorder());
    }

    private void loadPorderData() {
        tableModel.setRowCount(0);
        if ("Admin".equals(loginEmployRole)) {
            allPorders = pordersummaryDao.readAll();
        } else {
            allPorders = pordersummaryDao.readAllByEmployno(loingEmployno);
        }
        for (PorderSummary p : allPorders) {
            tableModel.addRow(new Object[]{p.getPorderno(), p.getMemberno(), p.getMembername(), p.getEmployno(), p.getEmployname(), p.getProducts(), p.getTotalprice()});
        }
    }

    private void searchPorder() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadPorderData(); // 如果沒輸入則顯示全部
            return;
        }

        List<PorderSummary> filteredPorders = allPorders.stream()
                .filter(p -> p.getPorderno().toLowerCase().contains(keyword) ||
                		     p.getMemberno().toLowerCase().contains(keyword) ||
                             p.getMembername().toLowerCase().contains(keyword) ||
                             p.getEmployno().toLowerCase().contains(keyword) ||
                             p.getProducts().toLowerCase().contains(keyword) ||
                             String.valueOf(p.getTotalprice()).toLowerCase().contains(keyword) ||
                             p.getEmployname().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (PorderSummary p : filteredPorders) {
            tableModel.addRow(new Object[]{p.getPorderno(), p.getMemberno(), p.getMembername(), p.getEmployno(), p.getEmployname(), p.getProducts(), p.getTotalprice()});
        }
    }

    private void deletePorder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要刪除的訂單");
            return;
        }
        String porderno = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除訂單 " + porderno + " 嗎？", "確認刪除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            porderDao.deleteOrderByPorderNo(porderno);
            loadPorderData();
        }
    }
    
    private void viewPorderDetails() {
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
        new PorderDialog(PorderDeleteUI.this, orderDetails).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PorderDeleteUI().setVisible(true));
    }
}
