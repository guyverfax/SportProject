//未完成, 只到 AddItem 的判斷
package testui;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.PorderDao;
import dao.ProductDao;
import dao.impl.PorderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Employ;
import model.Porder;
import model.Product;
import util.Tool;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class PorderUpdateUIv2 extends JFrame {

	private static final long serialVersionUID = 1L;
		
	//登入資訊
    private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginEmployRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();
		
	//Member no
	String memberNo;
	String memberName;
	
	private JPanel middlePanel,bottomPanel;	
	private JTable porderTable;
    private DefaultTableModel tableModel;
    private JButton btnAddItem, btnEditItem, btnDeleteItem, btnSaveChanges;
    private PorderDao porderDao = new PorderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private String porderNo;  // 當前編輯的訂單號碼
    private JPanel topPanel;
    private JLabel lblOrderNo;
    private JTextField txtOrderNo;
    private JButton btnSearch;
    private JLabel lblEmployno;
    private JLabel lblEmployname;
    private JLabel lblTime;
    private JLabel lblTotalAmount;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PorderUpdateUIv2 frame = new PorderUpdateUIv2();
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
	public PorderUpdateUIv2() {
		setTitle("編輯訂單v2");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(743, 453);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
		
		topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(new Color(255, 255, 204));
        topPanel.setBounds(0, 0, 780, 50);
        getContentPane().add(topPanel);
        
        lblOrderNo = new JLabel("訂單號碼:");
        lblOrderNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblOrderNo.setBounds(10, 15, 74, 25);
        topPanel.add(lblOrderNo);
        
        lblEmployno = new JLabel("員工編號: <dynamic>");
        lblEmployno.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployno.setBounds(351, 11, 117, 31);
        topPanel.add(lblEmployno);
        lblEmployno.setText("員工編號: "+loingEmployno);
        
        lblEmployname = new JLabel("員工名字: <dynamic>");
        lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblEmployname.setBounds(457, 11, 117, 31);
        topPanel.add(lblEmployname);
        lblEmployname.setText("員工名字: "+loingEmployname);
        
        lblTime = new JLabel("現在時間:");
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTime.setBounds(601, 10, 123, 31);
        topPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
        
		txtOrderNo = new JTextField();
        txtOrderNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtOrderNo.setBounds(70, 15, 150, 25);
        topPanel.add(txtOrderNo);
        
        btnSearch = new JButton("搜尋");
        btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnSearch.setBounds(240, 15, 60, 25);
        topPanel.add(btnSearch);
        
        middlePanel=new JPanel();
        middlePanel.setBounds(0, 60, 729, 272);
        middlePanel.setLayout(null);
        getContentPane().add(middlePanel);
	
		// 📌 訂單表格
        porderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(porderTable);
        scrollPane.setBounds(5, 10, 709, 253);
        middlePanel.add(scrollPane);
        
        
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 204));
        bottomPanel.setBounds(0, 344, 780, 62);
        bottomPanel.setLayout(null);
        getContentPane().add(bottomPanel);
 
        lblTotalAmount = new JLabel("總金額: 0");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTotalAmount.setBounds(10, 10, 90, 30);
        bottomPanel.add(lblTotalAmount);
       
        btnAddItem = new JButton("新增品項");
        btnAddItem.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnAddItem.setBounds(95, 10, 90, 30);
        bottomPanel.add(btnAddItem);
        
        btnEditItem = new JButton("修改品項");
        btnEditItem.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnEditItem.setBounds(195, 10, 90, 30);
        bottomPanel.add(btnEditItem);
        
        btnDeleteItem = new JButton("刪除品項");
        btnDeleteItem.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDeleteItem.setBounds(295, 10, 90, 30);
        bottomPanel.add(btnDeleteItem);
        
        btnSaveChanges = new JButton("儲存修改");
        btnSaveChanges.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnSaveChanges.setBounds(395, 10, 90, 30);
        bottomPanel.add(btnSaveChanges);
        
        btnSearch.addActionListener(e -> searchPorderno());
       
        btnAddItem.addActionListener(e -> addItem());
        btnEditItem.addActionListener(e -> editItem());
        btnDeleteItem.addActionListener(e -> deleteItem());
        btnSaveChanges.addActionListener(e -> saveChanges());
        	
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
    
    // 搜尋 
    private void searchPorderno()
    {
    	 String orderNo = txtOrderNo.getText().trim();
         //for testing
         orderNo="O2025022010532701";
         
         if (orderNo.isEmpty()) {
             JOptionPane.showMessageDialog(null, "請輸入訂單號碼");
             return;
         }
         
         List<Porder> orders=new ArrayList();
         if (loginEmployRole.equals("Admin"))
         {
         	orders=porderDao.readByPorderno(orderNo);
         }
         else
         {
         	orders=porderDao.readByPordernoAndEmployno(orderNo, loingEmployno);
         }
         
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
             //取得 MemberNo 和 memberName for AddItem()
             memberNo=order.getMemberno();
             memberName=order.getMemberno();
         }
         porderTable.setModel(tableModel);
 		 porderTable.getColumnModel().getColumn(0).setPreferredWidth(180);  // 訂單號碼
 		 calculateTotalAmount();
    }
    
    //
    private void addItem() {
        String productNo = JOptionPane.showInputDialog("請輸入產品編號：");
        if (productNo == null || productNo.trim().isEmpty()) return;

        Product product = productDao.readByProductno(productNo);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "產品不存在！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }        	 
        // 輸入品項
        JPanel panel=new JPanel();
        panel.add(new JLabel("新增品項"));

        JTextField panelProductno=new JTextField();
        panel.add(new JLabel("請輸入產品編號:"));
        panel.add(panelProductno);
        
        // 輸入數量
        JComboBox panelAmount = new JComboBox();
        panelAmount.setModel(new DefaultComboBoxModel(new Integer[] {1,2,3,4,5,6,7,9,10}));
        panel.add(new JLabel("請選擇數量:"));
        panel.add(panelAmount);
        
        //使用JOptionPane.showConfirmDialog 來顯示對話框
        int result = JOptionPane.showConfirmDialog(null, panel, "選擇數量", 
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int selectedQuantity = (int) panelAmount.getSelectedItem();
            System.out.println("選擇的數量：" + selectedQuantity);
        } else {
            System.out.println("取消選擇");
            return ;
        }
        
        //檢查產品編號是否重複
        boolean isProductnoDuplicate = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingProductno = (String) tableModel.getValueAt(i, 1);
            if (existingProductno.equals(productNo)) {
                isProductnoDuplicate = true;
                break;
            }
        }
        
        // 若產品編號不重覆，才能新增
        if (isProductnoDuplicate) {
            JOptionPane.showMessageDialog(null, "己有相同產品，請修改數量");
            return;  
        }
        
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("請輸入數量："));      
        
        // just for testing
        String orderNo="O2025022010532701";
        int subtotal = quantity * product.getPrice();
        tableModel.addRow(new Object[]{
         		orderNo,
         		product.getProductno(), 
         		product.getProductname(), 
         		memberNo, 
         		loingEmployno,
         		product.getPrice(), 
         		quantity, 
         		subtotal,
         		product.getQuantity()});
    }

    //
    private void editItem() {
        int selectedRow = porderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要修改的品項！", "錯誤", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newQuantity = JOptionPane.showInputDialog("請輸入新數量：");
        if (newQuantity != null && !newQuantity.trim().isEmpty()) {
            tableModel.setValueAt(Integer.parseInt(newQuantity), selectedRow, 2);
        }
    }
    
    //
    private void deleteItem() {
        int selectedRow = porderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要刪除的品項！", "錯誤", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.removeRow(selectedRow);
    }
    
    // 
    private void saveChanges() {
        int confirm = JOptionPane.showConfirmDialog(this, "確定要儲存變更嗎？", "確認", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // 刪除舊訂單
        if (!porderDao.deleteOrderByPorderNo(porderNo)) {
            JOptionPane.showMessageDialog(this, "刪除舊訂單失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 取得 JTable 的新訂單數據
        List<Porder> newOrders = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String productNo = tableModel.getValueAt(i, 0).toString();
            int amount = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
            newOrders.add(new Porder(porderNo, productNo, "M001", "E001", amount));
        }

        // 插入新的訂單品項，並更新庫存
        if (!porderDao.createUpdateStock(newOrders)) {
            JOptionPane.showMessageDialog(this, "儲存訂單失敗！", "錯誤", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "訂單更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // 關閉視窗
        }
    }
}
