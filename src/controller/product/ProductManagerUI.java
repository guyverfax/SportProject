package controller.product;

import dao.impl.PorderDaoImpl;
import dao.PorderDao;
import model.Employ;
import model.Porder;

import dao.impl.ProductDaoImpl;
import dao.ProductDao;
import model.Product;
import util.Tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductManagerUI extends JFrame {
	
	//登入資訊
	private Employ loginEmploy=(Employ) Tool.read("employ.txt");
	private String loginRole=loginEmploy.getRole();
	private String loingEmployno=loginEmploy.getEmployno();
	private String loingEmployusername=loginEmploy.getUsername();
		
    private JTextField txtProductNo, txtProductName, txtPrice, txtQuantity;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ProductDao productDao = new ProductDaoImpl();
    private PorderDao porderDao = new PorderDaoImpl();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProductManagerUI().setVisible(true);
        });
    }
    
    public ProductManagerUI() {
        setTitle("產品管理");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // 上方 JPanel (輸入區域)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(204, 255, 204));
        topPanel.setBounds(10, 10, 560, 120);
        topPanel.setLayout(null);
        getContentPane().add(topPanel);

        JLabel lblProductNo = new JLabel("產品編號:");
        lblProductNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblProductNo.setBounds(10, 10, 80, 25);
        topPanel.add(lblProductNo);
        txtProductNo = new JTextField();
        txtProductNo.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtProductNo.setBounds(100, 10, 150, 25);
        topPanel.add(txtProductNo);

        JLabel lblProductName = new JLabel("產品名稱:");
        lblProductName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblProductName.setBounds(10, 40, 80, 25);
        topPanel.add(lblProductName);
        txtProductName = new JTextField();
        txtProductName.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtProductName.setBounds(100, 40, 150, 25);
        topPanel.add(txtProductName);

        JLabel lblPrice = new JLabel("價格:");
        lblPrice.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblPrice.setBounds(270, 10, 50, 25);
        topPanel.add(lblPrice);
        txtPrice = new JTextField();
        txtPrice.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtPrice.setBounds(320, 10, 100, 25);
        topPanel.add(txtPrice);

        JLabel lblQuantity = new JLabel("庫存數量:");
        lblQuantity.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblQuantity.setBounds(270, 40, 80, 25);
        topPanel.add(lblQuantity);
        txtQuantity = new JTextField();
        txtQuantity.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        txtQuantity.setBounds(340, 40, 100, 25);
        topPanel.add(txtQuantity);

        JButton btnAdd = new JButton("新增產品");
        btnAdd.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnAdd.setBounds(10, 80, 100, 25);
        topPanel.add(btnAdd);

        JButton btnUpdate = new JButton("修改產品");
        btnUpdate.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnUpdate.setBounds(120, 80, 100, 25);
        topPanel.add(btnUpdate);

        JButton btnDelete = new JButton("刪除產品");
        btnDelete.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        btnDelete.setBounds(230, 80, 100, 25);
        topPanel.add(btnDelete);
        
        JLabel lblLogin = new JLabel("登入帳號: <dynamic>");
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblLogin.setBounds(405, 75, 138, 15);
        topPanel.add(lblLogin);
        lblLogin.setText("登入帳號: "+loingEmployusername);
        
        JLabel lblTime = new JLabel("現在時間:");
        lblTime.setForeground(Color.BLACK);
        lblTime.setFont(new Font("微軟正黑體", Font.BOLD, 12));
        lblTime.setBounds(405, 95, 155, 15);
        topPanel.add(lblTime);
        Tool.updateDateTime(lblTime);	
		Timer timer = new Timer(1000, e -> Tool.updateDateTime(lblTime));
		timer.start();

        // 中間 JPanel (表格區域)
        JPanel middlePanel = new JPanel();
        middlePanel.setBounds(10, 140, 560, 250);
        middlePanel.setLayout(new BorderLayout());
        getContentPane().add(middlePanel);

        tableModel = new DefaultTableModel(new String[]{"產品編號", "產品名稱", "價格", "庫存數量", "訂單備貨量", "差額"}, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        middlePanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        // 下方 JPanel (額外功能按鈕)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(204, 255, 204));
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
        		Tool.goMainUI(ProductManagerUI.this);
        	}
        });
        btnBack.setBounds(442, 10, 108, 30);
        bottomPanel.add(btnBack);

        loadProductData(); // 載入資料

        // 新增
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        // 修改
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        // 刪除
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        // 重新載入
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadProductData();
            }
        });
    }

    // 新增產品
    private void addProduct() {
        String productNo = txtProductNo.getText().trim();
        String productName = txtProductName.getText().trim();
        String priceText = txtPrice.getText().trim();
        String quantityText = txtQuantity.getText().trim();

        // 驗證輸入
        if (productNo.isEmpty() || productName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請填寫所有欄位!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidInteger(priceText) || !isValidInteger(quantityText)) {
            JOptionPane.showMessageDialog(this, "價格與庫存數量須為正整數!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidProductno(productNo)) {
        	JOptionPane.showMessageDialog(this, "產品編號需4位字元,開頭P和3位數字!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
          
        if (productDao.isExitsByProductno(productNo))
        {
        	JOptionPane.showMessageDialog(this, "產品編號重覆!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int price = Integer.parseInt(priceText);
        int quantity = Integer.parseInt(quantityText);

        Product product = new Product(productNo, productName, price, quantity);
        productDao.create(product);
        JOptionPane.showMessageDialog(this, "產品新增成功!");
        loadProductData();
    }

    // 修改產品
    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要修改的產品!");
            return;
        }

        String productNo = (String) tableModel.getValueAt(selectedRow, 0);
        String newProductName = JOptionPane.showInputDialog("輸入新的產品名稱:", tableModel.getValueAt(selectedRow, 1));
        String newPriceText = JOptionPane.showInputDialog("輸入新的價格:", tableModel.getValueAt(selectedRow, 2));
        String newQuantityText = JOptionPane.showInputDialog("輸入新的庫存數量:", tableModel.getValueAt(selectedRow, 3));

        if (newProductName == null || newProductName.trim().isEmpty() || 
            newPriceText == null || newPriceText.trim().isEmpty() || 
            newQuantityText == null || newQuantityText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有欄位不可為空!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidInteger(newPriceText) || !isValidInteger(newQuantityText)) {
            JOptionPane.showMessageDialog(this, "價格與庫存數量須為正整數!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newPrice = Integer.parseInt(newPriceText);
        int newQuantity = Integer.parseInt(newQuantityText);

        Product updatedProduct = new Product(productNo, newProductName, newPrice, newQuantity);
        productDao.update(updatedProduct);
        JOptionPane.showMessageDialog(this, "產品修改成功!");
        loadProductData();
    }

    // 刪除產品
    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "請選擇要刪除的產品!");
            return;
        }

        String productNo = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除產品 " + productNo + " 嗎?", "確認刪除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        	List<Porder> porderList=new ArrayList();
        	porderList=porderDao.readByProductno(productNo);
            if (!porderList.isEmpty())
            {
            	JOptionPane.showMessageDialog(this, "無法刪除產品, 因為有訂單!", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
            	return ;
            }        
            productDao.delete(productNo);
            JOptionPane.showMessageDialog(this, "產品刪除成功!");
            loadProductData();
        }
    }

    // 載入產品資料
    private void loadProductData() {
        tableModel.setRowCount(0); // 清空表格
        List<Product> products = productDao.readAll();
        for (Product product : products) {
        	int backOrder=porderDao.readAmount(product.getProductno());
           	tableModel.addRow(new Object[]{product.getProductno(), product.getProductname(), product.getPrice(), product.getQuantity(), backOrder, product.getQuantity()-backOrder});
        }
    }

    private boolean isValidInteger(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        try {
        	int number = Integer.parseInt(text.trim());
            return number > 0; // 只有大於 0 才算有效的正整數
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidProductno(String productno) {
        String regex = "^P\\d{3}$";
        return productno.matches(regex);
    }   
}