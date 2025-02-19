package controller.customer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.porder.PorderReadDialog;
import controller.porder.PorderReadUI;
import model.Employ;
import model.Member;
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
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class MemberReadUI extends JFrame {

	//登入資訊
	private Member loginMember=(Member) Tool.read("loginmember.txt");
	private String loingMemberno=loginMember.getMemberno();
	private String loingMembername=loginMember.getName();
	private String loingMemberphone=loginMember.getPhone();
	private String loingMemberaddress=loginMember.getAddress();
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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
					MemberReadUI frame = new MemberReadUI();
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
	public MemberReadUI() {
		setTitle("訂單查詢");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(220, 250, 220));
		panel1.setBounds(10, 10, 770, 74);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		JLabel lblMemberno = new JLabel("客戶編號:");
		lblMemberno.setBounds(10, 0, 154, 37);
		lblMemberno.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		panel1.add(lblMemberno);
		lblMemberno.setText("客戶編號: "+loingMemberno);
		
		JLabel lblMembername = new JLabel("客戶名字:");
		lblMembername.setBounds(150, 0, 154, 37);
		lblMembername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		panel1.add(lblMembername);
		lblMembername.setText("客戶名字: "+loingMembername);
		
		JLabel lblMemberphone = new JLabel("客戶電話:");
		lblMemberphone.setBounds(290, 0, 154, 37);
		lblMemberphone.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		panel1.add(lblMemberphone);
		lblMemberphone.setText("客戶電話: "+loingMemberphone);
		
		JLabel lblMemberaddress = new JLabel("客戶地址:");
		lblMemberaddress.setBounds(430, 0, 154, 37);
		lblMemberaddress.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		panel1.add(lblMemberaddress);
		lblMemberaddress.setText("客戶地址: "+loingMemberaddress);
		
		JButton btnDetail = new JButton("查看詳情");
		btnDetail.setBounds(10, 40, 85, 25);
		panel1.add(btnDetail);
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
		        List<Porder> porders = porderDao.readAllByPorderno(porderNo);
		        new MemberReadDialog(MemberReadUI.this, porderNo, loginMember, porders ).setVisible(true);
		        
			}
		});
		btnDetail.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		
		JButton btnBack = new JButton("回主頁");
		btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		btnBack.setBounds(105, 40, 85, 25);
		panel1.add(btnBack);
		
		JLabel lblLogin = new JLabel("登入帳號: <dynamic>");
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblLogin.setBounds(615, 10, 138, 15);
		panel1.add(lblLogin);
		lblLogin.setText("登入帳號: "+loingMemberno);
		
		JLabel lblTime = new JLabel("New label");
		lblTime.setForeground(Color.BLACK);
		lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		lblTime.setBounds(615, 30, 155, 15);
		panel1.add(lblTime);
		Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();

		//回主頁
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MemberMainUI frame = new MemberMainUI();
				frame.setVisible(true);
				dispose();
        	}
        });
		
		// 訂單表格
		pordersummaryTable = new JTable();
		pordersummaryTable.setFont(new Font("微軟正黑體", Font.PLAIN, 11));		
		JScrollPane scrollPane = new JScrollPane(pordersummaryTable);
		scrollPane.setBounds(10, 94, 775, 333);
		contentPane.add(scrollPane);
		
		List<PorderSummary> pordersummarys=new ArrayList();
		pordersummarys=pordersummaryDao.readAllByMemberno(loingMemberno);
		
		List<Porder> porders=new ArrayList();
		porders=porderDao.readByMemberno(loingMemberno);
		updateTable(pordersummarys);
		
	}
	
	private void updateTable(List<PorderSummary> orders) {
		DefaultTableModel model = new DefaultTableModel(new String[]{"訂單編號", "客戶編號", "客戶名字", "員工編號", "員工名字", "產品", "金額"},0)
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
		pordersummaryTable.getColumnModel().getColumn(0).setPreferredWidth(150);  // 訂單號碼
		pordersummaryTable.getColumnModel().getColumn(1).setPreferredWidth(50); // 客戶編號
		pordersummaryTable.getColumnModel().getColumn(2).setPreferredWidth(50); // 客戶名字
		pordersummaryTable.getColumnModel().getColumn(3).setPreferredWidth(50); // 員工編號
		pordersummaryTable.getColumnModel().getColumn(4).setPreferredWidth(50); // 員工名字
		pordersummaryTable.getColumnModel().getColumn(5).setPreferredWidth(200); // 產品
		pordersummaryTable.getColumnModel().getColumn(6).setPreferredWidth(50); // 金額
	}
}
