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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Font;
import java.awt.GridLayout;

import model.Employ;
import dao.EmployDao;
import dao.impl.EmployDaoImpl;
import dao.MemberDao;
import dao.PorderDao;
import dao.ProductDao;
import dao.impl.MemberDaoImpl;
import dao.impl.PorderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Member;
import model.Porder;
import model.Product;
import util.Tool;
import java.awt.Component;

public class PorderCreate3 extends JFrame{
    private JTable orderTable;
    private JLabel lblTotalAmount; //顥示總金額
    private int totalAmount=0; //計算總金額
    
    //Order No
    private String orderNo;
    
    //for selected product
    private Product selectedProduct; 
    private String selectedProductno;
    private String selectedProductname;
    private int selectedProductprice;
    private int selectedProductquantity;
    
    Employ employ=(Employ) Tool.read("employ.txt");
    private String infoEmployno=employ.getEmployno();
    private String infoEmployname=employ.getName();
    private String infoEmployusername;
    private String infoEmployrole;
    
    Member member=(Member) Tool.read("member.txt");
    private String infoMemberno=member.getMemberno();
    private String infoMembername=member.getName();
    private String infoMemberphone=member.getPhone();
    private String infoMemberaddress=member.getAddress();
    
    private MemberDao memberDao=new MemberDaoImpl();
    private EmployDao employDao=new EmployDaoImpl();
    private ProductDao productDao=new ProductDaoImpl();
    private PorderDao porderDao=new PorderDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PorderCreate3().setVisible(true));
    }
    
    public PorderCreate3() {
        setTitle("新增訂單 - 成立訂單");
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
        lblSelectProduct.setBackground(Color.LIGHT_GRAY);
        lblSelectProduct.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectProduct.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblSelectProduct.setOpaque(true);
        topPanel.add(lblSelectProduct);
        
        JLabel lblCheckOut = new JLabel("3.成立訂單");
        lblCheckOut.setBackground(Color.GREEN);
        lblCheckOut.setHorizontalAlignment(SwingConstants.CENTER);
        lblCheckOut.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblCheckOut.setOpaque(true);
        topPanel.add(lblCheckOut);

        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(0, 20, 585, 234);
        infoPanel.setBackground(SystemColor.inactiveCaptionBorder);
        getContentPane().add(infoPanel);
        infoPanel.setLayout(new GridLayout(6, 2, 10, 10));
        
        JLabel lblOrderno = new JLabel("訂單號碼: ");
        lblOrderno.setForeground(Color.BLACK);
        lblOrderno.setBackground(Color.WHITE);
        lblOrderno.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        infoPanel.add(lblOrderno);
        orderNo=Tool.generateOrderNumber();
        lblOrderno.setText("訂單號碼: "+orderNo);
        
        JLabel lblOrdertime = new JLabel("訂單時間: ");
        lblOrdertime.setForeground(Color.BLACK);
        lblOrdertime.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblOrdertime.setBackground(Color.WHITE);
        infoPanel.add(lblOrdertime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = sdf.format(new Date());
		lblOrdertime.setText("訂單時間: "+currentDateTime);
        
        JLabel lblEmployno = new JLabel("員工編號: ");
        lblEmployno.setForeground(Color.BLACK);
        lblEmployno.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblEmployno.setBackground(Color.WHITE);
        infoPanel.add(lblEmployno);
        lblEmployno.setText("員工編號: "+infoEmployno);
        
        JLabel lblEmployname = new JLabel("員工名字: ");
        lblEmployname.setForeground(Color.BLACK);
        lblEmployname.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblEmployname.setBackground(Color.WHITE);
        infoPanel.add(lblEmployname);
        lblEmployname.setText("員工名字: "+infoEmployname);
        
        JLabel lblMemberno = new JLabel("客戶編號: ");
        lblMemberno.setForeground(Color.BLACK);
        lblMemberno.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMemberno.setBackground(Color.WHITE);
        infoPanel.add(lblMemberno);
        lblMemberno.setText("客戶編號: "+infoMemberno);
        
        JLabel lblMembername = new JLabel("客戶名字: ");
        lblMembername.setForeground(Color.BLACK);
        lblMembername.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMembername.setBackground(Color.WHITE);
        infoPanel.add(lblMembername);
        lblMembername.setText("客戶名字: "+infoMembername);
        
        JLabel lblMemberphone = new JLabel("客戶電話: ");
        lblMemberphone.setForeground(Color.BLACK);
        lblMemberphone.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMemberphone.setBackground(Color.WHITE);
        infoPanel.add(lblMemberphone);
        lblMemberphone.setText("客戶電話: "+infoMemberphone);
        
        JLabel lblMemberaddress = new JLabel("客戶地址: ");
        lblMemberaddress.setForeground(Color.BLACK);
        lblMemberaddress.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        lblMemberaddress.setBackground(Color.WHITE);
        infoPanel.add(lblMemberaddress);
        lblMemberaddress.setText("客戶地址: "+infoMemberaddress);
              
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
        
        
        JButton btnSubmit = new JButton("送出");
        btnSubmit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (totalAmount<=0)
        		{
        			JOptionPane.showMessageDialog(null, "請先增加產品", "提示", JOptionPane.INFORMATION_MESSAGE);
        			return ;
        		}
        		
        		DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
        		
        		//整筆判斷
        		List<Porder> orders=new ArrayList();
        		for (int i = 0; i < orderModel.getRowCount(); i++) {
					String productno=(String) orderModel.getValueAt(i, 0);
					int amount=(int) orderModel.getValueAt(i, 3);					
					Porder porder=new Porder(orderNo,productno,infoMemberno,infoEmployno,amount);
					orders.add(porder);			
        		}
        		
        		boolean success = porderDao.createUpdateStock(orders);
        		if (success) {
        			JOptionPane.showMessageDialog(null, "訂單成立，庫存已更新，回到主頁！", "提示", JOptionPane.INFORMATION_MESSAGE);
        			Tool.goMainUI(PorderCreate3.this);
        		} else {
                	JOptionPane.showMessageDialog(null, "訂單建立失敗，可能是庫存不足或系統錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
        		
        		/*單筆判斷
        		for (int i = 0; i < orderModel.getRowCount(); i++) {
					String productno=(String) orderModel.getValueAt(i, 0);
					int amount=(int) orderModel.getValueAt(i, 3);					
					Porder porder=new Porder(orderNo,productno,infoMemberno,infoEmployno,amount);
					boolean success=porderDao.createUpdateStock(porder);			
        		}		
        		JOptionPane.showMessageDialog(null, " 訂單成立，庫存已更新，回到主頁！", "提示", JOptionPane.INFORMATION_MESSAGE);
				
				Tool.goMainUI(PorderCreate3.this);
				*/
				return ;			
        	}
        });
        btnSubmit.setBounds(382, 10, 80, 30);
        orderPN.add(btnSubmit);
        btnSubmit.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        JButton btnBack = new JButton("回到主頁");
        btnBack.setBounds(472, 10, 87, 30);
        orderPN.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Tool.goMainUI(PorderCreate3.this);
        		dispose();
        	}
        });
        btnBack.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        
        lblTotalAmount = new JLabel("總金額:");
        lblTotalAmount.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTotalAmount.setBounds(10, 10, 112, 30);
        orderPN.add(lblTotalAmount);
        loadOrderTable();
    	calculateTotalAmount();
    }

    private void sortOrderTable() {
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
    	lblTotalAmount.setText("總金額: "+totalAmount+" 元");
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
    
    public void loadOrderTable() {
    	DefaultTableModel loadedOrderModel = Tool.loadOrderModel("orderModel.txt");
    	if (loadedOrderModel != null) {
    	    System.out.println("orderModel 已成功讀取！");
    	}
    	
    	DefaultTableModel orderModel = new DefaultTableModel(
    		    new String[]{"產品編號", "產品名稱", "產品價格", "購買數量", "小計金額"}, 0
    		) {
    		    @Override
    		    public boolean isCellEditable(int row, int column) {
    		        return false; // 讓所有儲存格不可編輯
    		    }
    		};
    	int rowCount = loadedOrderModel.getRowCount();
    	int colCount = loadedOrderModel.getColumnCount();
    	for (int i = 0; i < rowCount; i++) {
    	    Object[] rowData = new Object[colCount]; // 建立存放單列資料的陣列
    	    for (int j = 0; j < colCount; j++) {
    	        rowData[j] = loadedOrderModel.getValueAt(i, j); // 逐個欄位複製
    	    }
    	    orderModel.addRow(rowData); // 將資料加入新的 TableModel
    	}
    	orderTable.setModel(orderModel);
    }   
}
