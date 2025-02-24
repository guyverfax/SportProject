package controller.porder;

import dao.EmployDao;
import dao.impl.EmployDaoImpl;
import dao.MemberDao;
import dao.impl.MemberDaoImpl;
import model.Employ;
import model.Member;
import util.Tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Font;
import java.awt.GridLayout;

public class PorderCreate1 extends JFrame {
	
	private JTextField txtSearch;
    private JTable table;
    private JLabel lblSelectedMember;
    
    Employ employ=(Employ) Tool.read("employ.txt");
    private String loginEmployno=employ.getEmployno();
    private String loginEmployname=employ.getName();
    private String loginEmployrole=employ.getRole();
    
    Member member; 
    private String selectMemberno;
    private String selectMembername;
    private String selectMemberphone;
    private String selectMemberaddress;
    
    private MemberDao memberDao = new MemberDaoImpl();

    public PorderCreate1() {
        setTitle("新增訂單 - 選擇客戶");
        setSize(600, 373);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 586, 20);
        topPanel.setBackground(new Color(255, 255, 204));
        getContentPane().add(topPanel);
        topPanel.setLayout(new GridLayout(1, 0, 0, 0));
        
        JLabel lblSelectMember = new JLabel("1.選擇客戶");
        lblSelectMember.setBackground(Color.GREEN);
        lblSelectMember.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectMember.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblSelectMember.setOpaque(true);
        topPanel.add(lblSelectMember);
        
        JLabel lblSelectProduct = new JLabel("2.挑選產品");
        lblSelectProduct.setBackground(Color.LIGHT_GRAY);
        lblSelectProduct.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectProduct.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblSelectProduct.setOpaque(true);
        topPanel.add(lblSelectProduct);
        
        JLabel lblCheckOut = new JLabel("3.成立訂單");
        lblCheckOut.setBackground(Color.LIGHT_GRAY);
        lblCheckOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblCheckOut.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblCheckOut.setOpaque(true);
        topPanel.add(lblCheckOut);

        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(0, 20, 585, 50);
        searchPanel.setBackground(SystemColor.inactiveCaptionBorder);
        getContentPane().add(searchPanel);
        searchPanel.setLayout(null);
        
        JLabel lblKeyword = new JLabel("客戶關鍵字");
        lblKeyword.setBounds(10, 10, 73, 31);
        lblKeyword.setForeground(Color.BLACK);
        lblKeyword.setBackground(Color.WHITE);
        lblKeyword.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        searchPanel.add(lblKeyword);
                
        txtSearch = new JTextField();
        txtSearch.setBounds(80, 10, 90, 30);
        txtSearch.setFont(new Font("微軟正黑體", Font.BOLD, 13));
        searchPanel.add(txtSearch);
        
        JButton btnSearch = new JButton("搜尋");
        btnSearch.setBounds(180, 10, 65, 31);
        btnSearch.setHorizontalAlignment(SwingConstants.LEFT);
        btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        searchPanel.add(btnSearch);
                
        // 設定搜尋按鈕點擊事件
        btnSearch.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		searchMembers();
            }
        });
        
        JLabel lblEmployno = new JLabel("員工編號: ");
        lblEmployno.setBounds(260, 10, 117, 31);
        lblEmployno.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        searchPanel.add(lblEmployno);
        lblEmployno.setText("員工編號: "+loginEmployno);
        
        JLabel lblEmployname = new JLabel("員工名字: ");
        lblEmployname.setBounds(340, 10, 117, 31);
        lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        searchPanel.add(lblEmployname);
        lblEmployname.setText("員工名字: "+loginEmployname);
        
        JLabel lblTime = new JLabel("New label");
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblTime.setBounds(460, 18, 128, 15);
        searchPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
        
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(0, 68, 600, 224);
        getContentPane().add(tablePanel);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"客戶編號", "姓名", "電話", "地址"})
        {
        	//惟讀
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 10, 585, 213);
        tablePanel.add(scrollPane);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(0, 290, 585, 50);
        bottomPanel.setBackground(SystemColor.inactiveCaptionBorder);
        getContentPane().add(bottomPanel);

        lblSelectedMember = new JLabel("選擇的客戶: 無");
        lblSelectedMember.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblSelectedMember.setBounds(10, 10, 300, 30);
        bottomPanel.add(lblSelectedMember);

        // 設定點擊 JTable 事件
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String memberNo = table.getValueAt(selectedRow, 0).toString();
                String name = table.getValueAt(selectedRow, 1).toString();
                String phone = table.getValueAt(selectedRow, 2).toString();
                String address = table.getValueAt(selectedRow, 3).toString();
                lblSelectedMember.setText("選擇的客戶: " + memberNo + " - " + name); 
                member=new Member(memberNo,name,phone,address);                
            }
        });
        
        JButton btnNext = new JButton("下一步");
        btnNext.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnNext.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (lblSelectedMember.getText().equals("選擇的客戶: 無"))
        		{
        			JOptionPane.showMessageDialog(PorderCreate1.this, "請選擇客戶！", "錯誤", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
                Tool.save(member,"member.txt");
                PorderCreate2 frame=new PorderCreate2();
                frame.setVisible(true);
                dispose();        		
        	}
        });
        btnNext.setBounds(372, 10, 80, 30);
        bottomPanel.add(btnNext);
        
        JButton btnBack = new JButton("回到主頁");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderCreate1.this);
        		dispose();
        	}
        });
        btnBack.setBounds(486, 10, 87, 30);
        bottomPanel.add(btnBack);
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
    }

    private void searchMembers() {
        String keyword = txtSearch.getText().trim();
        List<Member> members = memberDao.readByKeyword(keyword);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Member member : members) {
            model.addRow(new Object[]{member.getMemberno(), member.getName(), member.getPhone(), member.getAddress()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PorderCreate1().setVisible(true));
    }
}
