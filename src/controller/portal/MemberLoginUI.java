package controller.portal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.customer.MemberMainUI;
import service.EmployService;
import service.MemberService;
import service.impl.EmployServiceImpl;
import service.impl.MemberServiceImpl;
import util.Tool;
import model.Employ;
import model.Member;

public class MemberLoginUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private MemberService memberService= new MemberServiceImpl();
    private EmployService employService= new EmployServiceImpl();

    public MemberLoginUI() {
        setTitle("客戶登入");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(220, 250, 220));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUsername = new JLabel("帳號：");
        lblUsername.setBounds(59, 60, 100, 20);
        contentPane.add(lblUsername);

        // 使用者名稱輸入框
        txtUsername = new JTextField();
        txtUsername.setBounds(105, 60, 150, 20);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        // 密碼標籤
        JLabel lblPassword = new JLabel("密碼：");
        lblPassword.setBounds(59, 100, 100, 20);
        contentPane.add(lblPassword);

        // 密碼輸入框
        txtPassword = new JPasswordField();
        txtPassword.setBounds(105, 100, 150, 20);
        contentPane.add(txtPassword);

        JLabel lblNotice1 = new JLabel("(使用客戶編號登入)");
        lblNotice1.setForeground(Color.RED);
        lblNotice1.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        lblNotice1.setBounds(273, 63, 124, 15);
        contentPane.add(lblNotice1);
        
        JLabel lblNotice2 = new JLabel("(使用客戶電話登入)");
        lblNotice2.setForeground(Color.RED);
        lblNotice2.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        lblNotice2.setBounds(273, 103, 109, 15);
        contentPane.add(lblNotice2);
        
        // 登入按鈕
        JButton btnLogin = new JButton("登入");
        btnLogin.setBounds(161, 147, 80, 25);
        contentPane.add(btnLogin);
        // 初始化事件處理器
        initEventHandlers(btnLogin);
        
    }

    private void initEventHandlers(JButton btnLogin) {
        // 登入
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());
                                
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "請輸入使用者名稱和密碼！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Member member = memberService.readByMemberno(username);
                if (member!=null && member.getPhone().equals(password)) {
                	Tool.save(member, "loginmember.txt");
                    JOptionPane.showMessageDialog(null, "登入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    MemberMainUI frame = new MemberMainUI();
					frame.setVisible(true);
					dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "使用者或密碼錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	MemberLoginUI frame = new MemberLoginUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}