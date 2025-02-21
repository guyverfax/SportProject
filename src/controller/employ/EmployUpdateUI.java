package controller.employ;

import dao.impl.EmployDaoImpl;
import dao.impl.MemberDaoImpl;
import dao.impl.PorderDaoImpl;
import dao.EmployDao;
import dao.MemberDao;
import dao.PorderDao;
import model.Employ;
import model.Member;
import model.Porder;

import dao.impl.ProductDaoImpl;
import dao.ProductDao;
import model.Product;
import util.Tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.portal.RegisterUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployUpdateUI extends JFrame {
	
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployname=loginEmploy.getName();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmploypassword=loginEmploy.getPassword();
	private String loginEmployRole=loginEmploy.getRole();
	
    private JTextField txtEmployNo, txtEmployusername, txtEmployName, txtEmployPassword;
    private JComboBox cbEmployRole;
    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployUpdateUI().setVisible(true);
        });
    }
    
    public EmployUpdateUI() {
        setTitle("員工個人資料修改");
        setSize(600, 174);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // 上方 JPanel (輸入區域)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 192, 203));
        topPanel.setBounds(10, 10, 560, 120);
        topPanel.setLayout(null);
        getContentPane().add(topPanel);

        JLabel lblEmployNo = new JLabel("員工編號:");
        lblEmployNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployNo.setBounds(10, 10, 164, 25);
        topPanel.add(lblEmployNo);
        lblEmployNo.setText("員工編號:    "+loingEmployno);
        
        /*
        txtEmployNo = new JTextField();
        txtEmployNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployNo.setBounds(74, 10, 100, 25);
        topPanel.add(txtEmployNo);
        JLabel lblNoNotice = new JLabel("例: E111");
        lblNoNotice.setForeground(Color.RED);
        lblNoNotice.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblNoNotice.setBounds(184, 16, 50, 15);
        topPanel.add(lblNoNotice);
        */

        JLabel lblEmployUsername = new JLabel("員工帳號:");
        lblEmployUsername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployUsername.setBounds(236, 10, 80, 25);
        topPanel.add(lblEmployUsername);
        txtEmployusername = new JTextField();
        txtEmployusername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployusername.setBounds(303, 10, 100, 25);
        topPanel.add(txtEmployusername);
        txtEmployusername.setText(loingEmployusername);

        JLabel lblEmployName = new JLabel("員工名字:");
        lblEmployName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployName.setBounds(10, 45, 80, 25);
        topPanel.add(lblEmployName);
        txtEmployName = new JTextField();
        txtEmployName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployName.setBounds(74, 45, 100, 25);
        topPanel.add(txtEmployName);
        txtEmployName.setText(loingEmployname);

        JLabel lblEmployPassword = new JLabel("員工密碼:");
        lblEmployPassword.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployPassword.setBounds(236, 45, 80, 25);
        topPanel.add(lblEmployPassword);
        txtEmployPassword = new JTextField();
        txtEmployPassword.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployPassword.setBounds(303, 45, 100, 25);
        topPanel.add(txtEmployPassword);
        txtEmployPassword.setText(loingEmploypassword);
        
        JLabel lblEmployRole = new JLabel("員工角色:");
        lblEmployRole.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployRole.setBounds(431, 45, 80, 25);
        topPanel.add(lblEmployRole);
        
        cbEmployRole = new JComboBox();
        cbEmployRole.setModel(new DefaultComboBoxModel(new String[] {"User", "Admin"}));
        cbEmployRole.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
        cbEmployRole.setBounds(489, 45, 61, 23);
        topPanel.add(cbEmployRole);
        cbEmployRole.setSelectedItem(loginEmployRole);

        JButton btnUpdate = new JButton("確認");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(74, 80, 100, 25);
        topPanel.add(btnUpdate);
        
        JButton btnBack = new JButton("回主頁");
        btnBack.setBounds(371, 79, 108, 30);
        topPanel.add(btnBack);
        
        JLabel lblTime = new JLabel("現在時間:");
        lblTime.setForeground(Color.BLACK);
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTime.setBounds(413, 15, 133, 15);
        topPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
        
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(EmployUpdateUI.this);
        	}
        });

        // 修改
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtEmployName.getText().trim();
            	String username = txtEmployusername.getText().trim();
                String password = txtEmployPassword.getText().trim();
            	String role = (String) cbEmployRole.getSelectedItem();
                // 驗證輸入是否符合要求
                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "所有欄位均不得空白！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null, "密碼必須包含至少1個英文、1個數字和1個符號，且長度不超過8個字！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(null, "資料修改成功, 回到主頁面！", "提示", JOptionPane.INFORMATION_MESSAGE);         
                Employ employ=new Employ(loingEmployno,name,username,password,role);
                employDao.update(employ);
                Tool.save(employ, "employ.txt");
                Tool.goMainUI(EmployUpdateUI.this);
            }
        });
    }
    
    private boolean isValidEmployno(String employno) {
        String regex = "^E\\d{3}$";
        return employno.matches(regex);
    }
    
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{1,8}$";
        return password.matches(regex);
    }
}