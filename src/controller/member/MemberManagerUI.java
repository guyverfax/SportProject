package controller.member;

import dao.impl.MemberDaoImpl;
import dao.impl.PorderDaoImpl;
import dao.MemberDao;
import dao.PorderDao;
import dao.impl.ProductDaoImpl;
import dao.ProductDao;
import model.Member;
import model.Porder;
import model.Product;
import util.Tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MemberManagerUI extends JFrame {
    private JTextField txtMemberNo, txtMemberPhone, txtMemberName, txtMemberAddress;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private MemberDao memberDao=new MemberDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MemberManagerUI().setVisible(true);
        });
    }
    
    public MemberManagerUI() {
        setTitle("客戶管理");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // 上方 JPanel (輸入區域)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(153, 255, 255));
        topPanel.setBounds(10, 10, 560, 120);
        topPanel.setLayout(null);
        getContentPane().add(topPanel);

        JLabel lblMemberNo = new JLabel("客戶編號:");
        lblMemberNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblMemberNo.setBounds(10, 10, 80, 25);
        topPanel.add(lblMemberNo);
        txtMemberNo = new JTextField();
        txtMemberNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtMemberNo.setBounds(74, 10, 100, 25);
        topPanel.add(txtMemberNo);

        JLabel lblMemberPhone = new JLabel("客戶電話:");
        lblMemberPhone.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblMemberPhone.setBounds(254, 10, 80, 25);
        topPanel.add(lblMemberPhone);
        txtMemberPhone = new JTextField();
        txtMemberPhone.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtMemberPhone.setBounds(321, 10, 100, 25);
        topPanel.add(txtMemberPhone);
        JLabel lblNoNotice2 = new JLabel("例: 0911111111");
        lblNoNotice2.setForeground(Color.RED);
        lblNoNotice2.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblNoNotice2.setBounds(431, 16, 100, 15);
        topPanel.add(lblNoNotice2);


        JLabel lblMemberName = new JLabel("客戶名字:");
        lblMemberName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblMemberName.setBounds(10, 45, 80, 25);
        topPanel.add(lblMemberName);
        txtMemberName = new JTextField();
        txtMemberName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtMemberName.setBounds(74, 45, 100, 25);
        topPanel.add(txtMemberName);

        JLabel lblMemberAddress = new JLabel("客戶地址:");
        lblMemberAddress.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblMemberAddress.setBounds(254, 40, 80, 25);
        topPanel.add(lblMemberAddress);
        txtMemberAddress = new JTextField();
        txtMemberAddress.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtMemberAddress.setBounds(321, 40, 229, 25);
        topPanel.add(txtMemberAddress);

        JLabel lblNoNotice1 = new JLabel("例: M111");
        lblNoNotice1.setForeground(Color.RED);
        lblNoNotice1.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblNoNotice1.setBounds(184, 16, 50, 15);
        topPanel.add(lblNoNotice1);
        
        JButton btnAdd = new JButton("新增客戶");
        btnAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnAdd.setBounds(10, 80, 100, 25);
        topPanel.add(btnAdd);

        JButton btnUpdate = new JButton("修改客戶");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(120, 80, 100, 25);
        topPanel.add(btnUpdate);

        JButton btnDelete = new JButton("刪除客戶");
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(230, 80, 100, 25);
        topPanel.add(btnDelete);
        
        // 中間 JPanel (表格區域)
        JPanel middlePanel = new JPanel();
        middlePanel.setBounds(10, 140, 560, 250);
        middlePanel.setLayout(new BorderLayout());
        getContentPane().add(middlePanel);

        tableModel = new DefaultTableModel(new String[]{"客戶編號", "客戶名字", "客戶電話", "客戶地址"}, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(tableModel);
        memberTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // 客戶編號
        memberTable.getColumnModel().getColumn(1).setPreferredWidth(50); // 客戶名字
        memberTable.getColumnModel().getColumn(2).setPreferredWidth(50); // 客戶電話
        memberTable.getColumnModel().getColumn(3).setPreferredWidth(150); // 客戶地址
        memberTable.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        middlePanel.add(new JScrollPane(memberTable), BorderLayout.CENTER);

        // 下方 JPanel (額外功能按鈕)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(153, 255, 255));
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
        		Tool.goMainUI(MemberManagerUI.this);
        	}
        });
        btnBack.setBounds(442, 10, 108, 30);
        bottomPanel.add(btnBack);

        loadMemberData(); // 載入資料

        // 新增
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });

        // 修改
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMember();
            }
        });

        // 刪除
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        // 監聽事件：重新載入產品資料
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadMemberData();
            }
        });
    }

    // 新增產品
    private void addMember() {
        String memberNo = txtMemberNo.getText().trim();
        String memberName = txtMemberName.getText().trim();
        String memberPhone = txtMemberPhone.getText().trim();
        String memberAddress = txtMemberAddress.getText().trim();

        // 驗證輸入
        if (memberNo.isEmpty() || memberName.isEmpty() || memberName.isEmpty() || memberAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請填寫所有欄位!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidMemberno(memberNo)) {
        	JOptionPane.showMessageDialog(this, "客戶編號需4位字元,開頭M和3位數字!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidMemberphone(memberPhone)) {
        	JOptionPane.showMessageDialog(this, "客戶電話需10位數字,開頭09!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (memberDao.isExitsByMemberno(memberNo))
        {
        	JOptionPane.showMessageDialog(this, "客戶編號重覆!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Member member = new Member(memberNo, memberName, memberPhone, memberAddress);
        memberDao.create(member);
        JOptionPane.showMessageDialog(this, "客戶新增成功!");
        loadMemberData();
    }

    // 修改產品
    private void updateMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要修改的客戶!");
            return;
        }

        String newMemberNo = (String) tableModel.getValueAt(selectedRow, 0);
        String newMemberName = JOptionPane.showInputDialog("輸入新的客戶名稱:", tableModel.getValueAt(selectedRow, 1));
        String newMemberPhone = JOptionPane.showInputDialog("輸入新的客戶電話:", tableModel.getValueAt(selectedRow, 2));
        String newMemberAddress = JOptionPane.showInputDialog("輸入新的客戶地址:", tableModel.getValueAt(selectedRow, 3));

        if (newMemberName == null || newMemberName.trim().isEmpty() || 
            newMemberPhone == null || newMemberPhone.trim().isEmpty() || 
            newMemberAddress == null || newMemberAddress.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有欄位不可為空!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidMemberphone(newMemberPhone)) {
        	JOptionPane.showMessageDialog(this, "客戶電話需10位數字,開頭09!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Member updateMember = new Member(newMemberNo, newMemberName, newMemberPhone, newMemberAddress);
        memberDao.update(updateMember);
        JOptionPane.showMessageDialog(this, "客戶資料修改成功!");
        loadMemberData();
    }

    // 刪除產品
    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要刪除的客戶資料!");
            return;
        }

        String memberNo = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除客戶資料 " + memberNo + " 嗎?", "確認刪除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        	List<Porder> porderList=new ArrayList();
        	porderList=porderDao.readByMemberno(memberNo);
            if (!porderList.isEmpty())
            {
            	JOptionPane.showMessageDialog(this, "無法刪除產品, 因為有訂單!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            	return ;
            }        
            memberDao.delete(memberNo);
            JOptionPane.showMessageDialog(this, "客戶資料刪除成功!");
            loadMemberData();
        }
    }

    // 載入產品資料
    private void loadMemberData() {
        tableModel.setRowCount(0); // 清空表格
        List<Member> members = memberDao.readAll();
        for (Member member : members) {
            tableModel.addRow(new Object[]{member.getMemberno(), member.getName(), member.getPhone(), member.getAddress()});
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
    
    private boolean isValidMemberno(String memberno) {
        String regex = "^M\\d{3}$";
        return memberno.matches(regex);
    }
    
    private boolean isValidMemberphone(String memberphone) {
        String regex = "^09\\d{8}$";
        return memberphone.matches(regex);
    }
}