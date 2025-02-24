package controller.portal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import service.EmployService;
import service.MemberService;
import service.impl.EmployServiceImpl;
import service.impl.MemberServiceImpl;
import model.Employ;
import model.Member;

public class RegisterUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtName;
    private JTextField txtEmployno;

    private EmployService employService=new EmployServiceImpl();

    public RegisterUI() {
        setTitle("會員註冊會員註冊系統");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 帳號
        JLabel lblUsername = new JLabel("帳號：");
        lblUsername.setBounds(80, 30, 100, 20);
        contentPane.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(190, 30, 150, 20);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        // 密碼
        JLabel lblPassword = new JLabel("密碼：");
        lblPassword.setBounds(80, 70, 100, 20);
        contentPane.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(190, 70, 150, 20);
        contentPane.add(txtPassword);

        // 姓名
        JLabel lblName = new JLabel("姓名：");
        lblName.setBounds(80, 110, 100, 20);
        contentPane.add(lblName);
        
        txtName = new JTextField();
        txtName.setBounds(190, 110, 150, 20);
        contentPane.add(txtName);
        txtName.setColumns(10);
        
        // 員工編號
        JLabel lblEmployno = new JLabel("員工編號：");
        lblEmployno.setBounds(80, 160, 100, 20);
        contentPane.add(lblEmployno);
        
        txtEmployno = new JTextField();
        txtEmployno.setBounds(190, 160, 150, 20);
        contentPane.add(txtEmployno);
        txtEmployno.setColumns(10);
        

        //======Button
        // 註冊按鈕
        JButton btnRegister = new JButton("註冊");
        btnRegister.setBounds(99, 239, 80, 25);
        contentPane.add(btnRegister);

        // 初始化事件處理器
        initEventHandlers(btnRegister);
        
        JButton btnLogin = new JButton("回到登入畫面");
        btnLogin.setBounds(219, 239, 121, 25);
        contentPane.add(btnLogin);
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	LoginUI frame=new LoginUI();
            	frame.setVisible(true);
            	dispose();
            }
        });
    }

    private void initEventHandlers(JButton btnRegister) {
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());
                String name = txtName.getText().trim();
                String employno = txtEmployno.getText().trim();

                // 驗證輸入是否符合要求
                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || employno.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterUI.this, "所有欄位均不得空白！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (employService.isExitsByUsername(username))
                {
                	JOptionPane.showMessageDialog(RegisterUI.this, "帳號重覆！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(RegisterUI.this, "密碼必須包含至少1個英文、1個數字和1個符號，且長度不超過8個字！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!isValidEmployno(employno)) {
                    JOptionPane.showMessageDialog(RegisterUI.this, "員工編號固定4個字，開頭E其餘3個數字！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (employService.isExitsByEmployno(employno))
                {
                	JOptionPane.showMessageDialog(RegisterUI.this, "員工編號重覆！", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // 自動產生不重複 memberno
                //String memberno = generateUniqueMemberNo();
                
                Employ newEmploy = new Employ(employno, name, username, password, "一般");

                // 調用 MemberService 進行註冊
                boolean isRegistered = employService.create(newEmploy);
                if (isRegistered) {
                    JOptionPane.showMessageDialog(RegisterUI.this, "註冊成功回到登入畫面！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    LoginUI frame=new LoginUI();
                	frame.setVisible(true);
                	dispose();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(RegisterUI.this, "註冊失敗，請稍後再試！", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{1,8}$";
        return password.matches(regex);
    }

    private boolean isValidEmployno(String employno) {
        String regex = "^E\\d{3}$";
        return employno.matches(regex);
    }
    
    private String generateUniqueMemberNo() {
        String employno;
        do {
            employno = "E" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        } while (employService.isExitsByEmployno(employno));
        return employno;
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtName.setText("");
        txtEmployno.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegisterUI frame = new RegisterUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}