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
import dao.PorderDetailDao;
import dao.PorderSummaryDao;
import dao.ProductDao;
import dao.impl.EmployDaoImpl;
import dao.impl.MemberDaoImpl;
import dao.impl.PorderDaoImpl;
import dao.impl.PorderDetailDaoImpl;
import dao.impl.PorderSummaryDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Employ;
import model.Porder;
import model.PorderDetail;
import model.PorderSummary;
import model.Product;
import util.Tool;

import javax.swing.table.DefaultTableModel;

import controller.portal.LoginUI;

public class PorderUpdateUI extends JFrame {
	
    //登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginEmployRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
	private String loingEmployname=loginEmploy.getName();
	
    private JTextField txtOrderNo;
    private String porderNo;
    private String memberNo;
    private String employNo;
    
    private JButton btnSearch, btnUpdate, btnDelete;
    private JTable porderTable;
    private DefaultTableModel tableModel;
    private JPanel topPanel, middlePanel, bottomPanel;
    private JLabel lblTotalAmount;
    private JButton btnBack;
    private JLabel lblEmployno;
    private JLabel lblEmployname;
    private JLabel lblTime;

    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();
    private PorderSummaryDao pordersummaryDao= new PorderSummaryDaoImpl();
    private PorderDetailDao porderdetailDao=new PorderDetailDaoImpl();
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PorderUpdateUI().setVisible(true);
        });
    }
    
    public PorderUpdateUI() {
        setTitle("編輯訂單");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(817, 453);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        // 上方搜尋區域
        topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 255, 204));
        topPanel.setBounds(0, 0, 800, 50);
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
        lblTime.setBounds(601, 10, 156, 31);
        topPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();
        
        
        // 中間表格區域
        middlePanel = new JPanel();
        middlePanel.setBounds(0, 60, 800, 295);
        getContentPane().add(middlePanel);
        middlePanel.setLayout(null);

        tableModel = new DefaultTableModel(new Object[][]{}, 
    			new String[]{"訂單編號",
    						"訂單日期",
    						"客戶編號", 
    						"客戶名字", 
    						"員工編號", 
    						"員工名字", 
    						"產品編號",
    						"產品名字",
    						"單價",
    						"數量",
    						"小計",
    						"庫存量"})
		{
    		//惟讀
    		@Override
    		public boolean isCellEditable(int row, int column) {
    			return false;
    		}
		};
		
        porderTable = new JTable(tableModel);
        porderTable.setFont(new Font("微軟正黑體", Font.PLAIN, 11));
        porderTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        porderTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(porderTable);
        scrollPane.setBounds(0, 0, 800, 285);
        middlePanel.add(scrollPane);
        
        // 底部按鈕區域
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 255, 204));
        bottomPanel.setBounds(0, 356, 800, 63);
        bottomPanel.setLayout(null);
        getContentPane().add(bottomPanel);

        lblTotalAmount = new JLabel("總金額: 0");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTotalAmount.setBounds(10, 20, 126, 30);
        bottomPanel.add(lblTotalAmount);      
        
        btnUpdate = new JButton("修改品項");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(100, 20, 100, 30);
        bottomPanel.add(btnUpdate);

        btnDelete = new JButton("刪除品項");
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(220, 20, 100, 30);
        bottomPanel.add(btnDelete);
               
        JButton btnAdd = new JButton("增加品項");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		addItem();
        	}
        });
        btnAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnAdd.setBounds(340, 20, 100, 30);
        bottomPanel.add(btnAdd);

        btnBack = new JButton("回主頁");
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnBack.setBounds(649, 20, 100, 30);
        bottomPanel.add(btnBack);
        
        // 尋找
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                String orderNo = txtOrderNo.getText().trim();
                if (orderNo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "請輸入訂單號碼");
                    return;
                }
                
                loadPorderData();                         
              
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
	            String porderno=(String) tableModel.getValueAt(selectedRow, 0);
	            String orderdate=(String) tableModel.getValueAt(selectedRow, 1);
	            String memberno = (String) tableModel.getValueAt(selectedRow, 2);
	            String membername = (String) tableModel.getValueAt(selectedRow, 3);
	            String employno = (String) tableModel.getValueAt(selectedRow, 4);
	            String employname = (String) tableModel.getValueAt(selectedRow, 5);	
	            String productno = (String) tableModel.getValueAt(selectedRow, 6);
	            String productname = (String) tableModel.getValueAt(selectedRow, 7);	
	            int price = (int) tableModel.getValueAt(selectedRow, 8);
	            int amount = (int) tableModel.getValueAt(selectedRow, 9);
	            int subtotal = (int) tableModel.getValueAt(selectedRow, 10);
	            int quantity = (int) tableModel.getValueAt(selectedRow, 11);
	            
	            Integer[] options = {1,2,3,4,5,6,7,8,9,10};
	            JComboBox<Integer> amountComboBox = new JComboBox<>(options);
	            amountComboBox.setSelectedItem(amount);
	                        
	            // 建立對話框內容
	            Object[] fields = {
	                "訂號編號: "+porderno,
	                "產品編號: "+productno,
	                "產品名稱: "+productname,
	                "產品單價: "+price, 
	            	"購買數量:",amountComboBox,
	            	"庫存數量: "+quantity, 
	            };
	            
 	            // 顯示 JOptionPane，讓使用者修改數值
	            int result = JOptionPane.showConfirmDialog(null, fields, "修改數量",
	                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	            
	            // 如果使用者按下 OK，則更新  
	            if (result == JOptionPane.OK_OPTION) {
	                int newAmount = (Integer) (amountComboBox.getSelectedItem());
	                
	                //判斷庫存是否充足
	                if (newAmount > quantity)
	                {
	                	JOptionPane.showMessageDialog(null,"數量大於庫存，請重新調整！", "錯誤", JOptionPane.ERROR_MESSAGE);
	                	return;
	                }
	                
	                porderDao.updateAmount(porderno, productno, newAmount);
	                productDao.update(productno, quantity+amount-newAmount);
	                
	                loadPorderData();
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
                                        
                    // 取得原始數據
    	            String porderno=(String) tableModel.getValueAt(selectedRow, 0);
    	            String orderdate=(String) tableModel.getValueAt(selectedRow, 1);
    	            String memberno = (String) tableModel.getValueAt(selectedRow, 2);
    	            String membername = (String) tableModel.getValueAt(selectedRow, 3);
    	            String employno = (String) tableModel.getValueAt(selectedRow, 4);
    	            String employname = (String) tableModel.getValueAt(selectedRow, 5);	
    	            String productno = (String) tableModel.getValueAt(selectedRow, 6);
    	            String productname = (String) tableModel.getValueAt(selectedRow, 7);	
    	            int price = (int) tableModel.getValueAt(selectedRow, 8);
    	            int amount = (int) tableModel.getValueAt(selectedRow, 9);
    	            int subtotal = (int) tableModel.getValueAt(selectedRow, 10);
    	            int quantity = (int) tableModel.getValueAt(selectedRow, 11);
    	            
                    porderDao.deletePorderItem(porderno, productno);
    	            productDao.update(productno, quantity+amount);
                                       
                    loadPorderData();

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
    
    private void addItem() {
    	
    	if (tableModel==null || tableModel.getRowCount()==0)
    	{
    		JOptionPane.showMessageDialog(null, "請先找到訂單再新增品項吧");
    		return;
    	}
    	
    	JComboBox<String> jcbProduct = new JComboBox<>(productDao.readByProductnoAndProductname());
        JComboBox<Integer> jcbAmount = new JComboBox<>(new Integer[] {1,2,3,4,5,6,7,8,9,10});
        
        //建立對話框內容
        Object[] fields = {
            "產品編號: ",jcbProduct,
            "產品數量: ",jcbAmount,
        };
        
        // 顯示 JOptionPane，讓使用者修改數值
        int result = JOptionPane.showConfirmDialog(null, fields, "增加品項",
                     JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.CANCEL_OPTION)
        {
        	return;
        }     
        
        //檢查產品編號是否重複
        String selectProduct=(String) jcbProduct.getSelectedItem();
        String selectProductno=selectProduct.split(" - ")[0];
        String selectProductname=selectProduct.split(" - ")[1];
        boolean isProductnoDuplicate = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingProductno = (String) tableModel.getValueAt(i, 6);
            if (existingProductno.equals(selectProductno)) {
                isProductnoDuplicate = true;
                break;
            }
        }
        
        //若產品編號不重覆，才能新增
        if (isProductnoDuplicate) {
            JOptionPane.showMessageDialog(null, "己有相同產品，請修改數量");
            return;
        }
        
        //檢查庫存量
        int newAmount=(int)jcbAmount.getSelectedItem();
        int quantity=productDao.readByProductno(selectProductno).getQuantity();
        if (newAmount>quantity)
        {
        	 JOptionPane.showMessageDialog(null, "庫存量不足");
             return;        
        }
        
        //存入資料庫
        Porder addPorder=new Porder();
        addPorder.setPorderno(porderNo);
        addPorder.setProductno(selectProductno);
        addPorder.setMemberno(memberNo);
        addPorder.setEmployno(employNo);
        addPorder.setAmount((int)jcbAmount.getSelectedItem());
        
        productDao.update(selectProductno, quantity-newAmount);
        porderDao.create(addPorder);
        
        loadPorderData();
        calculateTotalAmount();
    }

    private void loadPorderData() {
        tableModel.setRowCount(0);
        List<PorderDetail> porderDetails=new ArrayList();
        if ("Admin".equals(loginEmployRole)) {
            porderDetails = porderdetailDao.readAllByPorderno(txtOrderNo.getText());
        } else {
            porderDetails = porderdetailDao.readAllByPorderno(txtOrderNo.getText(), loingEmployno);
        }
        for (PorderDetail p : porderDetails) {
            tableModel.addRow(new Object[]{
            		p.getPorderno(), 
            		p.getOrderdate(),
            		p.getMemberno(), 
            		p.getMembername(), 
            		p.getEmployno(), 
            		p.getEmployname(), 
            		p.getProductno(), 
            		p.getProductname(),
            		p.getPrice(),
            		p.getAmount(),
            		p.getSubtotal(),
            		p.getStockquantity()
            		});
           porderNo=p.getPorderno();
           memberNo=p.getMemberno();
           employNo=p.getEmployno();
        }
     }
    
    public void calculateTotalAmount()
    {
    	// 重新計算總金額
        int totalAmount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalAmount += (int) tableModel.getValueAt(i, 10);
        }
        lblTotalAmount.setText("總金額: " + totalAmount);
    }
}