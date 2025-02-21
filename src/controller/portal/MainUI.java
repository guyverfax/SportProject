package controller.portal;

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
import controller.report.EmployChart;
import controller.report.MemberChart;
import controller.report.ProductChart;
import model.Employ;
import util.Tool;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

public class MainUI extends JFrame {

	//登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
		
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
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
	public MainUI() {
		setTitle("管理主頁");
		setSize(599, 454);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBounds(10, 10, 565, 52);
        getContentPane().add(titlePanel);
        titlePanel.setLayout(null);
        
        JLabel lblTitle = new JLabel("體育用品店訂單和庫存管理系統");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBackground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        lblTitle.setBounds(10, 10, 410, 30);
        titlePanel.add(lblTitle);
        
        JLabel lblLogin = new JLabel("New label");
        lblLogin.setForeground(Color.WHITE);
        lblLogin.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblLogin.setBounds(400, 10, 130, 15);
        titlePanel.add(lblLogin);
        lblLogin.setText("登入帳號: "+loingEmployusername);
        
        JLabel lblTime = new JLabel("New label");
        lblTime.setForeground(Color.WHITE);
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblTime.setBounds(400, 30, 130, 15);
        titlePanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		
		JButton btnLogout = new JButton("登出");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "確定要登出嗎?", "確認", JOptionPane.YES_NO_OPTION);
		        if (confirm == JOptionPane.YES_OPTION) {
		            PortalUI frame=new PortalUI();
		            frame.setVisible(true);
		            dispose();
		        }
			}
		});
		btnLogout.setForeground(Color.RED);
		btnLogout.setFont(new Font("微軟正黑體", Font.BOLD, 10));
		btnLogout.setBounds(500, 8, 60, 20);
		titlePanel.add(btnLogout);
		timer.start();
		
		// 創建 JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 72, 575, 335);

        // 頁籤1：訂單
        JPanel porderPanel = new JPanel();
        porderPanel.setBackground(new Color(255, 255, 204));
        porderPanel.add(new JLabel("訂單管理"));
        porderPanel.setLayout(null);
        
        // 頁籤2：產品
        JPanel productPanel = new JPanel();
        productPanel.setBackground(new Color(204, 255, 204));
        productPanel.add(new JLabel("產品管理"));
        productPanel.setLayout(null);
        
        // 頁籤3：會員
        JPanel memberPanel = new JPanel();
        memberPanel.setBackground(new Color(153, 255, 255));
        memberPanel.add(new JLabel("會員管理"));
        memberPanel.setLayout(null);        
        
        // 頁籤4：員工
        JPanel employPanel = new JPanel();
        employPanel.setBackground(new Color(255, 192, 203));
        employPanel.add(new JLabel("員工管理"));
        employPanel.setLayout(null); 
        
        // 頁籤5：報表
        JPanel reportPanel = new JPanel();
        reportPanel.setBackground(new Color(230, 230, 250));
        reportPanel.add(new JLabel("報表管理"));
        reportPanel.setLayout(null); 
        
        // 加入頁籤
        tabbedPane.addTab("訂單管理", porderPanel);
        
        JButton btnCreate = new JButton("新增訂單");
        btnCreate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnCreate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PorderCreate1 frame=new PorderCreate1();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnCreate.setBounds(230, 63, 85, 23);
        porderPanel.add(btnCreate);
        
        JButton btnRead = new JButton("查詢訂單");
        btnRead.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnRead.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PorderReadUI frame=new PorderReadUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnRead.setBounds(229, 115, 85, 23);
        porderPanel.add(btnRead);
        
        JButton btnUpdate = new JButton("編輯訂單");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PorderUpdateUI frame=new PorderUpdateUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnUpdate.setBounds(229, 166, 85, 23);
        porderPanel.add(btnUpdate);
        
        JButton btnDelete = new JButton("刪除訂單");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PorderDeleteUI frame=new PorderDeleteUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(229, 220, 85, 23);
        porderPanel.add(btnDelete);
        
        
        tabbedPane.addTab("產品管理", productPanel);
        
        JButton btnProductManager = new JButton("管理產品");
        btnProductManager.setBounds(230, 63, 85, 23);
        productPanel.add(btnProductManager);
        btnProductManager.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!loginRole.equals("Admin"))
        		{
        			JOptionPane.showMessageDialog(null, "您不是Admin無法使用");
        			return;
        		}
        		ProductManagerUI frame=new ProductManagerUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnProductManager.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        JButton btnProductAdd = new JButton("新增產品");
        btnProductAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "己整合到管理產品功能", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return ;
        	}
        });
        btnProductAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnProductAdd.setBounds(230, 108, 85, 23);
        productPanel.add(btnProductAdd);
        
        JButton btnProductEdit = new JButton("編輯產品");
        btnProductEdit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "請由管理產品功能進去", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return ;
        	}
        });
        btnProductEdit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnProductEdit.setBounds(230, 156, 85, 23);
        productPanel.add(btnProductEdit);
        
        JButton btnProductDelete = new JButton("刪除產品");
        btnProductDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "請改至管理產品功能", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return ;
        	}
        });
        btnProductDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnProductDelete.setBounds(230, 208, 85, 23);
        productPanel.add(btnProductDelete);
        tabbedPane.addTab("客戶管理", memberPanel);
        
        JButton btnMemberManager = new JButton("管理客戶");
        btnMemberManager.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!loginRole.equals("Admin"))
        		{
        			JOptionPane.showMessageDialog(null, "您不是Admin無法使用");
        			return;
        		}
        		MemberManagerUI frame=new MemberManagerUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnMemberManager.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnMemberManager.setBounds(230, 63, 85, 23);
        memberPanel.add(btnMemberManager);
        
        JButton btnMemberAdd = new JButton("新增客戶");
        btnMemberAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "請改用管理客戶功能", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return;
        	}
        });
        btnMemberAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnMemberAdd.setBounds(230, 116, 85, 23);
        memberPanel.add(btnMemberAdd);
        
        JButton btnMemberEdit = new JButton("編輯客戶");
        btnMemberEdit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "請改用管理客戶功能", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return;
        	}
        });
        btnMemberEdit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnMemberEdit.setBounds(230, 169, 85, 23);
        memberPanel.add(btnMemberEdit);
        
        JButton btnMemberDelete = new JButton("刪除客戶");
        btnMemberDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, "請改用管理客戶功能", "訊息", JOptionPane.INFORMATION_MESSAGE);
        		return;
        	}
        });
        btnMemberDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnMemberDelete.setBounds(230, 216, 85, 23);
        memberPanel.add(btnMemberDelete);
        tabbedPane.addTab("員工管理", employPanel);
        
        JButton btnEmployUpdate = new JButton("修改個人資料");
        btnEmployUpdate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EmployUpdateUI frame=new EmployUpdateUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnEmployUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnEmployUpdate.setBounds(230, 83, 112, 23);
        employPanel.add(btnEmployUpdate);
        
        JButton btnEmployManager = new JButton("管理員工");
        btnEmployManager.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!loginRole.equals("Admin"))
        		{
        			JOptionPane.showMessageDialog(null, "您不是Admin無法使用");
        			return;
        		}
        		EmployManagerUI frame=new EmployManagerUI();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnEmployManager.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnEmployManager.setBounds(230, 129, 112, 23);
        employPanel.add(btnEmployManager);
        
        tabbedPane.addTab("報表管理", reportPanel);
        
        JButton btnByEmploy = new JButton("員工銷售圖表");
        btnByEmploy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EmployChart frame=new EmployChart();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnByEmploy.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnByEmploy.setBounds(230, 81, 112, 23);
        reportPanel.add(btnByEmploy);
        
        JButton btnByProduct = new JButton("產品銷售圖表");
        btnByProduct.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ProductChart frame=new ProductChart();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnByProduct.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnByProduct.setBounds(230, 139, 112, 23);
        reportPanel.add(btnByProduct);
        
        JButton btnByMember = new JButton("客戶銷售圖表");
        btnByMember.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MemberChart frame=new MemberChart();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnByMember.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnByMember.setBounds(230, 198, 112, 23);
        reportPanel.add(btnByMember);
        
        // 將 JTabbedPane 加入 JFrame
        getContentPane().add(tabbedPane);
        
        
	}
}
