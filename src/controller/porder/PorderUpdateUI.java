package controller.porder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import dao.EmployDao;
import dao.MemberDao;
import dao.PorderDao;
import dao.ProductDao;
import dao.impl.EmployDaoImpl;
import dao.impl.MemberDaoImpl;
import dao.impl.PorderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Employ;
import model.Porder;
import model.PorderSummary;
import model.Product;
import util.Tool;

import javax.swing.table.DefaultTableModel;

import controller.portal.LoginUI;

public class PorderUpdateUI extends JFrame {
	
    //登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	
    private JTextField txtOrderNo;
    private JButton btnSearch, btnUpdate, btnDelete;
    private JTable porderTable;
    private DefaultTableModel tableModel;
    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private JPanel topPanel, middlePanel, bottomPanel;
    private JLabel lblTotalAmount;
    private JButton btnBack;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PorderUpdateUI().setVisible(true);
        });
    }
    
    public PorderUpdateUI() {
        setTitle("編輯訂單");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 453);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        // 上方搜尋區域
        topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 255, 204));
        topPanel.setBounds(0, 0, 780, 50);
        topPanel.setLayout(null);
        getContentPane().add(topPanel);

        JLabel lblOrderNo = new JLabel("訂單號碼:");
        lblOrderNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblOrderNo.setBounds(10, 15, 74, 25);
        topPanel.add(lblOrderNo);

        txtOrderNo = new JTextField();
        txtOrderNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtOrderNo.setBounds(70, 15, 150, 25);
        topPanel.add(txtOrderNo);

        btnSearch = new JButton("搜尋");
        btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnSearch.setBounds(240, 15, 60, 25);
        topPanel.add(btnSearch);
        
        JLabel lblLogin = new JLabel("登入帳號:<dynamic>");
        lblLogin.setBounds(310, 20, 122, 15);
        topPanel.add(lblLogin);
        lblLogin.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
        lblLogin.setText("登入帳號:"+loingEmployusername);
        
        JLabel lblTime = new JLabel("現在時間:");
        lblTime.setBounds(430, 20, 196, 15);
        topPanel.add(lblTime);
        lblTime.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
        
        
        // 中間表格區域
        middlePanel = new JPanel();
        middlePanel.setBounds(0, 60, 780, 295);
        getContentPane().add(middlePanel);
        middlePanel.setLayout(null);

        porderTable = new JTable();
        porderTable.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(porderTable);
        scrollPane.setBounds(0, 0, 760, 285);
        middlePanel.add(scrollPane);
        
        // 底部按鈕區域
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 204));
        bottomPanel.setBounds(0, 356, 780, 63);
        bottomPanel.setLayout(null);
        getContentPane().add(bottomPanel);

        btnUpdate = new JButton("修改");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(100, 20, 60, 30);
        bottomPanel.add(btnUpdate);

        btnDelete = new JButton("刪除");
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(180, 20, 60, 30);
        bottomPanel.add(btnDelete);
        
        lblTotalAmount = new JLabel("總金額: 0");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTotalAmount.setBounds(10, 20, 126, 30);
        bottomPanel.add(lblTotalAmount);      
        
        btnBack = new JButton("回主頁");
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnBack.setBounds(458, 20, 100, 30);
        bottomPanel.add(btnBack);        

        // 尋找
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                String orderNo = txtOrderNo.getText().trim();
                if (orderNo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "請輸入訂單號碼");
                    return;
                }
                
                String sql = "select * from porder WHERE 1=1";
        	    List<Object> params = new ArrayList<>();
        	    
        	    sql += " AND porderno LIKE ?";
    	        params.add("%" + orderNo + "%");
    	        
    	        // for testing if loginRole="Admin";
    	        if (loginRole.equals("User"))
    		    {
    		    	sql += " AND employno LIKE ?";
    		        params.add("%" + loingEmployno + "%");
    		    }
    	                	    
    	        List<Porder> orders = porderDao.readAll(sql, params);
                //List<Porder> orders = porderDao.readAllByPorderno(orderNo);
                
                if (orders.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "找不到該訂單");
                    return;
                }      
                
                tableModel = new DefaultTableModel(new String[]{"訂單號碼", "產品編號", "產品名稱", "客戶編號", "員工編號", "單價", "數量", "小計", "庫存數量"}, 0) {
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                int totalAmount = 0;
                for (Porder order : orders) {
                	Product product=productDao.readByProductno(order.getProductno());
                    int subtotal = order.getAmount() * product.getPrice();
                    totalAmount += subtotal;
                    tableModel.addRow(new Object[]{
                    		order.getPorderno(), 
                    		order.getProductno(), 
                    		product.getProductname(), 
                    		order.getMemberno(), 
                    		order.getEmployno(),
                    		product.getPrice(), 
                    		order.getAmount(), 
                    		subtotal,
                    		product.getQuantity()});
                }
                porderTable.setModel(tableModel);
        		porderTable.getColumnModel().getColumn(0).setPreferredWidth(180);  // 訂單號碼
        		calculateTotalAmount();
            }
        });

        //修改
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = porderTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "請選擇要修改的訂單");
                    return;
                }
                //new PorderEditDialog(PorderEditUI.this, orderTable, selectedRow).setVisible(true);
	            // 取得原始數據
	            String porderno = (String) tableModel.getValueAt(selectedRow, 0);
	            String productno = (String) tableModel.getValueAt(selectedRow, 1);
	            String productname = (String) tableModel.getValueAt(selectedRow, 2);
	            String memberno = (String) tableModel.getValueAt(selectedRow, 3);
	            String employno = (String) tableModel.getValueAt(selectedRow, 4);	
	            int price = (int) tableModel.getValueAt(selectedRow, 5);
	            int amount = (int) tableModel.getValueAt(selectedRow, 6);
	            int subtotal = (int) tableModel.getValueAt(selectedRow, 7);
	            int quantity = (int) tableModel.getValueAt(selectedRow, 8);
	            
	            Integer[] options = {1,2,3,4,5,6,7,8,9,10};
	            JComboBox<Integer> amountComboBox = new JComboBox<>(options);
	            amountComboBox.setSelectedItem(amount);
	                        
	            // 建立對話框內容
	            Object[] fields = {
	                "訂號編號: "+porderno,
	                "產品編號: "+productno,
	                "產品名稱: "+productname,
	                "客戶編號: "+memberno,
	                "員工編號: "+employno,
	                "產品單價: "+price, 
	            	"購買數量:",amountComboBox,
	            	"金額小計: "+subtotal,
	            	"庫存數量: "+quantity, 
	            };
	            
 	            // 顯示 JOptionPane，讓使用者修改數值
	            int result = JOptionPane.showConfirmDialog(null, fields, "修改數量",
	                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	            
	            // 如果使用者按下 OK，則更新 JTable          
	            if (result == JOptionPane.OK_OPTION) {
	                int newAmount = (Integer) (amountComboBox.getSelectedItem());
	                
	                //判斷庫存是否充足
	                if (newAmount > quantity)
	                {
	                	JOptionPane.showMessageDialog(null,"數量大於庫存，請重新調整！", "錯誤", JOptionPane.ERROR_MESSAGE);
	                	return;
	                }
	                
	                tableModel.setValueAt(newAmount, selectedRow, 6);
	                
	                int newsubtotal = 
	                		newAmount * 
	                		productDao.readByProductno(productno).getPrice();
	                porderDao.updateAmount(porderno, productno, newAmount);
	                productDao.update(productno, quantity+amount-newAmount);
	                tableModel.setValueAt(newsubtotal, selectedRow, 7);
	                tableModel.setValueAt(productDao.readByProductno(productno).getQuantity(), selectedRow, 8);
	                
	                // 重新計算總金額
	                calculateTotalAmount();
	            }      
            }
        });
        
        //刪除
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = porderTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "請選擇要刪除的訂單項目");
                    return;
                }
                
                if (porderTable.getRowCount() == 1)
                {
                	JOptionPane.showMessageDialog(null, "只剩一列會變成空訂單, 請至刪除訂單頁面");
                	return;
                	
                }
                
                int confirm = JOptionPane.showConfirmDialog(null, "確定要刪除此項目嗎？", "確認刪除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.NO_OPTION) return;
                confirm = JOptionPane.showConfirmDialog(null, "再次確認:刪除無法回復喔!", "確認刪除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String orderNo = porderTable.getValueAt(selectedRow, 0).toString();
                    String productNo = porderTable.getValueAt(selectedRow, 1).toString();

                    // 從資料庫刪除 po
                    porderDao.deletePorderItem(orderNo, productNo);
                                        
                    // 從資料庫增加庫存數量 
                    int amount=(int) porderTable.getValueAt(selectedRow, 6);
                    Product newProduct=productDao.readByProductno(productNo);
                    int newQuantity=newProduct.getQuantity();
                    productDao.update(productNo, newQuantity+amount);
                                       
                    // 從 JTable 移除該行
                    DefaultTableModel model = (DefaultTableModel) porderTable.getModel();
                    model.removeRow(selectedRow);

                    // 重新計算總金額
                    calculateTotalAmount();
                    
                    JOptionPane.showMessageDialog(null, "項目刪除成功");
                }
            }
        });
        
        //回主頁
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderUpdateUI.this);
        	}
        });
    }
    
    public void calculateTotalAmount()
    {
    	// 重新計算總金額
        int totalAmount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalAmount += (int) tableModel.getValueAt(i, 7);
        }
        lblTotalAmount.setText("總金額: " + totalAmount);
    }
}