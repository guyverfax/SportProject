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

import controller.porder.PorderReadUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployManagerUI extends JFrame {
	
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();
	
    private JTextField txtEmployNo, txtEmployusername, txtEmployName, txtEmployPassword;
    private JComboBox cbEmployRole;
    private JTable employTable;
    private DefaultTableModel tableModel;
    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployManagerUI().setVisible(true);
        });
    }
    
    public EmployManagerUI() {
        setTitle("員工管理");
        setSize(600, 500);
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
        lblEmployNo.setBounds(10, 10, 80, 25);
        topPanel.add(lblEmployNo);
        txtEmployNo = new JTextField();
        txtEmployNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployNo.setBounds(74, 10, 100, 25);
        topPanel.add(txtEmployNo);

        JLabel lblNoNotice = new JLabel("例: E111");
        lblNoNotice.setForeground(Color.RED);
        lblNoNotice.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblNoNotice.setBounds(184, 16, 50, 15);
        topPanel.add(lblNoNotice);
        
        JLabel lblEmployUsername = new JLabel("員工帳號:");
        lblEmployUsername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployUsername.setBounds(236, 10, 80, 25);
        topPanel.add(lblEmployUsername);
        txtEmployusername = new JTextField();
        txtEmployusername.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployusername.setBounds(303, 10, 100, 25);
        topPanel.add(txtEmployusername);

        JLabel lblEmployName = new JLabel("員工名字:");
        lblEmployName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployName.setBounds(10, 45, 80, 25);
        topPanel.add(lblEmployName);
        txtEmployName = new JTextField();
        txtEmployName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployName.setBounds(74, 45, 100, 25);
        topPanel.add(txtEmployName);

        JLabel lblEmployPassword = new JLabel("員工密碼:");
        lblEmployPassword.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployPassword.setBounds(236, 45, 80, 25);
        topPanel.add(lblEmployPassword);
        txtEmployPassword = new JTextField();
        txtEmployPassword.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtEmployPassword.setBounds(303, 45, 100, 25);
        topPanel.add(txtEmployPassword);
        
        JLabel lblEmployRole = new JLabel("員工角色:");
        lblEmployRole.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployRole.setBounds(431, 45, 80, 25);
        topPanel.add(lblEmployRole);
        
        cbEmployRole = new JComboBox();
        cbEmployRole.setModel(new DefaultComboBoxModel(new String[] {"User", "Admin"}));
        cbEmployRole.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
        cbEmployRole.setBounds(489, 45, 61, 23);
        topPanel.add(cbEmployRole);
        
        JButton btnAdd = new JButton("新增員工");
        btnAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnAdd.setBounds(10, 80, 100, 25);
        topPanel.add(btnAdd);

        JButton btnUpdate = new JButton("修改員工");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(120, 80, 100, 25);
        topPanel.add(btnUpdate);

        JButton btnDelete = new JButton("刪除員工");
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(230, 80, 100, 25);
        topPanel.add(btnDelete);
        
        JLabel lblLogin = new JLabel("登入帳號: <dynamic>");
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblLogin.setBounds(405, 80, 138, 15);
        topPanel.add(lblLogin);
        lblLogin.setText("登入帳號: "+loingEmployusername);
        
        JLabel lblTime = new JLabel("現在時間:");
        lblTime.setForeground(Color.BLACK);
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTime.setBounds(405, 100, 155, 15);
        topPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();

        // 中間 JPanel (表格區域)
        JPanel middlePanel = new JPanel();
        middlePanel.setBounds(10, 140, 560, 250);
        middlePanel.setLayout(new BorderLayout());
        getContentPane().add(middlePanel);

        tableModel = new DefaultTableModel(new String[]{"員工編號", "員工名字", "員工帳號", "員工密碼", "員工角色"}, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employTable = new JTable(tableModel);
        /*
        employTable.getColumnModel().getColumn(0).setPreferredWidth(100); // 員工編號
        employTable.getColumnModel().getColumn(1).setPreferredWidth(100); // 員工名字
        employTable.getColumnModel().getColumn(2).setPreferredWidth(100); // 員工帳號
        employTable.getColumnModel().getColumn(3).setPreferredWidth(100); // 員工密碼
        employTable.getColumnModel().getColumn(4).setPreferredWidth(100); // 員工角色
        */
        employTable.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        employTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        middlePanel.add(new JScrollPane(employTable), BorderLayout.CENTER);

        // 下方 JPanel (額外功能按鈕)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 192, 203));
        bottomPanel.setBounds(10, 400, 560, 50);
        bottomPanel.setLayout(null);
        getContentPane().add(bottomPanel);

        JButton btnRefresh = new JButton("重新載入");
        btnRefresh.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnRefresh.setBounds(10, 10, 100, 25);
        bottomPanel.add(btnRefresh);
        
        JButton btnBack = new JButton("回主頁");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(EmployManagerUI.this);
        	}
        });
        btnBack.setBounds(442, 10, 108, 30);
        bottomPanel.add(btnBack);

        loadEmployData(); // 載入資料

        // 新增
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmploy();
            }
        });

        // 修改
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateEmploy();
            }
        });

        // 刪除
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmploy();
            }
        });

        // 監聽事件：重新載入產品資料
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadEmployData();
            }
        });
    }

    // 新增產品
    private void addEmploy() {
        String employNo = txtEmployNo.getText().trim();
        String employName = txtEmployName.getText().trim();
        String employUsername = txtEmployusername.getText().trim();
        String employPassword = txtEmployPassword.getText().trim();
        String employRole = (String) cbEmployRole.getSelectedItem();

        // 驗證輸入
        if (employNo.isEmpty() || employName.isEmpty() || employUsername.isEmpty() || employPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請填寫所有欄位!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmployno(employNo)) {
        	JOptionPane.showMessageDialog(this, "員工編號需4位字元,開頭E和3位數字!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidPassword(employPassword)) {
        	JOptionPane.showMessageDialog(this, "密碼必須包含至少1個英文、1個數字和1個符號，且長度不超過8個字！", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (employDao.isExitsByEmployno(employNo))
        {
        	JOptionPane.showMessageDialog(this, "員工編號重覆!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Employ employ = new Employ(employNo, employName, employUsername, employPassword, employRole);
        employDao.create(employ);
        JOptionPane.showMessageDialog(this, "員工新增成功!");
        loadEmployData();
    }

    // 修改產品
    private void updateEmploy() {
        int selectedRow = employTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要修改的員工!");
            return;
        }

        
        String getEmployNo = (String) tableModel.getValueAt(selectedRow, 0);
        String getEmployName = (String) tableModel.getValueAt(selectedRow, 1);
        String getEmployUsername = (String) tableModel.getValueAt(selectedRow, 2);
        String getEmployPassword = (String) tableModel.getValueAt(selectedRow, 3);
        String getEmployRole = (String) tableModel.getValueAt(selectedRow, 4);
        
        String[] options = {"User", "Admin"};
        JComboBox<String> newEmployRoleComboBox = new JComboBox<>(options);
        newEmployRoleComboBox.setSelectedItem(getEmployRole);
         
        // 建立對話框內容
        Object[] fields = {
            "員工編號: "+getEmployNo,
            "員工名字: "+getEmployName,
            "員工帳號: "+getEmployUsername,
            "員工密碼: "+getEmployPassword,
        	"員工角色:",newEmployRoleComboBox,
        };
        
        // 顯示 JOptionPane，讓使用者修改數值
        int result = JOptionPane.showConfirmDialog(null, fields, "修改員工資料",
                     JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        // 如果使用者按下 OK，則更新 JTable          
        if (result == JOptionPane.OK_OPTION) {
            String newEmployRole = (String) (newEmployRoleComboBox.getSelectedItem());               
            tableModel.setValueAt(newEmployRole, selectedRow, 4);
            Employ employ=new Employ(getEmployNo, getEmployName, getEmployUsername, getEmployPassword, newEmployRole);
            employDao.update(employ);
            JOptionPane.showMessageDialog(this, "員工資料修改成功!");
        }      
        loadEmployData();
    }

    // 刪除
    private void deleteEmploy() {
        int selectedRow = employTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要刪除的員工資料!");
            return;
        }

        String employNo = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除員工資料 " + employNo + " 嗎?", "確認刪除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        	List<Porder> porderList=new ArrayList();
        	porderList=porderDao.readByEmployno(employNo);
            if (!porderList.isEmpty())
            {
            	JOptionPane.showMessageDialog(this, "無法刪除員工, 因為有訂單!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            	return ;
            }        
            
            if (loingEmployno.equals(employNo))
            {
            	JOptionPane.showMessageDialog(this, "無法刪除自己!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            	return ;
            }
            employDao.delete(employNo);
            JOptionPane.showMessageDialog(this, "員工資料刪除成功!");
            loadEmployData();
        }
    }

    // 載入資料
    private void loadEmployData() {
        tableModel.setRowCount(0); // 清空表格
        List<Employ> employs = employDao.readAll();
        for (Employ employ : employs) {
            tableModel.addRow(new Object[]{employ.getEmployno(), employ.getName(), employ.getUsername(), employ.getPassword(), employ.getRole()});
        }
    }

    private boolean isValidInteger(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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