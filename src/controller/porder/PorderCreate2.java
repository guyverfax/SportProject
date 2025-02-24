package controller.porder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;
import java.awt.Font;
import java.awt.GridLayout;

import model.Employ;
import dao.EmployDao;
import dao.impl.EmployDaoImpl;
import dao.MemberDao;
import dao.ProductDao;
import dao.impl.MemberDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Member;
import model.Product;
import util.Tool;
import java.awt.Component;

public class PorderCreate2 extends JFrame{
	
	private JTextField txtSearch;
    private JTable productTable;
    private JTable orderTable;
    private JLabel lblTotalAmount; //顥示總金額
    private int totalAmount=0; //計算總金額
    
    //for selected product
    private Product selectedProduct; 
    private JLabel lblSelectedProduct;  
    private String selectedProductno;
    private String selectedProductname;
    private int selectedProductprice;
    private int selectedProductquantity;
    
    Employ employ=(Employ) Tool.read("employ.txt");
    private String infoEmployno=employ.getEmployno();
    private String infoEmployname=employ.getName();
    private String infoEmployrole=employ.getRole();
    
    Member member=(Member) Tool.read("member.txt");
    private String infoMemberno;
    private String infoMembername;
    private String infoMemberphone;
    private String infoMemberaddress;
    
    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PorderCreate2().setVisible(true));
    }
    
    public PorderCreate2() {
        setTitle("新增訂單 - 挑選產品");
        setSize(600, 521);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 586, 20);
        topPanel.setBackground(new Color(255, 255, 204));
        getContentPane().add(topPanel);
        topPanel.setLayout(new GridLayout(1, 0, 0, 0));
        
        JLabel lblSelectMember = new JLabel("1.選擇客戶");
        lblSelectMember.setBackground(Color.LIGHT_GRAY);
        lblSelectMember.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectMember.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblSelectMember.setOpaque(true);
        topPanel.add(lblSelectMember);
        
        JLabel lblSelectProduct = new JLabel("2.挑選產品");
        lblSelectProduct.setBackground(Color.GREEN);
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

        txtSearch = new JTextField();
        txtSearch.setBounds(80, 10, 90, 30);
        txtSearch.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        searchPanel.add(txtSearch);

        JButton btnSearch = new JButton("搜尋");
        btnSearch.setBounds(180, 10, 80, 30);
        btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        searchPanel.add(btnSearch);
        
        JLabel lblKeyword = new JLabel("編號或名稱");
        lblKeyword.setBounds(10, 15, 66, 20);
        lblKeyword.setForeground(Color.BLACK);
        lblKeyword.setBackground(Color.WHITE);
        lblKeyword.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        searchPanel.add(lblKeyword);
        
        JLabel lblEmployno = new JLabel("員工編號: ");
        lblEmployno.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblEmployno.setBounds(270, 10, 117, 31);
        searchPanel.add(lblEmployno);
        lblEmployno.setText("員工編號: "+infoEmployno);
        
        JLabel lblEmployname = new JLabel("員工名字: ");
        lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblEmployname.setBounds(350, 10, 117, 31);
        searchPanel.add(lblEmployname);
        lblEmployname.setText("員工名字: "+infoEmployname);
        
        JLabel lblTime = new JLabel("New label");
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 10));
        lblTime.setBounds(457, 10, 128, 30);
        searchPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();

        JPanel productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(0, 78, 585, 173);
        getContentPane().add(productPanel);

        productTable = new JTable();
        productTable.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        productTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"產品編號", "產品名稱", "產品價格", "庫存數量"})
        {
        	//惟讀
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}	
        });
        JScrollPane productSL = new JScrollPane(productTable);
        productSL.setBounds(0, 0, 585, 114);
        productPanel.add(productSL);

        JPanel productPN = new JPanel();
        productPN.setBounds(0, 118, 585, 58);
        productPanel.add(productPN);
        productPN.setLayout(null);
        productPN.setBackground(SystemColor.inactiveCaptionBorder);

        lblSelectedProduct = new JLabel("挑選的產品: 無");
        lblSelectedProduct.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblSelectedProduct.setBounds(10, 10, 212, 30);
        productPN.add(lblSelectedProduct);
        
        JLabel lblcbSelectedProduct = new JLabel("購買數量");
        lblcbSelectedProduct.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblcbSelectedProduct.setBounds(223, 10, 66, 30);
        productPN.add(lblcbSelectedProduct);
        
        JComboBox cbSelectedProduct = new JComboBox();
        cbSelectedProduct.setModel(new DefaultComboBoxModel(new Integer[] {1,2,3,4,5,6,7,9,10}));
        cbSelectedProduct.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        cbSelectedProduct.setBounds(282, 10, 49, 30);
        productPN.add(cbSelectedProduct);
       
        JButton btnNew = new JButton("新增");
        btnNew.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnNew.setBounds(345, 10, 66, 30);
        productPN.add(btnNew);
        btnNew.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (lblSelectedProduct.getText().equals("挑選的產品: 無"))
        		{
        			JOptionPane.showMessageDialog(PorderCreate2.this, "請選擇產品！", "錯誤", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		int amount=(int) cbSelectedProduct.getSelectedItem();
		        if (amount<=0 ) {
		            JOptionPane.showMessageDialog(null, "數量為零");
		            return;  
		        }
		        
		        DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
		        
		        //檢查產品編號是否重複
		        boolean isProductnoDuplicate = false;
		        for (int i = 0; i < orderModel.getRowCount(); i++) {
		            String existingProductno = (String) orderModel.getValueAt(i, 0);
		            if (existingProductno.equals(selectedProductno)) {
		                isProductnoDuplicate = true;
		                break;
		            }
		        }
		        
		        // 若產品編號不重覆，才能新增
		        if (isProductnoDuplicate) {
		            JOptionPane.showMessageDialog(null, "己有相同產品，請修改數量");
		            return;  
		        }
		        
		        // 若庫存不足，無法新增
		        Product newProduct=productDao.readByProductno(selectedProductno);
		        if (newProduct.getQuantity()<amount) {
		        	JOptionPane.showMessageDialog(PorderCreate2.this, "庫存數量不足！", "錯誤", JOptionPane.ERROR_MESSAGE);
		        	return;
        		}
		        
		        //orderModel 存 String, String, Integer(int->Integer, Integer, Integer(int), 
		        orderModel.addRow(new Object[] {
		        	selectedProductno, selectedProductname, selectedProductprice, 
		        	amount, selectedProductprice*amount
		        });
		     		        
		        //sortOrderTable(); 在 delete 會有問題
		        calculateTotalAmount();
		        lblTotalAmount.setText("總金額: "+totalAmount+" 元");
		        JOptionPane.showMessageDialog(null, "加入清單成功");         		                         		
        	}
        });
        
        
        
        // 設定搜尋按鈕點擊事件
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });

        // 設定點擊 JTable 事件
        productTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                String productNo = productTable.getValueAt(selectedRow, 0).toString();
                String productName = productTable.getValueAt(selectedRow, 1).toString();
                String price = productTable.getValueAt(selectedRow, 2).toString();
                String quantity = productTable.getValueAt(selectedRow, 3).toString();
                lblSelectedProduct.setText("挑選的產品: " + productNo + " - " + productName);
                              
                selectedProductno=productNo;
                selectedProductname=productName;
                selectedProductprice=Integer.parseInt(price);              
                selectedProduct=new Product(productNo,productName,Integer.parseInt(price),Integer.parseInt(quantity));                
            }
        });
        
        
        
        JPanel orderPanel = new JPanel();
        orderPanel.setBounds(0, 250, 586, 234);
        getContentPane().add(orderPanel);
        orderPanel.setBackground(SystemColor.inactiveCaptionBorder);
        orderPanel.setLayout(null);
        
        orderTable = new JTable();
        orderTable.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        orderTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"產品編號", "產品名稱", "產品價格", "購買數量", "小計金額"})
        {
        	//惟讀
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}	
        });
        JScrollPane orderSL = new JScrollPane(orderTable);
        orderSL.setBounds(0, 10, 586, 156);
        orderPanel.add(orderSL);
        
      
        JPanel orderPN = new JPanel();
        orderPN.setBackground(SystemColor.inactiveCaptionBorder);
        orderPN.setBounds(0, 172, 586, 41);
        orderPanel.add(orderPN);
        orderPN.setLayout(null);
           
        //orderTable 是 String, String, int?, int?, int?
        JButton btnUpdate = new JButton("修改");
        btnUpdate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = orderTable.getSelectedRow();  // 取得選中的行
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(PorderCreate2.this, "請先選擇要修改的項目", "提示", JOptionPane.INFORMATION_MESSAGE);
	                return;
	            }
	            // 取得原始數據
	            DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
	            String orderproductno = (String) orderModel.getValueAt(selectedRow, 0);
	            int orderproductprice = (int) orderModel.getValueAt(selectedRow, 2);
	            int orderamount = (int) orderModel.getValueAt(selectedRow, 3);
	            
	            JComboBox<String> cbOrderamount = new JComboBox<>(new DefaultComboBoxModel(new Integer[] {1,2,3,4,5,6,7,8,9,10}));
	            cbOrderamount.setSelectedItem(orderamount);
	            
	            // 建立對話框內容
	            Object[] fields = {
	            	"數量:", cbOrderamount	            	
	            };
	            
	            // 顯示 JOptionPane，讓使用者修改數值
	            int result = JOptionPane.showConfirmDialog(null, fields, "修改數量",
	                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	            // 如果使用者按下 OK，則更新 JTable
	            if (result == JOptionPane.OK_OPTION) {
	                int updateAmount = (int) cbOrderamount.getSelectedItem();
	                if (productDao.readByProductno(orderproductno).getQuantity() < updateAmount )
	                {
	                	JOptionPane.showMessageDialog(PorderCreate2.this, "庫存數量不足", "錯誤", JOptionPane.ERROR_MESSAGE);
	                	return ;
	                }
	                orderModel.setValueAt(updateAmount, selectedRow, 3);
	                orderModel.setValueAt(orderproductprice*updateAmount, selectedRow, 4);
	            }   
	            calculateTotalAmount();
		        lblTotalAmount.setText("總金額: "+totalAmount+" 元");
        	}
        });
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(117, 10, 66, 30);
        orderPN.add(btnUpdate);
        
        
        JButton btnNext = new JButton("下一步");
        btnNext.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (totalAmount<=0)
        		{
        			JOptionPane.showMessageDialog(PorderCreate2.this, "請先增加產品", "提示", JOptionPane.INFORMATION_MESSAGE);
        			return ;
        		}
        		saveOrderTable();
        		PorderCreate3 frame=new PorderCreate3();
        		frame.loadOrderTable();
            	frame.calculateTotalAmount();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        btnNext.setBounds(382, 10, 80, 30);
        orderPN.add(btnNext);
        btnNext.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        JButton btnBack = new JButton("回到主頁");
        btnBack.setBounds(472, 10, 87, 30);
        orderPN.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderCreate2.this);
        		dispose();
        	}
        });
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        JButton btnDelete = new JButton("刪除");
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = orderTable.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(PorderCreate2.this, "請先選擇要刪除的列", "提示", JOptionPane.INFORMATION_MESSAGE);
	                return;
	            }
	            int result = JOptionPane.showConfirmDialog(null,"確定?","是否要刪除?",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	            if (result == JOptionPane.OK_OPTION) {
	                DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
	                orderModel.removeRow(selectedRow);
	            }
	            calculateTotalAmount();
		        lblTotalAmount.setText("總金額: "+totalAmount+" 元");
	       }
        });
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(193, 10, 66, 30);
        orderPN.add(btnDelete);
        
        lblTotalAmount = new JLabel("總金額:");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTotalAmount.setBounds(10, 10, 112, 30);
        orderPN.add(lblTotalAmount);
        
    }

    private void searchProducts() {
        String keyword = txtSearch.getText().trim();
        List<Product> products = productDao.readByKeyword(keyword);
        DefaultTableModel productModel = (DefaultTableModel) productTable.getModel();
        productModel.setRowCount(0);
        for (Product p : products) {
            productModel.addRow(new Object[]{p.getProductno(), p.getProductname(), p.getPrice(), p.getQuantity()});
        }
    }

    public void sortOrderTable() {
    	DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(orderModel);
        orderTable.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING))); // 0 表示第一欄 productno
    }
    
    public void calculateTotalAmount() {
    	DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
    	totalAmount=0;
    	for (int i = 0; i < orderModel.getRowCount(); i++) {
            totalAmount+=(int)orderModel.getValueAt(i, 4);
        }
    }
    
    public void saveOrderTable() {
    	DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
    	DefaultTableModel orderModel2 = new DefaultTableModel(
    		    new String[]{"產品編號", "產品名稱", "產品價格", "購買數量", "小計金額"}, 0
    		);
    	int rowCount = orderModel.getRowCount();
    	int colCount = orderModel.getColumnCount();
    	for (int i = 0; i < rowCount; i++) {
    	    Object[] rowData = new Object[colCount]; // 建立存放單列資料的陣列
    	    for (int j = 0; j < colCount; j++) {
    	        rowData[j] = orderModel.getValueAt(i, j); // 逐個欄位複製
    	    }
    	    orderModel2.addRow(rowData); // 將資料加入新的 TableModel
    	}
    	Tool.saveOrderModel(orderModel2, "orderModel.txt");
    }
    
}
