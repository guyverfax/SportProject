package controller.portal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import service.EmployService;
import service.MemberService;
import service.impl.EmployServiceImpl;
import service.impl.MemberServiceImpl;
import util.Tool;
import model.Employ;
import model.Member;

public class LoginUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private MemberService memberService= new MemberServiceImpl();
    private EmployService employService= new EmployServiceImpl();

    public LoginUI() {
        setTitle("員工登入");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(230, 240, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUsername = new JLabel("帳號：");
        lblUsername.setBounds(80, 60, 100, 20);
        contentPane.add(lblUsername);

        // 使用者名稱輸入框
        txtUsername = new JTextField();
        txtUsername.setBounds(190, 60, 150, 20);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        // 密碼標籤
        JLabel lblPassword = new JLabel("密碼：");
        lblPassword.setBounds(80, 100, 100, 20);
        contentPane.add(lblPassword);

        // 密碼輸入框
        txtPassword = new JPasswordField();
        txtPassword.setBounds(190, 100, 150, 20);
        contentPane.add(txtPassword);

        // 登入按鈕
        JButton btnLogin = new JButton("登入");
        btnLogin.setBounds(100, 140, 80, 25);
        contentPane.add(btnLogin);

        // 註冊按鈕
        JButton btnRegister = new JButton("註冊");
        btnRegister.setBounds(228, 140, 80, 25);
        contentPane.add(btnRegister);

        // 初始化事件處理器
        initEventHandlers(btnLogin, btnRegister);
    }

    private void initEventHandlers(JButton btnLogin, JButton btnRegister) {
        // 登入按鈕事件處理
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginUI.this, "請輸入使用者名稱和密碼！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employ employ = employService.readByUsername(username);
                if (employ != null && employ.getPassword().equals(password)) {
                	Tool.save(employ, "employ.txt");
                    JOptionPane.showMessageDialog(LoginUI.this, "登入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    Tool.goMainUI(LoginUI.this);
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "使用者或密碼錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 註冊按鈕事件處理
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	RegisterUI frame=new RegisterUI();
            	frame.setVisible(true);
            	dispose();
           }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginUI frame = new LoginUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}