package controller.customer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.employ.EmployManagerUI;
import controller.employ.EmployUpdateUI;
import controller.member.MemberManagerUI;
import controller.porder.PorderCreate1;
import controller.porder.PorderDeleteUI;
import controller.porder.PorderReadUI;
import controller.porder.PorderUpdateUI;
import controller.product.ProductManagerUI;
import model.Employ;
import model.Member;
import util.Tool;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

public class MemberMainUI extends JFrame {

	//登入資訊
	private Member loginMember=(Member) Tool.read("loginmember.txt");
	private String loingMemberno=loginMember.getMemberno();
	private String loingMembername=loginMember.getName();
		
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberMainUI frame = new MemberMainUI();
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
	public MemberMainUI() {
		setTitle("客戶主頁");
		setSize(599, 454);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(152, 251, 152));
        titlePanel.setBounds(10, 10, 565, 52);
        getContentPane().add(titlePanel);
        titlePanel.setLayout(null);
        
        JLabel lblTitle = new JLabel("體育用品店客戶使用系統");
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBackground(Color.GREEN);
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        lblTitle.setBounds(80, 7, 294, 32);
        titlePanel.add(lblTitle);
        
        JLabel lblLogin = new JLabel("登入帳號: <dynamic>");
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblLogin.setBounds(410, 7, 138, 15);
        titlePanel.add(lblLogin);
        lblLogin.setText("登入帳號: "+loingMemberno);
        
        JLabel lblTime = new JLabel("New label");
        lblTime.setForeground(Color.BLACK);
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTime.setBounds(410, 27, 155, 15);
        titlePanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
        
		// 創建 JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 72, 575, 335);

        // 頁籤1：訂單
        JPanel porderPanel = new JPanel();
        porderPanel.setBackground(new Color(220, 250, 220));
        porderPanel.add(new JLabel("訂單管理"));
        porderPanel.setLayout(null);
        
        // 加入頁籤
        tabbedPane.addTab("訂單管理", porderPanel);
        
        JButton btnRead = new JButton("查詢訂單");
        btnRead.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnRead.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MemberReadUI frame=new MemberReadUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnRead.setBounds(227, 56, 85, 23);
        porderPanel.add(btnRead);
        
        JButton btnAdd = new JButton("新增訂單");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "功能施作中, 請連絡店家為您新增訂單", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return ;
        	}
        });
        btnAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnAdd.setBounds(227, 104, 85, 23);
        porderPanel.add(btnAdd);
        
        JButton btnUpdate = new JButton("編輯訂單");
        btnUpdate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "功能施作中, 請連絡店家為您處理訂單", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return ;
        	}
        });
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(227, 156, 85, 23);
        porderPanel.add(btnUpdate);
        
        JButton btnDelete = new JButton("刪除訂單");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "不好吧, 您要不要先知會客服呢?", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return ;
        	}
        });
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(227, 205, 85, 23);
        porderPanel.add(btnDelete);
             
        // 將 JTabbedPane 加入 JFrame
        getContentPane().add(tabbedPane);   
        
	}
}
